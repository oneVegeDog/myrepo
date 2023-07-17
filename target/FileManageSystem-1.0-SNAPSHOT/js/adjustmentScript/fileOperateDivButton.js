let distance = 10;
let divButtons = document.getElementsByClassName("divButton");
for(let i =0;i<divButtons.length;i++){

    divButtons[i].onmouseover = function (){
        divButtons[i].style.background ="lightskyblue";
    }
    divButtons[i].onmouseout = function () {
        divButtons[i].style.background ="deepskyblue";
    }
    divButtons[i].onclick = function () {
        divButtons[i].style.background = "aliceblue";
    }
}