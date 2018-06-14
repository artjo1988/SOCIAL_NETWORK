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

			<!-- Right navbar -->
			<ul class="list-inline navbar-right top-menu top-right-menu">
				<!-- Messages -->
				<li class="dropdown">
					<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <i class="ion-ios7-email-outline fa-2x "></i> <span class="badge badge-sm up bg-purple count"></span> </a>
				</li>
				<!-- End messages -->
                <!-- Notification -->
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <i class="ion-person-add fa-2x"></i> <span class="badge badge-sm up bg-pink count"></span> </a>
                </li>
                <!-- End Notification -->
				<!-- User Menu Dropdown -->
				<li class="dropdown text-center">
				<#if user.avatarUrl??>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <img alt="" src="${user.avatarUrl}" class="img-circle profile-img thumb-sm"> <span class="username">${user.firstName} ${user.lastName}</span> <span class="caret"></span> </a>
				<#else>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <img alt="" src="/img/no_avatar.jpg" class="img-circle profile-img thumb-sm"> <span class="username">${user.firstName} ${user.lastName}</span> <span class="caret"></span> </a>
				</#if>
					<ul class="dropdown-menu extended pro-menu fadeInUp animated" tabindex="5003" style="overflow: hidden; outline: none;">
						<li>
							<a href="/profile"><i class="ion-person"></i>Моя страница</a>
                        </li>
                        <li>
                            <a href="/changePassword"><i class="ion-key"></i>Изменить пароль</a>
                        </li>
                        <li>
                            <a href="/changeEmail"><i class="ion-email"></i>Изменить Email</a>
                        </li>
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
		<aside class="left-panel" style="margin-top: 10px">
			<!-- Navbar -->
			<nav class="navigation">
				<ul class="list-unstyled">
					<li class="active">
						<a href="/profile"><i class="ion-home"></i> <span class="nav-label">Моя стрвница</span></a>
					</li>
                    <li class="active">
                        <a href="/friends"><i class="ion-person-stalker"></i> <span class="nav-label"> Мои друзья</span></a>
                    </li>
					<li class="active">
						<a href="/message"><i class="ion-chatbubbles"></i> <span class="nav-label">Мои сообщения</span></a>
					</li>
                    <li class="active">
                        <a href="/users"><i class="ion-ios7-search-strong"></i> <span class="nav-label">Поиск друзей</span></a>
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
							<div class="flexWrap profileNums">
                                <div class="flexItem"><a href="#"><span>${info.friends}</span>друзей</a></div>
                                <div class="flexItem"><a href="#"><span>${info.subscribers}</span>подписчиков</a></div>
                                <div class="flexItem"><a href="#"><span>${info.posts}</span>постов</a></div>
                                <div class="flexItem"><a href="#"><span>${info.chats}</span>диалогов</a></div>
							</div>
						</div>
						<!-- end User information -->
						<div class="white-bg dopBlock">
                            <form method="post" action="/addPost">
                                <div class="form-group m-b-15">
                                    <#--<div class="input-group">-->
										<div class="col-lg-12" style="margin-left: -15px; height: 125px; width: 104%">
											<textarea style="border-color: #c6c6c6" class="form-control " id="inputText" name="inputText" aria-required="true" cols="61" rows = "5" maxlength = "765" autocomplete="on" wrap="hard" required></textarea>
										</div>
                                        <div class="input-group-btn" >
											<button type="submit" class="btn btn-primary" style="float: right">
												Отправить
											</button>
										</div>
                                    <#--</div>-->
                                </div>
                            </form>
							<#if posts??>
								<#list posts as post>
								<div class="dopBlockPosts" style="background: #ebf0ec" display: block" >
									<div style="display: flex; margin-bottom: 10px;">
										<div style="margin-right: 15px">
											<#if user.avatarUrl??>
                                                <img src= "${user.avatarUrl}" class="img-circle text-center " width='65' height='65'>
											<#else>
                                                <img src="/img/no_avatar.jpg"  class="img-circle text-center " width='65' height='65'>
											</#if>
										</div>
                                        <div style="display: block; margin-right: 54%">
                                            <div style="margin: 6px 0 3px 0;">
												<strong>${user.firstName} ${user.lastName}</strong>
                                            </div>
											<div>
												<em>время</em>
											</div>

                                        </div>
										<div class="dropdown text-center" style="margin-top: 6px;">
											<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span>действие</span> <span class="caret"></span> </a>
												<ul class="dropdown-menu extended pro-menu fadeInUp animated" tabindex="5003" style="overflow: hidden; outline: none; margin-top: -40px">
                                                    <li>
                                                        <a href="#" onclick="insertEditPost(${post.id});"><i class="ion-edit"></i>Редактировать</a>
                                                    </li>
                                                    <li>
                                                        <a href="/deletePost/${post.id}"><i class="ion-close"></i>Удалить</a>
                                                    </li>
												</ul>
										</div>
									</div>
									<div style="padding-bottom: 6px">
										${post.content}
									</div>
								</div>
								</#list>
							</#if>
						</div>
					</div><!--flexItem-->
				</div><!--flexWrap profileBlock-->
			</div>
			<!-- End Content -->
			<!-- Footer -->
			<footer class="footer">
				2018 © "Друзья" автор Артём Пьянов 
			</footer>
			<!-- End Footer -->
		</section>

		<div class="demo-options" style="padding-top: 150px">
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
        <script src="/js/main.js"></script>
		<script src="/js/jquery-2.1.4.min.js"></script>
		<script src="/js/bootstrap.min.js"></script>
		<script src="/js/modernizr.min.js"></script>
		<script src="/js/pace.min.js"></script>
		<script src="/js/wow.min.js"></script>
		<script src="/js/jquery.scrollTo.min.js"></script>
		<script src="/js/jquery.nicescroll.js" type="text/javascript"></script>
		<script src="/plugins/chat/moment-2.2.1.js"></script>
		<script src="/plugins/toggles/toggles.min.js"></script>

		<!-- Sweet Alerts -->
		<script src="/plugins/sweet-alert/sweet-alert.min.js"></script>
		<script src="/plugins/sweet-alert/sweet-alert.init.js"></script>

		<!-- Site Script -->
		<script src="/js/app.js"></script>

		<!-- Demo Swicth Color -->
		<script src="/js/demo_color.js"></script>

	</body>
</html>
