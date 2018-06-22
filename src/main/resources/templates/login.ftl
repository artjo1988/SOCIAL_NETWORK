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

		<title>Вход</title>

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

	</head>

    <body>

    	<#if error??>
		<div class="alert alert-danger" role="alert">Логин или пароль введены неверно</div>
		</#if>
        <div class="logo hidden-xs">
            <a href="/" class="logo-expanded"> <img src="/img/icon.png" alt="logo"> <span class="nav-text" style="color: #3b5998 "> Друзья</span> </a>
        </div>
        <div class="wrapper-page animated fadeInDown">
            <div class="panel panel-color panel-primary">
                <div class="panel-heading"> 
                   <h3 class="text-center m-t-10"> Войти в социальную сеть <strong>"Друзья"</strong> </h3>
                </div>
                <form class="form-horizontal m-t-40" method="post" action="/login">                                           
                    <div class="form-group ">
                        <div class="col-xs-12">
                            <label for="login">
                                <input class="form-control" type="text" id="login" name="login" placeholder="Логин">
                            </label>
                        </div>
                    </div>
                    <div class="form-group ">
                        <div class="col-xs-12">
                            <label for="password">
                                <input class="form-control" type="password" id="password"  name="password" placeholder="Пароль">
                            </label>
                        </div>
                    </div>
                    <div class="form-group ">
                        <div class="col-xs-12">
                            <label for="remember-me" class="cr-styled" >
                                <input type="checkbox" checked id="remember-me" name="remember-me">
                                <i class="fa"></i> 
                                Запомнить меня
                            </label>
                        </div>
                    </div>
                    <div class="form-group text-right">
                        <div class="col-xs-12">
                            <button class="btn btn-primary w-md" type="submit">Войти</button>
                        </div>
                    </div>
                    <div class="form-group m-t-30">
                        <div class="col-sm-7">
                            <a href="/recoverPassword/email"><i class="fa fa-lock m-r-5"></i> Забыли пароль? </a>
                        </div>
                        <div class="col-sm-5 text-right">
                            <a href="/register" class="btn btn-primary w-md" >Регистрация</a>
                        </div>
                    </div>
                </form>
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