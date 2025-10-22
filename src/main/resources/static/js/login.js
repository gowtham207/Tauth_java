$('#login-form').on('submit',(event)=>{
    event.preventDefault()
    const payload = {
        email:$('#email').val(),
        password:$('#password').val()
    }
})

console.log("hello world ");

