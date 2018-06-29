
function sendFile(file) {
    var formData = new FormData();
    formData.append("file", file);
    $.ajax({
    type: "POST",
    url: "/files",
    data: formData,
    contentType: false,
    processData: false,
    success: function (data) {
    $("#avatar").html("<img class=\"img-circle text-center m-t-15\" width='100' height='100' src='/files/" +
    data + "'/>");

    },
    error: function (data) {
    alert(data)
    }
    });
};



function chekRegister() {
    var login,
        eMail,
        password,
        password2,
        loginStat,
        eMailStat,
        passwordStat,
        password2Stat;
    //Логин
    $("#login").change(function(){
        login = $("#login").val();
        var expLogin = /^[a-zA-Z0-9_]+$/g;
        var resLogin = login.search(expLogin);
        if(resLogin == -1){
            $("#login").next().hide().text("  Неверный логин").css("color","red").fadeIn(400);
            $("#login").removeClass().addClass("inputRed");
            loginStat = 0;
            buttonOnAndOff();
        }else{
            $.ajax({
                url: "/checkLogin",
                type: "POST",
                data: {"login" : login},
                cache: false,
                success: function(response){
                    if(response["message"] == "true"){
                        $("#login").next().hide().text("  Логин занят").css("color","red").fadeIn(400);
                        $("#login").removeClass().addClass("inputRed");
                        loginStat = 0;
                        buttonOnAndOff();
                    }else{
                        $("#login").removeClass().addClass("inputGreen");
                        $("#login").next().text("");
                        loginStat = 1;
                        buttonOnAndOff();
                    }

                }
            });
        }
    });
    $("#login").keyup(function(){
        $("#login").removeClass();
        $("#login").next().text("");
    });

    // Email
    $("#eMail").change(function(){
        eMail = $("#eMail").val();
        var expEmail = /[-0-9a-z_]+@[-0-9a-z_]+\.[a-z]{2,6}/i;
        var resEmail = eMail.search(expEmail);
        if(resEmail == -1){
            $("#eMail").next().hide().text("  Неверный Email").css("color","red").fadeIn(400);
            $("#eMail").removeClass().addClass("inputRed");
            emailStat = 0;
            buttonOnAndOff();
        }else{
            $.ajax({
                url: "/checkEmail",
                type: "POST",
                data: "eMail=" + eMail,
                cache: false,
                success: function(response){
                    if(response["message"] == "true"){
                        $("#eMail").next().hide().text("  Email занят").css("color","red").fadeIn(400);
                        $("#eMail").removeClass().addClass("inputRed");
                        eMailStat = 0;
                        buttonOnAndOff();
                    }else{
                        $("#eMail").removeClass().addClass("inputGreen");
                        $("#eMail").next().text("");
                        eMailStat = 1;
                        buttonOnAndOff();
                    }

                }
            });

        }
    });
    $("#eMail").keyup(function(){
        $("#eMail").removeClass();
        $("#eMail").next().text("");
    });

    //Пароль
    $("#password").change(function(){
        password = $("#password").val();
        if(password.length < 6){
            $("#password").next().hide().text(" Короткий пароль").css("color","red").fadeIn(400);
            $("#password").removeClass().addClass("inputRed");
            passwordStat = 0;
            buttonOnAndOff();
        }else{
            $("#password").removeClass().addClass("inputGreen");
            $("#password").next().text("");
            passwordStat = 1;
            buttonOnAndOff();
        }
    });
    $("#password").keyup(function(){
        $("#password").removeClass();
        $("#password").next().text("");
    });

    //Повторный пароль
    $("#password2").change(function(){
        if(password2 != password){
            $("#password2").next().hide().text(" Не совпадает").css("color","red").fadeIn(400);
            $("#password2").removeClass().addClass("inputRed");
            password2Stat = 0;
            buttonOnAndOff();
        }else{
            $("#password2").removeClass().addClass("inputGreen");
            $("#password2").next().text("");
        }
    });
    $("#password2").keyup(function(){
        password2 = $("#password2").val();
        if(password2 == password){
            password2Stat = 1;
            buttonOnAndOff();
        }else{
            password2Stat = 0;
            buttonOnAndOff();
        }
    });

    function buttonOnAndOff(){
        if(eMailStat == 1 && passwordStat == 1 && password2Stat == 1 && loginStat == 1){
            $("#submit").removeAttr("disabled");
        }else{
            $("#submit").attr("disabled","disabled");
        }
    }
};

