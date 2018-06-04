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

		<title>Моя страница</title>

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

		<link href="/plugins/toggles/toggles.css" rel="stylesheet" />

		<!-- Custom styles -->
		<link href="/css/style.css" rel="stylesheet">
		<link href="/css/helper.css" rel="stylesheet">
		<link href="/css/style-responsive.css" rel="stylesheet" />

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
		<!--[if lt IE 9]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.min.js"></script>
		<![endif]-->

	<script type="text/javascript" src="https://gc.kis.v2.scr.kaspersky-labs.com/6AE77042-A858-0442-B452-785E2AAF4DA7/main.js" charset="UTF-8"></script><link rel="stylesheet" crossorigin="anonymous" href="https://gc.kis.v2.scr.kaspersky-labs.com/7AD4FAA2E587-254B-2440-858A-24077EA6/abn/main.css"/></head>

	<body>
		<!-- Header -->
		<header class="top-head container-fluid navbar-fixed-top">
			<!-- logo -->
			<div class="logo hidden-xs">
				<a href="/profile" class="logo-expanded"> <img src="/img/icon.png" alt="logo"> <span class="nav-text"> Друзья</span> </a>
			</div>
			<!-- end logo -->
			<button type="button" class="navbar-toggle pull-left">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-toggle ion-navicon-round"></span>
			</button>

			<!-- Search -->
			<!-- <form role="search" class="navbar-left app-search pull-left hidden-xs">
				<input type="text" placeholder="Search..." class="form-control">
			</form> -->
			<!-- End Search -->

			<!-- Right navbar -->
			<ul class="list-inline navbar-right top-menu top-right-menu">
				<!-- Messages -->
				<li class="dropdown">
					<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <i class="fa fa-envelope-o "></i> <span class="badge badge-sm up bg-purple count">4</span> </a>
				</li>
				<!-- End messages -->			
				<!-- User Menu Dropdown -->
				<li class="dropdown text-center">
				<#if user.avatarUrl??>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <img alt="" src="${user.avatarUrl}" class="img-circle profile-img thumb-sm"> <span class="username">${user.firstName} ${user.lastName}</span> <span class="caret"></span> </a>
				<#else>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <img alt="" src="/img/no_avatar.jpg" class="img-circle profile-img thumb-sm"> <span class="username">${user.firstName} ${user.lastName}</span> <span class="caret"></span> </a>
				</#if>
					<ul class="dropdown-menu extended pro-menu fadeInUp animated" tabindex="5003" style="overflow: hidden; outline: none;">
						<li>
							<a href="/profile"><i class="fa fa-briefcase"></i>Профайл</a>					
						<li>
							<a href="/logout"><i class="fa fa-sign-out"></i> Выйти</a>
						</li>
					</ul>
				</li>
				<!-- End User Menu Dropdown -->
			</ul>
			<!-- End Right Navbar -->
		</header>
		<!-- End Header -->
		<!-- Aside Menu -->
		<aside class="left-panel">
			<!-- Navbar -->
			<nav class="navigation">
				<ul class="list-unstyled">
					<li class="active">
						<a href="/profile"><i class="ion-home"></i> <span class="nav-label">Моя стрвница</span></a>
					</li>
                    <li class="active">
                        <a href="/profile"><i class="ion-person-stalker"></i> <span class="nav-label"> Мои друзья</span></a>
                    </li>
					<li class="active">
						<a href="/message"><i class="ion-chatbubbles"></i> <span class="nav-label">Сообщения</span></a>
					</li>
				</ul>
			</nav>
			<!-- End Navbar -->
		</aside>
		<!-- End Aside -->
		<!--Main Content -->
		<section class="content">
			<!-- Page Content -->
			<div class="wraper container-fluid">
				<div class="flexWrap profileBlock">	
					<div class="flexItem avatarCollumn">
						<!-- Avatar -->
						<div class="avatar tile-stats white-bg">
							<div class="chat-send">
								<#if user.avatarUrl??>
                             	   <img src= "${user.avatarUrl}" style="width: 210px; height: 230px:">
								<#else>
                            	    <img src="/img/no_avatar.jpg"  style="width: 210px; height: 230px:">
								</#if>
								<form method="get" action="/edit">
								<button  class="btn btn-purple w-md" style="margin-top: 15px;">
									Редактировать
								</button>
								</form>
							</div>
						</div>
						<div class="white-bg dopBlock">
							дополнительный блок можно удалить
						</div>
						<!-- end Avatar -->
					</div><!--flexItem-->
					<div class="flexItem infoCollumn">					
                        <!-- User information -->
                        <div class="user-information tile-stats white-bg">
							<div class=fontForName >${user.firstName} ${user.lastName}</div>
							<#--<div class="profileInfo">-->
                                <div class="flexWrap profileInfo">
                                    <div style="padding-right: 150px; padding-left: 30px;">
										<ul>
											<li class="forLi">День рождения :</li>
											<li class="forLi">Город :</li>
										</ul>
                                    </div>
									<div>
										<ul>
											<li class="forLi">-------</li>
											<li class="forLi">${user.city}</li>
										</ul>
                                    </div>
								</div>
							<#--</div>-->
							<div class="flexWrap profileNums">
								<div class="flexItem"><a href="#"><span>1</span>текст</a></div>
								<div class="flexItem"><a href="#"><span>2</span>текст</a></div>
							</div>
						</div>
						<!-- end User information -->
						<div class="white-bg dopBlock">
							дополнительный блок можно удалить
						</div>
					</div><!--flexItem-->
				</div><!--flexWrap profileBlock-->

						
