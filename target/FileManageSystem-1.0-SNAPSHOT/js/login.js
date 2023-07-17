
var loginMapping = "login";//登录的映射
var boxvue = new Vue({
    el:"#loginbox",
    data(){
        return{
            username_error_message:"",
            password_error_message:"",
            username:"",
            password:""
        }
    },
    methods:{
        //通过axios获取登录的状态码，0表示用户名不存在，1表示用户名存在但密码错误，2表示成功登录
        submitData(){
            if(boxvue.username_error_message==""&&boxvue.password_error_message==""){
                axios.get(loginMapping+"?username="+boxvue.username+"&password="+boxvue.password).then(function (resp) {
                    if(resp.data=='6'){
                        boxvue.username_error_message="用户名错误";
                    }else if(resp.data=='7'){
                        boxvue.password_error_message="密码不合法";
                    }else if(resp.data=='0'){
                        boxvue.username_error_message = "用户不存在";
                    }else if(resp.data=='1'){
                        boxvue.password_error_message = "密码错误";
                    }else if(resp.data=="3"){
                        boxvue.password_error_message = "用户被冻结";
                    }else {
                        location.href = resp.data;
                    }
                });
            }
        },
        usernameErrorMessage(){
            if(!/^\w{2,10}$/.test(boxvue.username)){
                this.username_error_message = "用户名不合法";
            }else{
                this.username_error_message ="";
            }
        },
        passwordErrorMessage(){
            if(!/^[a-zA-Z0-9]{6,20}$/.test(boxvue.password)){
                this.password_error_message = "密码不合法";
            }else{
                this.password_error_message = "";
            }
        }
    }
});