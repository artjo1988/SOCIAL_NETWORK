<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html lang="en">

	<head>
		<meta charset="utf-8">
		<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
		<meta name="description" content="Admina Bootstrap Admin. This is the demo of Admina. You need to purchase a license for legal use!">
		<meta name="author" content="DownTown Themes">

		<link rel="shortcut icon" href="/img/icon.png">

		<title>Загрузка аватарки</title>

        <!-- Google Fonts -->
        <link href='http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

        <!--Icon fonts css-->
        <link href="/plugins/font-awesome/css/font-awesome.css" rel="stylesheet" />
        <link href="/plugins/ionicon/css/ionicons.min.css" rel="stylesheet" />

        <!-- Bootstrap CSS -->
        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <link href="/css/bootstrap-reset.css" rel="stylesheet">

        <!--Animation css-->
        <link href="/css/animate.css" rel="stylesheet">

        <!--Morris Chart CSS -->
        <link rel="stylesheet" href="/plugins/morris/morris.css">

        <!-- sweet alerts -->
        <link href="/plugins/sweet-alert/sweet-alert.min.css" rel="stylesheet">

        <!-- Custom styles -->
        <link href="/css/style.css" rel="stylesheet">
        <link href="/css/helper.css" rel="stylesheet">
        <link href="/css/style-responsive.css" rel="stylesheet" />

        <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
        <!--[if lt IE 9]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.min.js"></script>
        <![endif]-->
        <script src="/js/jquery.js"></script>
        <script>
                        function sendFile(file) {
                            var formData = new FormData();
                            formData.append("file", file);
                            var xhr = new XMLHttpRequest();
                            xhr.open("POST", "/files", true);
                            xhr.send(formData);
                        }
        </script>


    </head>

    <body>

    <div class="wrapper-page animated fadeInDown">
        <div class="panel panel-color panel-primary">

            <#--<form method="get" action="/files" role="form" class="text-center">-->
                <#--<div class="alert alert-info alert-dismissable">-->
                    <#--<button type="button" class="close" data-dismiss="alert" aria-hidden="true">-->
                        <#--×-->
                    <#--</button>-->
                    <#--<h1><i class="fa fa-envelope-o text-info"></i></h1> Введите <b>Url</b> Вашей фотографии!-->
                <#--</div>-->
                <#--<div class="form-group m-b-0">-->
                    <#--<div class="input-group">-->
                        <#--<input type="password" class="form-control" placeholder="Enter Url">-->
                        <#--<span class="input-group-btn">-->
								<#--<button type="submit" class="btn btn-primary">-->
									<#--Загрузить-->
								<#--</button> </span>-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="form-group m-b-0 ">-->


                    <#--<div class="input-group" style="margin-left: 15px">-->
                        <#--<input type="file" id="file" name="file" class="form-control" placeholder="Имя файла..."/>-->
                    <#--</div>-->
                <#--</div>-->
                <#--<div class="form-group text-right" style="margin-bottom: 15px; margin-top: 15px ">-->
                    <#--<button onclick="sendFile(($('#file'))[0]['file'][0])"-->
                            <#--class="btn btn-purple w-md">Загрузить файл-->
                    <#--</button>-->
                    <#--<input type="hidden" id="file_hidden">-->
                    <#--<div class="filename"></div>-->
                <#--</div>-->


                <div>
                    <input type="file" id="fileInput" name="file" placeholder="Имя файла..."/>
                    <button onclick="sendFile(($('#fileInput'))[0]['files'][0])"
                            class="btn btn-primary">Загрузить файл
                    </button>
                    <input type="hidden" id="file_hidden">
                    <div class="filename"></div>
                </div>

            <#--</form>-->

        </div>
    </div>

    <!-- Basic Plugins -->
    <script src="/js/jquery.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/pace.min.js"></script>
    <script src="/js/wow.min.js"></script>
    <script src="/js/jquery.nicescroll.js" type="text/javascript"></script>

    <script src="/js/app.js"></script>

    </body>

</html>
