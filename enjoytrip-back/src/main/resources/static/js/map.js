const serviceKey = "xohx8FNZ2AOMvKIlBj6xwvHxTNQNf9bSGX6vLU6syrufnLtC34mdrC4mgHKUDfektfP0IjEzRNjSoKEJPI6B2Q==";

window.onload = function () {

    getSido();  // 드롭다운에 API로 받아온 시/도 선택지 추가함
    document.getElementById("search-sido").onchange = function () {
        const sidoCode = getSelectedValue("search-sido");
        getGugun(sidoCode)
    };
};

function getSido() {
    // index page 로딩 후 전국의 시도 설정.
    const url =
        "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
        serviceKey +
        "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

    fetch(url)
        .then((response) => response.json())
        .then((data) => makeOption(data, "search-sido", "시/도"));
}

function getGugun(sidoCode) {

    const url =
        "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
        serviceKey +
        "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=" + sidoCode + "&_type=json"

    fetch(url)
        .then((response) => response.json())
        .then((data) => makeOption(data, "search-gugun", "시/군/구"));

}

function makeOption(data, id, text) {
    let areas = data.response.body.items.item;
    let sel = document.getElementById(id);
    sel.replaceChildren();

    let init = document.createElement("option");
    init.setAttribute("value", 0);
    init.appendChild(document.createTextNode(text));
    sel.appendChild(init);

    areas.forEach((area) => {
        let opt = document.createElement("option");
        opt.setAttribute("value", area.code);
        opt.appendChild(document.createTextNode(area.name));
        sel.appendChild(opt);
    });
}

// 검색 버튼을 누르면..
// 지역, 유형, 검색어 얻기.
// 위 데이터를 가지고 공공데이터에 요청.
// 받은 데이터를 이용하여 화면 구성.
document.getElementById("btn-search").addEventListener("click", () => {

    const sidoCode = getSelectedValue("search-sido");
    const gugunCode = getSelectedValue("search-gugun");
    const contentTypeId = getSelectedValue("search-content-id");

    const url = "https://apis.data.go.kr/B551011/KorService1/areaBasedList1?serviceKey=" +
        serviceKey +
        "&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A" +
        "&contentTypeId=" + contentTypeId +
        "&areaCode=" + sidoCode +
        "&sigunguCode=" + gugunCode;

    fetch(url)
        .then((response) => response.json())
        .then((data) => makeList(data));
});

function getSelectedValue(selectId) {
    let target = document.getElementById(selectId);
    return target.value;
}

let positions; // marker 배열.
let markers;

// 카카오지도
var mapContainer = document.getElementById("map"), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

async function makeList(data) {

    document.querySelector("table").setAttribute("style", "display: ;");
    let trips = data.response.body.items.item;
    let tripList = ``;
    if (markers != null) {
        for (i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
        }
    }

    markers = [];
    positions = [];
    let count = 1;
    for (const area of trips) {
        tripList += `
            <tr onclick="moveCenter(${area.mapy}, ${area.mapx});">
              <td><img src="${area.firstimage}" width="100px"></td>
              <td>${area.title}</td>
              <td>${area.addr1} ${area.addr2}</td>
              <td>${area.mapy}</td>
              <td>${area.mapx}</td>
            </tr>
          `;
        let content = '<div class="overlay_info">';
        content += '    <a href="#" data-bs-toggle="modal" data-bs-target="#attractionDescModal' + count + '"><strong>';
        content += area.title;
        content += '</strong></a>';
        content += '    <div class="desc">';
        content += '        <img src="';
        content += area.firstimage;
        content += '" width="80px">';
        content += '        <span class="address">';
        content += area.addr1;
        content += ' ';
        content += area.addr2;
        content += '</span>';
        content += '    </div>';
        content += '</div>';

        let modal = ""
        let data = await getDescription(area.contentid);
        data = data.response.body.items.item[0];
        //console.log(data);
        modal = '<div class="modal" id="attractionDescModal' + count + '" tabindex="-1">';
        modal += '<div class="modal-dialog">';
        modal += '<div class="modal-content">';
        modal += '<div class="modal-header">';
        modal += '<h5 class="modal-title">자세히 보기</h5>';
        modal += '<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button></div>';
        modal += '<div class="modal-body">';
        modal += '<img class="img-fluid" src="' + data.firstimage + '">';
        modal += '<h1>' + data.title + '</h1>';
        modal += '<h6>주소</h6>';
        modal += '<h6>' + data.addr1 + '</h6>';
        modal += '<h6>상세설명</h6>';
        modal += '<h6>' + data.overview + '</h6>';
        modal += '</div></div></div></div>';

        let modalDiv = document.getElementById("desc-modal");
        let childDiv = document.createElement("div");
        childDiv.innerHTML = modal;
        //console.log(childDiv);
        modalDiv.appendChild(childDiv);
        //console.log(modalDiv);


        let markerInfo = {
            title: area.title,
            contenttypeid: area.contenttypeid,
            latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
            content: content
        };
        //<button id="btn-select" class="btn btn-outline-success" type="button">선택</button>
        positions.push(markerInfo);

        count++;
    }
    document.getElementById("trip-list").innerHTML = tripList;
    displayMarker();
}

function getDescription(contentid) {
    let url = "https://apis.data.go.kr/B551011/KorService1/detailCommon1?serviceKey=" +
        serviceKey +
        "&MobileOS=ETC&MobileApp=AppTest&_type=json&contentId=" +
        contentid +
        "&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&numOfRows=10&pageNo=1";

    const response = fetch(url);
    return response.then((result) => result.json());
}

function displayMarker() {
    // 마커 이미지의 이미지 주소입니다
    var bounds = new kakao.maps.LatLngBounds();

    for (var i = 0; i < positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        let imageSize = new kakao.maps.Size(24, 35);
        let imageSrc = `../img/markers/${positions[i].contenttypeid}.png`;

        // 마커 이미지를 생성합니다
        let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
            clickable: true
        });
        let iwRemoveable = true;

        // 마커에 표시할 인포윈도우를 생성합니다
        let infowindow = new kakao.maps.InfoWindow({
            content: positions[i].content, // 인포윈도우에 표시할 내용
            removable: iwRemoveable
        });

        // 마커에 click 이벤트를 등록합니다
        kakao.maps.event.addListener(marker, 'click', function () {
            infowindow.open(map, marker);
        });

        markers.push(marker);
        bounds.extend(positions[i].latlng);
    }

    // 생성된 마커들의 위치를 기준으로 맵을 움직입니다.
    map.setBounds(bounds);
}

// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, marker, infowindow) {
    return function () {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
    return function () {
        infowindow.close();
    };
}
