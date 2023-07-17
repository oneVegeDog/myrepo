

var judgeUserNameMapping = "judgeUserName";//判断用户名是否存在的后端servlet映射
var registerMapping = "register";//注册的映射
var registerBox = new Vue({
    el:"#registerbox",
    data(){
        return{
            username_error_message:"",
            password_error_message:"",
            sure_error_message:"",
            register_error_message:"",
            username:"",
            password:"",
            sure_password:""
        }
    },
    methods:{
        //发送请求，若返回0则代表用户已存在，不能注册
        judgeUserName(){
            if(!/^\w{2,10}$/.test(this.username)){
                this.username_error_message = "用户名不合法";
            }else{
                axios.get(judgeUserNameMapping+"?username="+registerBox.username).then(function (resp) {
                    if(resp.data=="0"){
                        registerBox.username_error_message = "用户名已存在";
                    }else{
                        registerBox.username_error_message="";
                    }
                });
            }
        },
        judgePassword(){
            if(!/^[a-zA-Z0-9]{6,20}$/.test(this.password)){
                this.password_error_message = "密码不合法";
            }else{
                this.password_error_message = "";
            }
        },
        surePassword(){
            // alert(this.password+"   "+this.sure_password);
            if(this.password == this.sure_password){
                this.sure_error_message = "";
            }else{
                this.sure_error_message = "密码不相同";
            }
        },
        register(){
            if(this.username_error_message==""&&this.password_error_message==""&&this.sure_error_message==""){
                axios.get(registerMapping+"?username="+registerBox.username+"&password="+registerBox.password).then(resp =>{
                    // alert("请求发送");
                    if(resp.data=="0"){
                        this.register_error_message = "注册失败！稍后再试";
                    }else{
                        location.href = "http://localhost:8080/FileManageSystem/index.html";
                    }
                });
            }
        }
    }
});
