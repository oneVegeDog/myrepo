

let keyword = document.getElementById("keyword");
let searchFiles = document.getElementById("searchFiles");

document.getElementById("search").onclick = function () {
    searchFiles.innerHTML = "";
    if(keyword.value == ""||keyword.value==null){
        alert("关键词为空");
    }else{
        searchFileTree(fileTreeVue.fileTree);
    }
}



//将选择的路径切割成一个字符串数组，交给遍历函数，搜索到指定的节点
searchFiles.onchange = function () {
    let folderNames = searchFiles.value.split("/");
    folderNames.splice(0,1);
    searchFileByPath(fileTreeVue.fileTree,folderNames,0);
}

function searchFileTree(childs) {
    for(let i=0;i<childs.length;i++){
        if(childs[i].fileName == keyword.value){
            searchFiles.insertAdjacentHTML("beforeend","<option>"+getPath(childs[i])+"</option>");
        }
        if (childs[i].sonList && childs[i].sonList.length) {
            searchFileTree(childs[i].sonList)
        }
    }
}

function searchFileByPath(childs,folderNames,cengNum) {
    for(let i=0;i<childs.length;i++){
        if(childs[i].fileName == folderNames[cengNum]){
            if (childs[i].sonList && childs[i].sonList.length) {
                searchFileByPath(childs[i].sonList, folderNames, cengNum + 1);
            }else{
                currentNode = childs[i];
                fileTreeVue.currentFile = childs[i].fileName;
                dbclickNode(childs[i]);
            }
        }
    }
}