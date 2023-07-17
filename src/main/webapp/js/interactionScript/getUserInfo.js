// let myFile = document.getElementById("myFile");
// let poweredFile = document.getElementById("poweredFile");
// let authorizedFile = document.getElementById("authorizedFile");

let getUserInfo = "getUserInfo";//取得用户信息
var userinfo = new Vue({
    el:"#infoBox",
    data(){
        return{
            vueuser:{}
        }
    },
    mounted() {
        vueuser = JSON.parse(sessionStorage.getItem("userInfo"));
        // alert(user);
        if(vueuser==null) {
            axios.get("http://localhost:8080/FileManageSystem/" + getUserInfo).then(function (resp) {
                userinfo.vueuser= resp.data;
                sessionStorage.setItem("userInfo", JSON.stringify(vueuser));
            });
        }

    }
});