function insertEditPost(id){
    var text=document.getElementById(id);
    document.getElementById('inputText').value=text.innerText;
    document.getElementById('idPost_hidden').value=id.toString();
};


function getMessages() {
    const idChat = getUrlVars()['id'];
    $.ajax({
        url: SERVER_API_URL + '/chats/' + idChat + '/messages/get',
        type: 'GET',
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            for (let i = 0; i < response.length; i++){
                writeMessage(response[i].from, response[i].message)
            }
        }
    });
}

function sendMessageBySocket(message, chatId) {
    var json = {};
    json["text"] = message.value;
    $.ajax({
        url: SERVER_API_URL + '/chats/' + chatId + '/messages/send',
        type: 'POST',
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(json),
        complete: function () {
            var input = document.getElementById("message");
            input.value = "";
        }
    })
}

/**
 * функция вывода сообщения в текстовом поле chatMessagesList
 * @param message - текст сообщения
 */
function writeMessage(from, message) {
    var select = document.getElementById('chatMessagesList');
    var messageOption = document.createElement('option');
    messageOption.value = 0;
    messageOption.innerHTML = from + " : " + message;
    select.appendChild(messageOption);
    // var messageOptionFrom = document.createElement('option');
    // messageOptionFrom.value = 0;
    // messageOptionFrom.innerHTML = from + " : ";
    // select.appendChild(messageOptionFrom);
    // var messageOptionMessage = document.createElement('option');
    // messageOptionMessage.value = 0;
    // messageOptionMessage.innerHTML = ("- - - - -> " + message);
    // select.appendChild(messageOptionMessage);
}
/**
 * функция получения параметров  url'a
 * для того, что бы вытащить только один параметр нужно указать его в -[]
 * например: getUrlVars()['id'];
 * @returns массив всех параметров
 */
function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}


const SERVER_API_URL = 'http://localhost';

function doConnect() {
    const chatId = getUrlVars()['id'];
    var socket = new SockJS(SERVER_API_URL + '/chat');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe(
            '/topic/chats/' + chatId,
            // что происходит, когда к нам приходит сообщение
            function (messageOutput) {
                writeMessage(JSON.parse(messageOutput.body).from, JSON.parse(messageOutput.body).message);
            });
    });
}

// function getStatus(idUser, idCandidate){
//     var idUs = document.getElementById(idUser)
//     var idCand = document.getElementById(idCandidate)
//     .ajax({
//         type: "POST",
//         url: "/getStatus",
//         data:{
//             "idUser" : idUs,
//             "idCandidate" : idCand
//         },
//         contentType: false,
//         processData: false,
//         cache: false,
//         success: function(response){
//            document.getElementById(idCandidate).value = response["message"].innerHTML;
//         }
//     })
// };

// function chekChatById(id) {
//     $.ajax({
//         type: 'GET',
//         url: '/chat/' + id,
//         contentType: false,
//         processData: false,
//         cache: false,
//         success: function (response){
//             $.ajax({
//                 type: 'GET',
//                 url: 'http://localhost:8081/chat.html',
//                 contentType: false,
//                 processData: false,
//                 cache: false,
//                 success:{
//
//                 },
//                 error:{
//                     400 : function () {
//                         alert('Не получилось!')
//                     }
//                 }
//             });
//         },
//         statusCode: {
//             400 : function () {
//                 alert('Не получилось!')
//             }
//         }
//     });
// };








