



document.getElementById("download").onclick=function () {
    if(currentNode==null){
        alert("未选中文件");
        return;
    }
    if(currentNode.isFolder == 1 ){
        alert("无法下载文件夹");
    }
    axios.get("http://localhost:8080/FileManageSystem/preview?fileName="+currentNode.fileId,{"Pragma":"no-cache","Cache-Control":"no-cache"}).then(async resp=>{
        let fileUrl = resp.data;
        downloadUrlFile(fileUrl,currentNode.fileName);
    });
}

function saveAs(data, name) {
    const urlObject = window.URL || window.webkitURL || window;
    const export_blob = new Blob([data]);
    const save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
    save_link.href = urlObject.createObjectURL(export_blob);
    save_link.download = name;
    save_link.click();
    let formData3 = new FormData()
    formData3.append('taskid', id);
    postAction(_that.downloadOver, formData3)
}
// 下载含有url的文件
function downloadUrlFile(url, fileName) {
    const url2 = url.replace(/\\/g, '/');
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url2, true);
    xhr.responseType = 'blob';
    //xhr.setRequestHeader('Authorization', 'Basic a2VybWl0Omtlcm1pdA==');
    // 为了避免大文件影响用户体验，建议加loading
    xhr.onload = () => {
        if (xhr.status === 200) {
            // 获取文件blob数据并保存
            saveAs(xhr.response, fileName);
        }
    };
    xhr.send();
}