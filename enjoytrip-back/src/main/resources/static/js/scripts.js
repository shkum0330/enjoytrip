window.onload = function () {
    index = 1;
    mainBg = document.getElementsByClassName("masthead")[0];
    setInterval(function () {
        index = (index + 1) % 5 + 1;
        mainBg.style.backgroundImage = "url('" + "./img/mainBgImg/mBgImg " + index + ".jpg" + "')";
    }, 2000);
}


const logInId = document.getElementById("idTxt");
const logInPW = document.getElementById("pwTxt");
const scrollBtnLefts = [];
const scrollBtnRights = [];
const scrollLists = [];
const scrollString = ["Plan", "Attr"];


//모든 왼쪽-오른쪽 버튼, 스크롤 리스트 array에 추가
for (let i = 0; i < scrollString.length; i++) {
    scrollBtnLefts.push(document.getElementById("btnScrollLeft"+scrollString[i]));
    scrollBtnRights.push(document.getElementById("btnScrollRight" + scrollString[i]));
    scrollLists.push(document.getElementById("scrollList"+ scrollString[i]));
}


for (let i = 0; i < scrollString.length; i++) {

    scrollBtnLefts[i].addEventListener("click", function ( ){ 
        scrollLists[i].scroll({
            top: 0,
            left: scrollLists[i].scrollLeft - window.innerWidth/10*9,
            behavior: 'smooth'
        });
    });
    
    
    scrollBtnRights[i].addEventListener("click", function () { 
        scrollLists[i].scroll({
            top: 0,
            left: scrollLists[i].scrollLeft + window.innerWidth/10*9,
            behavior: 'smooth'
        });
    });
    
}
