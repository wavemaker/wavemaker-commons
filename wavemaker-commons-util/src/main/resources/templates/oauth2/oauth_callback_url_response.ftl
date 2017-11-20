<!DOCTYPE html>
<html>
<head>
 <title>GetAccessToken</title>
</head>
<script type="text/javascript">
function poponload(){
    var requestSourceType = "${requestSourceType}",
        dest = "://services/oauth/${providerId}?access_token=${accessToken}";
    if (requestSourceType === "MOBILE") {
        location.href = "${customUrlScheme}" + dest;
    } else if (requestSourceType === "WAVELENS") {
        location.href = "com.wavemaker.wavelens" + dest;
    } else {
        localStorage.setItem("${providerId}.access_token","${accessToken}");
        if (window.opener) {
            window.opener.postMessage("oauth_success", window.location.origin);
        }
        window.close();
    }
}
</script>
<body onload="javascript: poponload()"/>
</html>


