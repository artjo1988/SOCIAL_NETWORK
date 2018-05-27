<#ftl encoding='UTF-8'>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        <h1> Please Sign Up! </h1>
    </div>
    <form method="post" action="/signUp">
        <label for="login"> Login
            <input class="input-field" type="text" id="login" name="login">
        </label>
        <label for="password"> Password
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <label for="first_name"> First name
            <input class="input-field" type="text" id="first_name" name="first_name">
        </label>
        <label for="last_name"> Last name
            <input class="input-field" type="text" id="last_name" name="name">
        </label>
        <label for="data_birthday"> Birth Date
            <input class="input-field" type="text" id="data_birthday" name="data_birthday">
        </label>
        <label for="city"> City
            <input class="input-field" type="text" id="city" name="city">
        </label>
        <label for="e_mail"> E-mail
            <input class="input-field" type="text" id="e_mail" name="city">
        </label>
        <input type="submit" value="Sign Up">
    </form>
</div>
<#--<div class="form-style-2">-->
    <#--<div class="form-style-2-heading">-->
        <#--<h2> Already registered! </h2>-->
    <#--</div>-->
    <#--<table>-->
        <#--<tr>-->
            <#--<th>User name</th>-->
            <#--<th>Birth Date</th>-->
            <#--<th>City</th>-->
        <#--</tr>-->
    <#--<#list usersFromServer as user>-->
        <#--<tr>-->
            <#--<td>${user.login}</td>-->
            <#--<td>${user.firstName}</td>-->
            <#--<td>${user.lastName}</td>-->
        <#--</tr>-->
    <#--</#list>-->
    <#--</table>-->
<#--</div>-->
</body>
</html>

