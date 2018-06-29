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

    <title>Регистрация</title>

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

    <!-- sweet alerts -->
    <link href="/plugins/sweet-alert/sweet-alert.min.css" rel="stylesheet">

    <!-- Custom styles -->
    <link href="/css/style.css" rel="stylesheet">
    <link href="/css/helper.css" rel="stylesheet">
    <link href="/css/style-responsive.css" rel="stylesheet" />

    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>

    <!-- SELECT 2-->
    <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
    <script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <!-- /SELECT2 -->

    <script>
        $(document).ready(function() {
            $(".js-example-basic-single").select2();
        });
    </script>

</head>


<body onload="chekRegister()">
    <div class="logo hidden-xs">
        <a href="/" class="logo-expanded"> <img src="/img/icon.png" alt="logo"> <span class="nav-text" style="color: #3b5998 "> Друзья</span> </a>
    </div>

		<div class="wrapper-pageReg animated fadeInDown">
			<div class="panel panel-color panel-primary">
				<div class="panel-heading">
					<h3 class="text-center m-t-10"> Регистрация </h3>
				</div>
				<form class="form-horizontal m-t-40" method="post" action="/register" id="form">
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="login">
								<input class="form-control " type="text" id="login" name="login" tabindex=1 required="" placeholder="Логин"><span></span>
							</label>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-12">
							<label for="password">
								<input class="form-control " type="password" id="password" name="password" tabindex=2 required="" placeholder="Пароль"><span></span>
							</label>
						</div>
					</div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <label for="password">
                                <input class="form-control " type="password" id="password2" name="password2" tabindex=3 required="" placeholder="Повторите пароль"><span></span>
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <label for="eMail">
                                <input class="form-control" type="email" id="eMail" name="eMail" required="" tabindex=4 placeholder="Email"><span></span>
                            </label>
                        </div>
                    </div>
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="firstName">
								<input class="form-control " type="text" id="firstName" name="firstName" tabindex=5 required="" placeholder="Имя"><span></span>
							</label>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="lastName">
								<input class="form-control " type="text" id="lastName" name="lastName" tabindex=6 required="" placeholder="Фамилия"><span></span>
							</label>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="city">
                                <select class="js-example-basic-single form-control" id="city" name="city" tabindex=7 equired="">
                                    <#list cities as city>
                                        <option value="${city.city}">${city.city}</option>
                                    </#list>
                                </select><span></span>
							</label>
						</div>
					</div>
                    <div class="form-group ">
                        <div class="col-xs-12">
                            <label for="dataBirthday">
                                <input class="form-control " type="date" id="dataBirthday" name="dataBirthday" form="form" required="" tabindex=8 placeholder="Дата рождения"><span></span>
                            </label>
                        </div>
                    </div>
					<div class="form-group text-right">
						<div class="col-xs-12">
							<button class="btn btn-purple w-md" id="submit" type="submit" disabled >
								Зарегистрироваться
							</button>
						</div>
					</div>
					<div class="form-group m-t-20">
						<div class="col-sm-12 text-center">
							<a href="login.html">Уже зарегистрированы?</a>
						</div>
					</div>
				</form>
			</div>
		</div>

		<!-- Basic Plugins -->
    <!-- Basic Plugins -->
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/pace.min.js"></script>
    <script src="/js/wow.min.js"></script>
    <script src="/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="/js/app.js"></script>
    <script src="/js/main.js"></script>


	</body>

</html>
