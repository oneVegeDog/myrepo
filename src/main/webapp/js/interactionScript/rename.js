

let re = document.getElementById("rename");

re.onclick = function (){
    rename(currentNode);
}
function rename(node) {
    if(node==null){
        alert("当前节点为空");
        return;
    }
    if(node==fileTreeVue.fileTree[0]){
        alert("不要重命名根目录");
        return;
    }
    // alert("判断通过");
    let newname = prompt("请输入新名字");
    alert(newname);
    if(newname=="null"||newname==null||newname==""){
        return;
    }
    if(/^[a-zA-Z0-9\\.]{3,20}$/.test(newname)){
        axios.get("../renameFile?newname="+newname+"&fileId="+node.fileId+"&path"+getPath(node)).then(resp=>{
            let message = resp.data;
            if(message.statue==200) {
                node.fileName=newname;
                flushFileTree();
            }else{
                alert(message.description);
            }
        });
    }else{
        alert("新名字不符合规范");
    }
}