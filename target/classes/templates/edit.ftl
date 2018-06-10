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

		<title>Редактирование</title>

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
		<div class="wrapper-page animated fadeInDown">
			<div class="panel panel-color panel-primary">
				<div class="panel-heading">
					<h3 class="text-center m-t-10"> Редактирование профиля </h3>
				</div>
                <div id="avatar" style="margin-left: 110px;">
				<#if user.avatarUrl??>
                    <img class="img-circle text-center m-t-15" width='100' height='100'  src="${user.avatarUrl}">
				<#else>
                    <img class="img-circle text-center m-t-15" width='100' height='100' src="/img/no_avatar.jpg">
				</#if>
                </div>
                <div style="padding-top: 50px">
				<div class="alert alert-info alert-dismissable" style="margin-top: -35px">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                        ×
                    </button>
                    <div class="text-center"> Выберите <b>Вашу</b> фотографию для загрузки!</div>
                </div>
                <div class="form-group m-b-0 ">
                    <div class="input-group" style="margin-left: 15px">
                        <input type="file" id="fileInput" name="fileInput" class="form-control" placeholder="Имя файла..."/>
                    </div>
                </div>
                <div class="form-group text-right" style="margin-bottom: 15px; margin-top: 15px ">
                    <button onclick="sendFile(($('#fileInput'))[0]['files'][0])"
                            class="btn btn-purple w-md">Загрузить файл
                    </button>
                </div>
                </div>
				<form class="form-horizontal m-t-20" method="post" action="/edit">
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="login" for="login">
								<input class="form-control " type="text" id="login" name="login" value=${user.login} required="" placeholder="Логин">
							</label>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="firstName">
								<input class="form-control " type="text" id="firstName" name="firstName"  value=${user.firstName}  required="" placeholder="Имя">
							</label>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="lastName">
								<input class="form-control " type="text" id="lastName" name="lastName"  value=${user.lastName} required="" placeholder="Фамилия">
							</label>
						</div>
					</div>
					<div class="form-group ">
						<div class="col-xs-12">
							<label for="city">
								<input class="form-control " type="text" id="city" name="city"  value=${user.city} required="" placeholder="Город">
							</label>
						</div>
					</div>
					<div class="form-group text-right">
						<div class="col-xs-12">
							<button class="btn btn-purple w-md" type="submit">
								Изменить
							</button>
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
