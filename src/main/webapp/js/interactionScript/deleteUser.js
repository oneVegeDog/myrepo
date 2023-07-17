document.getElementById("deleteUser").onclick = function () {
    axios.get("http://localhost:8080/FileManageSystem/deleteUser").then(resp=>{
        let message = resp.data;
        if(message.statue==200){
            sessionStorage.clear();
            location.href="http://localhost:8080/FileManageSystem/index.html";
        }else{
            alert(message.description);
        }
    });
}