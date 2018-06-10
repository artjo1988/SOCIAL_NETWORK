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

		<title>Изменение почты</title>

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
		<div class="alert alert-danger" role="alert"> ${message} </div>
	</#if>
    	<div class="wrapper-page animated fadeInDown">
			<div class="panel panel-color panel-primary">
            	<form method="post" action="/changeEmail" role="form" class="text-center">
					<div class="alert alert-info alert-dismissable">
                  	  	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">
                  	      	×
                  	  	</button>
                  	  	<h1><i class="fa fa-envelope-o text-info"></i></h1> На Ваш новый <b>Email</b> придет уведомление о изменении почты!
               	 	</div>
                	<div class="form-group m-b-0" style="padding-bottom: 20px; padding-top: 10px">
						<div class="input-group">
                       		<input class="form-control" type="text" id="oldEmail" name="oldEmail" required="" placeholder="Старая почта">
                    	</div>
                	</div>					<div class="form-group m-b-0" style="padding-bottom: 20px">
                    	<div class="input-group">
                        	<input class="form-control" type="text" id="newEmail" name="newEmail" required="" placeholder="Новая почта">
                   		</div>
					</div>
					<div class="form-group m-b-0" style="padding-bottom: 20px">
						<div class="input-group">
                        	<input class="form-control" type="text" id="reNewEmail" name="reNewEmail" required="" placeholder="Повторите новую почту">
                    	</div>
                	</div>
                    <div class="form-group text-right" style="padding-bottom: 20px">
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
