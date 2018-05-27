<#ftl encoding='UTF-8'>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<#if error??>
<div class="alert alert-danger" role="alert">Логин или пароль введены неверно</div>
</#if>
<div class="form-style-2">
    <div class="form-style-2-heading">
        Please Login!
    </div>
    <form method="post" action="/login">
        <label for="login">Логин
            <input class="input-field" type="text" id="login" name="login">
        </label>
        <label for="password">Пароль
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <label for="remember-me">
            <input type="checkbox" id="remember-me" name="remember-me">Запомнить меня</label>
        <input type="submit" value="Login">
    </form>
</div>
</body>
</html>