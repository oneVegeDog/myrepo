


let contentTextCopy;
var saveButton = document.getElementById("saveButton");
var cancelButton = document.getElementById("cancelButton");

document.getElementById("modify").onclick = function () {
    if(currentNode == null){
        alert("没有选中文件");
        return;
    }
    let fileName = currentNode.fileName;
    if(fileName.endsWith(".txt")||fileName.endsWith(".md")){
        let contentText = document.getElementById("contentText");
        contentText.contentEditable = "true";
        contentTextCopy = contentText.textContent;
        saveButton.style.display = "";
        cancelButton.style.display ="";
    }else{
        alert("选中的文件不可修改");
    }
}

saveButton.onclick = function () {
    if(confirm("确认保存吗？")){
        let contentText = document.getElementById("contentText");
        let form = new FormData();
        form.append("fileId",currentNode.fileId);
        form.append("contentText",contentText.textContent);
        axios.post("http://localhost:8080/FileManageSystem/changeText",
            form,
            {"Content-Type": "multipart/form-data;boundary="+new Date().getTime()}
            ).then(resp=>{
                let message = resp.data;
                if(200==message.statue){
                    contentText.contentEditable="false";
                    saveButton.style.display = "none";
                    cancelButton.style.display = "none";
                }else{
                    alert("修改出现问题，请稍后再试");
                }
        });
    }
}

cancelButton.onclick = function () {
    if(confirm("确认取消并放弃所有修改？")){
        let contentText = document.getElementById("contentText");
        contentText.textContent = contentTextCopy;
        contentText.contentEditable="false";
        saveButton.style.display = "none";
        cancelButton.style.display = "none";
    }
}