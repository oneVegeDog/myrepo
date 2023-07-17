var newFolder = "newFolder";//新建文件夹
document.getElementById("newFolder").onclick=function () {
    if(currentNode==null){
        alert("未选中新建的文件夹的位置");
        return;
    }
    if(currentNode.isFolder==0){
        alert("选中的不是目录");
        return;
    }
    let foldername = prompt("请输入文件夹的名字：");
    if(foldername!=null){
        if(/^\w{2,10}$/.test(foldername)){
            axios.get("../"+newFolder+"?path="+getPath(currentNode)+"&fileName="+foldername+"&left="+(currentNode.left+1)+"&parentId="+currentNode.fileId+"&parentName="+currentNode.fileName).then(resp=>{
                let message = resp.data;
                if(message.statue == 200){
                    let newnode = JSON.parse(message.jsonstr);
                    //更改各个节点的左右值，由于是增加节点，所以更改值传入的是+2
                    alterLeftAndRight(fileTreeVue.fileTree,2,currentNode.left+1)
                    // let newnode = JSON.parse(datas[1]);
                    currentNode.sonList.push(newnode);
                    newnode.parentNode=currentNode;
                    flushFileTree();
                    // let htmltext = getTreeHtml(fileTreeVue.fileTree);
                    // document.getElementById("fileTree").innerHTML=htmltext;
                    // bindClick(fileTreeVue.fileTree);
                    // sessionStorage.setItem("fileTree",myStringify(fileTreeVue.fileTree));
                }else if(message.statue==401){
                    alert("文件名在同级目录中已存在");
                }else{
                    alert("服务器出错，稍后再试");
                }
            });
        }else{
            alert("文件名不合法");
        }
    }
}

function test(childs) {
    for(let i=0;i<childs.length;i++){
        alert(childs[i].fileName+"-left:"+childs[i].left+"-right:"+childs[i].right);
        if (childs[i].sonList && childs[i].sonList.length) {
            test(childs[i].sonList);
        }
    }
}