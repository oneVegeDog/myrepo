
var copyuseranme = "";

let save = document.getElementById("save");
let cancel = document.getElementById("cancel");
let myFile = document.getElementById("myFile");
let authorizedFile = document.getElementById("authorizedFile");
let poweredFile = document.getElementById("poweredFile");
let alterUserInfo = document.getElementById("alterUserInfo");
let username = document.getElementById("username");

alterUserInfo.onclick = function () {
    myFile.style.pointerEvents = "none";
    authorizedFile.style.pointerEvents = "none";
    poweredFile.style.pointerEvents = "none";

    username.contentEditable = "true";

    copyuseranme = username.innerText;

    save.style.display = "";
    cancel.style.display = "";

    alterUserInfo.style.pointerEvents = "none";
}

save.onclick = function () {
    if(confirm("确认保存？")){
        userinfo.vueuser.username = username.innerText;
        alert(userinfo.vueuser.username);
        let userJson = JSON.stringify(userinfo.vueuser);
        let form = new FormData();
        form.append("userJson",userJson);

        axios.post("http://localhost:8080/FileManageSystem/alterUserInfo",form).then(resp=>{
            let message = resp.data;
            if(message.statue==200){
                afterChangeButton();
                sessionStorage.setItem("userInfo",userJson);
            }else{
                alert("服务器出错，稍后再试");
            }
        });
    }
}
cancel.onclick = function () {
    userinfo.vueuser = JSON.parse(sessionStorage.getItem("userInfo"));
    afterChangeButton();
}

function afterChangeButton(){
    username.contentEditable = "false";
    myFile.style.pointerEvents = "";
    authorizedFile.style.pointerEvents = "";
    poweredFile.style.pointerEvents = "";
    alterUserInfo.style.pointerEvents = "";
    save.style.display ="none";
    cancel.style.display = "none";
}