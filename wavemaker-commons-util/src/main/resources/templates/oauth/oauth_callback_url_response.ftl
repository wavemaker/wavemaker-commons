<!DOCTYPE html>
<html>
<head>
 <title>GetAccessToken</title>
</head>
<script type="text/javascript">
function poponload(){
    localStorage.setItem("${providerId}.access_token","${accessToken}");
    if (window.opener) {
        window.opener.postMessage("oauth_success", window.location.origin);
    }
    window.close();
}
</script>
<body onload="javascript: poponload()"/>
</html>


