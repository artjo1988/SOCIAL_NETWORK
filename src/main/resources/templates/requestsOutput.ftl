<#ftl encoding='UTF-8'>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="utf-8">
    <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="description" content="Admina Bootstrap Admin. This is the demo of Admina. You need to purchase a license for legal use!">
    <meta name="author" content="DownTown Themes">

    <link rel="shortcut icon" href="/img/icon.png">

    <title>Входящие заявки</title>

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

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

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
            <a data-toggle="dropdown" class="dropdown-toggle" href="/profile/requests/new"> <i class="ion-person-add fa-2x"></i> <span class="badge badge-sm up bg-pink count">${newRequestings}</span> </a>
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
            <!-- Users field -->
            <div class="flexItem infoCollumnUsers">
                <div class="white-bg dopBlock">
                    <div style="border-bottom: 2px solid rgba(202, 194, 199, 0.96); margin: -5px 0 10px 0; padding: 0 0 8px 0 ">
                        <a href="/profile/requests/input" style="margin-right: 10%">Входящие заявки<span class="ion-android-arrow-down-left" style="margin-left: 7px;"></span></a>
                        <a href="/profile/requests/output" style="margin-right: 10%">Исходящие заявки<span class="ion-android-arrow-up-right" style="margin-left: 7px;"></span></a>
                        <a href="/profile/requests/new" style="margin-right: 10%">Новые заявки<span class="ion-android-add" style="margin-left: 7px;"></span></a>
                    </div>
                <#if users??>
                    <#list users as user>
                        <div  style="margin-bottom: 15px ; display: block">
                            <div style="display: flex;">
                                <div>
                                    <a class="dropdown-toggle" href="/users/${user.id}"> <img class="img-circle text-center m-t-15" width='100' height='100' alt="" src="${user.avatarUrl}">
                                </div>
                                <div style="padding: 30px 0 0 25px">
                                    <p>
                                        <a href="/users/${user.id}"> <strong>${user.firstName} ${user.lastName}</strong></a>
                                        <br>
                                    ${user.city}
                                    </p>
                                    <a href="/users/${user.id}/message"><em>Написать сообщение</em></a>
                                </div>
                                <div style="padding: 30px; margin-left: 110px;">
                                    <a href="/${user.id}/unsubscribe" class="btn btn-purple w-md">
                                        Отписаться
                                    </a>
                                </div>
                            </div>
                        </div>
                    </#list>
                <#else>
                <span>  Вы пока ни накого не подписаны</span>
                </#if>

                </div>
            </div>
            <!-- End Uers field -->

            <!-- Search option -->
            <div class="flexItem searchCollumnUsers">

                <div class="white-bg dopBlockFriends" style="margin-bottom: 30px">
                    <div style="display: block; flex-direction: column;">
                        <div style="margin: 10px;">
                        <a href="/profile/friends" class="btn btn-purple w-md">
                           Мои друзья
                        </a>
                        </div>
                        <div style="padding: 10px;">
                        <a href="/profile/requests/input" class="btn btn-purple w-md">
                            Заявки в друзья
                        </a>
                        </div>
                    </div>
                </div>

            </div>
            <!-- End Search option -->
        </div>
    </div>
    <!-- End Content -->

    <!-- Footer -->
    <footer class="footer">
        2018 © "Друзья" автор Артём Пьянов
    </footer>
    <!-- End Footer -->
</section>

<div class="demo-options" >
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
<#--<script src="/js/jquery-2.1.4.min.js"></script>-->
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
