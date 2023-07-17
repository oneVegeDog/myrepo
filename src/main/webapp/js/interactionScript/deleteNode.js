var deleteFile = "deleteFile";//删除文件

var deleteNode = document.getElementById("delete");
deleteNode.onclick=function () {
    if(currentNode==null){
        alert("当前节点为空");
        return;
    }
    if(currentNode==fileTreeVue.fileTree[0]){
        alert("不能删除根目录");
        return;
    }
    if(!confirm("确认删除当前文件？")){
        return;
    }
    axios("../"+deleteFile+"?flieId="+currentNode.fileId+"&left="+currentNode.left+"&right="+currentNode.right+"&path="+getPath(currentNode)).then(resp=> {
        if(resp.data.statue == 200){
            currentNode.parentNode.sonList.remove(currentNode);
            alterLeftAndRight(fileTreeVue.fileTree,-(currentNode.right-currentNode.left+1),currentNode.left);
            flushFileTree();
            // document.getElementById("fileTree").innerHTML=getTreeHtml(fileTreeVue.fileTree);
            // bindClick(fileTreeVue.fileTree);
            // currentNode=null;
            // fileTreeVue.currentFile = "无当前文件";
            // sessionStorage.setItem("fileTree",myStringify(fileTreeVue.fileTree));
            //test(fileTreeVue.fileTree);
        }else if(resp.data.statue == 500){
            alert("删除失败,稍后再试");
        }
    });
}

Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};

Array.prototype.remove = function(val) {
    let index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
}