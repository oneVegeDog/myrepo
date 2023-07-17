var getFileTree = "getFileTree";//取得文件树的映射



var downloadFile = "dowmloadFile";//下载文件

class FileTreeNode {
    fileId;
    fileName;
    sonList;//子节点
    parentNode;//父节点
    parentId;//父节点的id
    parentName;//父节点的名字
    left;
    right;
    isFolder;
    constructor(fileId,fileName,sonList,parentNode,parentId,parentName,left,right,isFolder) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.sonList = sonList;
        this.parentNode = parentNode;
        this.parentId = parentId;
        this.parentName = parentName;
        this.left = left;
        this.right = right;
        this.isFolder = isFolder;
    }
}


var fileTreeVue = new Vue({
    el:"#myfilevuebox",
    data(){
        return{
            currentFile:"无当前节点",
            fileTree : JSON.parse(sessionStorage.getItem("fileTree"))
        }
    },
    methods:{
        async sendRequest(){
            if(this.fileTree==null){
                await axios.get("../"+getFileTree).then(resp=>{
                    fileTreeVue.fileTree = resp.data;
                    sessionStorage.setItem("fileTree", myStringify(fileTreeVue.fileTree));
                });
            }
        }
    },
    mounted(){
        this.sendRequest().then(r=>{
            let htmltext = getTreeHtml(this.fileTree);
            // alert(htmltext);
            document.getElementById("fileTree").innerHTML=htmltext;
            setParent(null,this.fileTree);
            bindClick(this.fileTree)
        });
    }
});

// function getTreeHtml(childs){
//     let html = "";
//     for(let i=0;i<childs.length;i++){
//         html+="<details> <summary><span class='tree-item' title='"+childs[i].fileName+"' id='"+childs[i].fileId+"'>"+childs[i].fileName+"</span> </summary>";
//         if (childs[i].sonList && childs[i].sonList.length) {
//             html += getTreeHtml(childs[i].sonList); // 如果有chidren就继续遍历
//         }
//         html+= '</details>';
//     }
//     return html;
// }
//
// function bindClick(childs) {
//     for(let i=0;i<childs.length;i++){
//         let node = document.getElementById(childs[i].fileId);
//         node.onclick=function () {
//             clickNode(childs[i]);
//         }
//         if(childs[i].isFolder==0){
//             node.ondblclick=function () {
//                 dbclickNode(childs[i]);
//             }
//         }
//         if (childs[i].sonList && childs[i].sonList.length) {
//             bindClick(childs[i].sonList);
//         }
//     }
// }
//
// function clickNode(node) {
//     fileTreeVue.currentFile = node.fileName;
//     currentNode = node;
// }
//
// function dbclickNode(node) {
//     let contentBox = document.getElementById("contentBox");
//     contentBox.innerHTML = "";
//     let fileName = node.fileName;
//     let fileId = node.fileId
//     axios.get("../preview?fileName="+fileId,{"Pragma":"no-cache","Cache-Control":"no-cache"}).then(async resp=>{
//         let fileUrl = resp.data;
//         console.log(fileUrl);
//         if( fileName.endsWith(".md")){
//             axios({url : fileUrl,
//                 method:'get',
//             }).then(resp=>{
//                 let md = resp.data;
//                 contentBox.innerHTML="<p id='contentText' style='color: aliceblue;background: unset;width: calc(100% - 250px);height: calc(100% - 90px);overflow: scroll'  scrolling='auto'>"+marked(md)+"</p>";
//             });
//         }else if(fileName.endsWith(".txt")||fileName.endsWith(".docx")){
//             axios.get(fileUrl,"responseType:'text/plain'").then(resp=>{
//                 contentBox.innerHTML="<p id='contentText' style='color: aliceblue;background: unset;width: calc(100% - 250px);height: calc(100% - 90px);overflow: scroll'  scrolling='auto'>"+resp.data+"</p>";
//             });
//         }else{
//             contentBox.insertAdjacentHTML("afterbegin","<embed id='content' src='"+fileUrl+"'>");
//         }
//
//     });
// }
//
// function getPath(node){
//     let path = "";
//     while(node!=null){
//         path = "/"+node.fileName+path;
//         node = node.parentNode;
//     }
//     return path;
// }
//
// function alterLeftAndRight(childs,alteration,originNodeLeft){
//     for(let i=0;i<childs.length;i++){
//         if(childs[i].left>=originNodeLeft){
//             childs[i].left+=alteration;
//         }
//         if(childs[i].right>=originNodeLeft){
//             childs[i].right+=alteration;
//         }
//         if (childs[i].sonList && childs[i].sonList.length) {
//             alterLeftAndRight(childs[i].sonList,alteration,originNodeLeft);
//         }
//     }
// }
//
// //由于stringify双向树的时候会报错，所以存到sessionStorage里的还是单向树，取出来之后再来指定父节点，变成双向树
// function myStringify(ob) {
//     let cache = [];
//     let str = JSON.stringify(ob, function(key, value) {
//         if (typeof value === 'object' && value !== null) {
//             if (cache.indexOf(value) !== -1) {
//                 // 移除
//                 return;
//             }
//             // 收集所有的值
//             cache.push(value);
//         }
//         return value;
//     });
//     cache = null; // 清空变量，便于垃圾回收机制回收
//     return str;
// }
//
// //将单向树变成双向树
// function setParent(parent,childs) {
//     for(let i=0;i<childs.length;i++){
//         childs[i].parentNode = parent;
//         if (childs[i].sonList && childs[i].sonList.length) {
//             setParent(childs[i],childs[i].sonList)
//         }
//     }
// }
//
// function flushFileTree(){
//     document.getElementById("fileTree").innerHTML=getTreeHtml(fileTreeVue.fileTree);
//     bindClick(fileTreeVue.fileTree);
//     currentNode=null;
//     fileTreeVue.currentFile = "无当前文件";
//     sessionStorage.setItem("fileTree",myStringify(fileTreeVue.fileTree));
// }

// function getFileFromUrl(url, fileName) {
//     return new Promise((resolve, reject) => {
//         var blob = null;
//         var xhr = new XMLHttpRequest();
//         xhr.open("GET", url);
//         xhr.setRequestHeader('Accept', 'text/plain');
//         xhr.responseType = "blob";
//         // 加载时处理
//         xhr.onload = () => {
//             // 获取返回结果
//             blob = xhr.response;
//             let file= new File([blob], fileName, { type: 'image/png' });
//             // 返回结果
//             resolve(file);
//         };
//         xhr.onerror = (e) => {
//             reject(e)
//         };
//         // 发送
//         xhr.send();
//     });
// }