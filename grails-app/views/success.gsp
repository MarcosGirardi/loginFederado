<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />
</head>
<body>
    <h1>
        <oauth2:ifLoggedInWith provider="google">yes</oauth2:ifLoggedInWith>
        <oauth2:ifNotLoggedInWith provider="google">no</oauth2:ifNotLoggedInWith>
    </h1>
</body>
</html>