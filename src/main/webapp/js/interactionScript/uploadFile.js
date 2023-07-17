
var selectFile = document.createElement("input");
selectFile.type= "file";
selectFile.setAttribute("style",'visibility:hidden');
document.body.appendChild(selectFile);
document.getElementById("upload").onclick=function () {
    if(currentNode==null){
        alert("未选中新建的文件夹的位置");
        return;
    }
    if(currentNode.isFolder==0){
        alert("选中的不是目录");
        return;
    }
    selectFile.click();
    // var inputObj=document.createElement('input')
    //
    // inputObj.setAttribute('id','_ef');
    //
    // inputObj.setAttribute('type','file');
    //
    // inputObj.setAttribute("style",'visibility:hidden');
    //
    // document.body.appendChild(inputObj);
    // document.getElementById("bu").onclick=function (){
    //     inputObj.dispatchEvent(new MouseEvent("click"));
    // }
}

selectFile.onchange = function () {
    let file = selectFile.files[0];
    if(typeof file == "object"){
        let flag = true;
        let uploadFileName=null;
        while(flag){
            uploadFileName = prompt("请输入上传后的名字",file.name);
            console.log(uploadFileName);
            let end = file.name.substring(file.name.lastIndexOf('.'));
            alert(end);
            if(uploadFileName.endsWith(end)){
                flag=false;
            }else{
                alert("不要改后缀名");
            }
        }
        if(uploadFileName == null || uploadFileName =="null"){

        }else{
            let formData = new FormData();
            formData.append("parentId",currentNode.fileId);
            formData.append("parentName",currentNode.fileName);
            formData.append("leftNum",(currentNode.left+1));
            formData.append("file",file);
            formData.append("fileName",uploadFileName);
            console.log("0"+uploadFileName);
            axios.post("../uploadFile",
                formData
            ).then(resp=>{
                let message = resp.data;
                if(message.statue==200){
                    let newnode = JSON.parse(message.jsonstr);
                    //更改各个节点的左右值，由于是增加节点，所以更改值传入的是+2
                    alterLeftAndRight(fileTreeVue.fileTree,2,currentNode.left+1)
                    // let newnode = JSON.parse(datas[1]);
                    currentNode.sonList.push(newnode);
                    newnode.parentNode=currentNode;
                    flushFileTree();
                }else {
                    alert(message.description);
                }
            });
        }
    }
}