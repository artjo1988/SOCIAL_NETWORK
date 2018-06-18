<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<#import 'spring.ftl' as spring>
<@spring.bind "user"/>
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

</head>

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
		<#if newRequestings??>
            <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <i class="ion-person-add fa-2x"></i> <span class="badge badge-sm up bg-pink count">${newRequestings}</span> </a>
		<#else>
            <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <i class="ion-person-add fa-2x"></i> <span class="badge badge-sm up bg-pink count"></span> </a>
		</#if>
		</li>
		<!-- End Notification -->
		<!-- User Menu Dropdown -->
		<li class="dropdown text-center">
			<a data-toggle="dropdown" class="dropdown-toggle" href="#"> <img alt="" src="${user.avatarUrl}" class="img-circle profile-img thumb-sm"> <span class="username">${user.firstName} ${user.lastName}</span> <span class="caret"></span> </a>
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
					<a href="/logout"><i class="fa fa-sign-out"></i>Выйти</a>
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
				<a href="/profile/friends"><i class="ion-person-stalker"></i> <span class="nav-label"> Мои друзья</span></a>
			</li>
			<li class="active">
				<a href="/profile/message"><i class="ion-chatbubbles"></i> <span class="nav-label">Мои сообщения</span></a>
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

            <!-- Левое информационное поле-->
			<div class="flexItem avatarCollumn">


				<!-- Аватарка-->
				<div class="avatar tile-stats white-bg">
					<div class="chat-send">
						   <img src= "${user.avatarUrl}" width="210" height="230" style="width: 210px; height: 250px:">
						<form method="get" action="/edit">
						<button  class="btn btn-purple w-md" style="margin-top: 15px;">
							Редактировать
						</button>
						</form>
					</div>
				</div>
                <!-- Аватарка конец-->


                <!-- Колонка друзья-->
				<div class="dopBlock tile-stats white-bg">

                    <div style="border-bottom: 2px solid rgba(202, 194, 199, 0.96); padding: 0px 0px 10px 0px; margin-top: -5px;  ">
                        Друзья
                    </div>

					<div style="margin-top: 10px; margin-bottom: 25px" >
						<#if user1??>
						    <div  align="center" style="float: left;">
								<div style="padding: 10px 11px 0px 11px;">
                                   <a href="/users/${user1.id}"><img src= "${user1.avatarUrl}" class="img-circle text-center " width='50' height='50'></a>
								</div>
								<div>
									<a href="/users/${user1.id}">${user1.firstName}</a>
								</div>
							</div>
						</#if>
						<#if user2??>
                            <div align="center" style="float: left;">
                                <div style="padding: 10px 11px 0px 11px;">
                                    <a href="/users/${user2.id}"><img src= "${user2.avatarUrl}" class="img-circle text-center " width='50' height='50'></a>
                                </div>
                                <div align="center">
                                    <a href="/users/${user2.id}">${user2.firstName}</a>
                                </div>
                            </div>
						</#if>
						<#if user3??>
                            <div align="center" style="float: left;">
                                <div style="padding: 10px 11px 0px 11px;" >
                                    <a href="/users/${user3.id}"><img src= "${user3.avatarUrl}" class="img-circle text-center " width='50' height='50'></a>
                                </div>
                                <div align="center">
                                    <a href="/users/${user3.id}">${user3.firstName}</a>
                                </div>
                            </div>
						</#if>
					</div>

                    <div style="margin-top: 25px; margin-bottom: 25px">
						<#if user4??>
                            <div align="center" style="float: left;">
                                <div style="padding: 10px 11px 0px 11px;">
									<a href="/users/${user4.id}"><img src= "${user4.avatarUrl}" class="img-circle text-center " width='50' height='50'></a>
								</div>
								<div align="center">
									<a href="/users/${user4.id}">${user4.firstName}</a>
								</div>
							</div>
						</#if>
						<#if user5??>
							<div align="center" style="float: left;">
                                <div style="padding: 10px 11px 0px 11px;">
									<a href="/users/${user5.id}"><img src= "${user5.avatarUrl}" class="img-circle text-center " width='50' height='50'></a>
								</div>
								<div align="center">
									<a href="/users/${user5.id}">${user5.firstName}</a>
								</div>
							</div>
						</#if>
						<#if user6??>
                            <div align="center" style="float: left;">
                                <div style="padding: 10px 11px 0px 11px;">
									<a href="/users/${user6.id}"><img src= "${user6.avatarUrl}" class="img-circle text-center " width='50' height='50'></a>
								</div>
								<div align="center">
									<a href="/users/${user6.id}">${user6.firstName}</a>
								</div>
							</div>
						</#if>
                    </div>
				</div>
                <!-- Колонка друзья конец-->


			</div>
            <!-- Левое информационное поле конец-->


            <!-- Правое информационное поле-->
			<div class="flexItem infoCollumn">


                <!-- Информация о пользователе-->
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
				<!-- Информация о пользователе конеw -->


                <!--Посты-->
				<div class="dopBlock tile-stats white-bg">
					<form method="post" action="/addPost">
						<div class="form-group m-b-15">
							<#--<div class="input-group">-->
								<div class="col-lg-12" style="margin-left: -15px; height: 125px; width: 104%">
									<input  type="hidden" id="idPost_hidden" name ="idPost_hidden" value="">
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
						<div class="dopBlockPosts tile-stats white-bg" style="background: #ebf0ec" display: block" >
							<div style="display: flex; margin-bottom: 10px;">
								<div style="margin-right: 15px">
									<a href="/users/${post.ownerPostDto.id}"><img src= "${post.ownerPostDto.avatarUrl}" class="img-circle text-center " width='65' height='65'></a>
								</div>
								<div style="display: block; margin-right: 54%">
									<div style="margin: 6px 0 3px 0;">
										<a href="/users/${post.ownerPostDto.id}"><strong>${post.ownerPostDto.firstName} ${post.ownerPostDto.lastName}</strong></a>
									</div>
									<div>
										<em>время</em>
									</div>

								</div>
								<div class="dropdown text-center" style="margin-top: 6px;">
									<a data-toggle="dropdown" class="dropdown-toggle" href="#" > <span>действие</span> <span class="caret"></span> </a>
										<ul class="dropdown-menu extended pro-menu fadeInUp animated" tabindex="5003" style="overflow: hidden; outline: none; margin-top: -40px; margin-left: -90px;">
											<#if user.id == post.ownerPostDto.id>
											<li>
												<a href="#" onclick="insertEditPost(${post.id});"><i class="ion-edit"></i>Редактировать</a>
											</li>
											</#if>
											<li>
												<a href="/deletePost/${post.id}"><i class="ion-close"></i>Удалить</a>
											</li>
										</ul>
								</div>
							</div>
							<div id="${post.id}" style="padding-bottom: 6px">
								${post.content}
							</div>
						</div>
						</#list>
					</#if>
				</div>
				<!--Посты конец-->


			</div>
        	<!-- Правое информационное конец-->


		</div>
		<!--flexWrap profileBlock-->
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