<style>
	.fontForName {font-size: 35px;}
	.forLi{padding: 8px; font-size: 18px;}
	.flexWrap {display: flex; width:100%;}
	.infoCollumn {flex-grow:1; margin-left: 25px;}
	.flexItem .user-information {margin:0; width:100%; float: none;}
	.avatarCollumn .dopBlock {padding: 25px; box-sizing: border-box; width: 250px;}
	.infoCollumn .dopBlock {padding: 25px; box-sizing: border-box; margin-top: 20px;}
	.profileInfo {  margin: 10px 0 0 0; padding: 15px 0;}
    .flexWrap.profileInfo {border-top: 2px solid rgba(202, 194, 199, 0.96);margin: 10px 0 0 0; padding: 15px 0;justify-content: flex-start;}
	.flexWrap.profileNums {border-top: 2px solid rgba(202, 194, 199, 0.96); margin: 5px 0 0 0; padding: 25px 0;justify-content: space-around;}
	.flexWrap.profileNums .flexItem {}
	.flexWrap.profileNums a span {display: block; text-align: center; font-weight: bold; font-size: 15px;}
</style>
						
						
						
						
						
						
						
			</div>
			<!-- End Content -->

			<!-- Footer -->
			<footer class="footer">
				2018 © "Друзья" автор Артём Пьянов 
			</footer>
			<!-- End Footer -->

		</section>

		<div class="demo-options">
			<div class="demo-options-icon">
				<i class="fa fa-cog"></i>
			</div>
			<div class="demo-heading">
				Настройки
			</div>

			<div class="demo-body">

				<label class="control-label">Фиксация верхней панели</label>
				<div class="control-label">
					<div class="toggle toggle-default active fixedTop"></div>
				</div>

			</div>

			<div class="demo-body">
				<div class="option-title">
					Цвет верхней панели
				</div>
				<ul id="demo-header-color" class="demo-color-list">
					<li>
						<span class="themecolor default"></span>
					</li>
					<li>
						<span class="themecolor dark"></span>
					</li>
					<li>
						<span class="themecolor red"></span>
					</li>
					<li>
						<span class="themecolor green"></span>
					</li>
					<li>
						<span class="themecolor yellow"></span>
					</li>
					<li>
						<span class="themecolor pink"></span>
					</li>
					<li>
						<span class="themecolor purple"></span>
					</li>
					<li>
						<span class="themecolor acqua"></span>
					</li>
					<li>
						<span class="themecolor grey"></span>
					</li>
					<li>
						<span class="themecolor white"></span>
					</li>

				</ul>
			</div>

		</div>

		<!-- Basic Plugins -->
		<script src="/js/jquery-2.1.4.min.js"></script>
		<script src="/js/bootstrap.min.js"></script>
		<script src="/js/modernizr.min.js"></script>
		<script src="/js/pace.min.js"></script>
		<script src="/js/wow.min.js"></script>
		<script src="/js/jquery.scrollTo.min.js"></script>
		<script src="/js/jquery.nicescroll.js" type="text/javascript"></script>
		<script src="/plugins/chat/moment-2.2.1.js"></script>
		<script src="/plugins/toggles/toggles.min.js"></script>

		<!-- Counter up -->
		<script src="/js/waypoints.min.js" type="text/javascript"></script>
		<script src="/js/jquery.counterup.min.js" type="text/javascript"></script>

		<!-- jQuery Flot Chart-->
		<script src="/plugins/flot-chart/jquery.flot.js"></script>
		<script src="/plugins/flot-chart/jquery.flot.tooltip.min.js"></script>
		<script src="/plugins/flot-chart/jquery.flot.resize.js"></script>

		<!--Morris Chart-->
		<script src="/plugins/morris/morris.min.js"></script>
		<script src="/plugins/morris/raphael.min.js"></script>
		<script src="/plugins/morris/morris.init.js"></script>

		<!-- Sparkline -->
		<script src="/plugins/sparkline-chart/jquery.sparkline.min.js" type="text/javascript"></script>
		<script src="/js/sparkline_demo.js" type="text/javascript"></script>

		<!-- Sweet Alerts -->
		<script src="/plugins/sweet-alert/sweet-alert.min.js"></script>
		<script src="/plugins/sweet-alert/sweet-alert.init.js"></script>

		<!-- Todo -->
		<script src="/js/todo.js"></script>

		<!-- Site Script -->
		<script src="/js/app.js"></script>

		<!-- Demo Swicth Color -->
		<script src="/js/demo_color.js"></script>

	</body>
</html>
