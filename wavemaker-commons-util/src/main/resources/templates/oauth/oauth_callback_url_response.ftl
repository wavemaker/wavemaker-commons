<!DOCTYPE html>
<html>
<head>
 <title>GetAccessToken</title>
</head>
<script type="text/javascript">
function poponload(){
        localStorage.setItem("${providerId}.access_token","${accessToken}");
        window.close();
}
</script>
<body onload="javascript: poponload()"/>
</html>


