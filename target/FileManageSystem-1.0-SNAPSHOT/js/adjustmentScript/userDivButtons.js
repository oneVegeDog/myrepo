

let divButtons = document.getElementsByClassName("divButton");
let divHeightAddSpacing = 55;//div区块的高加间距
let firstTop = 100;//第一个距离顶部的间距
for(let i = 0;i<divButtons.length;i++){
    divButtons[i].style.top =firstTop+divHeightAddSpacing*i+"px";
    divButtons[i].onmouseover = function (){
        divButtons[i].style.background ="lightskyblue";
    }
    divButtons[i].onmouseout = function () {
        divButtons[i].style.background ="deepskyblue";
    }
    divButtons[i].onclick = function () {
        divButtons[i].style.background = "aliceblue";
        location.href = divButtons[i].id+".html";
    }
}
