$(document).on('ready', function () {
    const accessToken = localStorage.getItem("access_token")
    
    
    if (accessToken){
            window.location.replace("/ui/home")
    }else{
        window.location.replace("/ui/login")
    }
});