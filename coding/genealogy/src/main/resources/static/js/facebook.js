var url = window.location.toString();

var code = getParameterByName("code", url);
var state = getParameterByName("state", url);

getLoginButtonUri();
function getLoginButtonUri() {
    $.ajax({
        url: "/facebook/getLoginUri",
        success: function (res, status) {
            $("#loginFacebook").attr("href", res);
        }
    })
}

isAuthenticated();

function isAuthenticated() {
    $.ajax({
        url: "/facebook/auth",
        success: function (res, status) {
            console.log("Authenticated: ", res)
            if (res === true) {
                window.location.href ="/";
            }
            else {

            }
        },
        error: function (res, status) {
            console.error(res, status)
        }
    });
}

function showUserInfo() {
    $.ajax({
        url: "/facebook/userinfo",
        success: function (res, status) {
            console.log(res);
            $("#userinfo").html(JSON.stringify(res, null, 2));
        }
    })
}

$("#logout").click(function () {
    $.ajax({
        url: "/facebook/logout",
        success: function (res, status) {
            console.log(res)
            window.location.replace("./")
        }
    })
});

if (code && state) {
    $.ajax({
        url: "/facebook/login?code=" + code + "&state=" + state,
        success: function (res, status) {
            console.log(status);
            window.location.replace("./")
        }
    });
}

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}