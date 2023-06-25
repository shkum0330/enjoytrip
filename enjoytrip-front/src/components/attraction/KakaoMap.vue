d
<template>
  <div class="kakao">
    <div id="map"></div>
    <div id="info">{{ markerInfo ? markerInfo.title : "" }}</div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markerInfo: null, // 클릭한 마커의 정보를 저장할 데이터
      overlay: null, // 커스텀 오버레이 객체를 저장할 데이터
    };
  },

  mounted() {
    if (window.kakao && window.kakao.maps) {
      // 카카오 객체가 있고, 카카오 맵 그릴 준비가 되어 있다면 맵 실행
      this.loadMap();
    } else {
      // 없다면 카카오 스크립트 추가 후 맵 실행
      this.loadScript();
    }
  },
  computed: {
    ...mapState({
      attractionList: (state) => state.attractionList,
    }),
  },
  watch: {
    attractionList() {
      if (this.attractionList != "") {
        this.loadAttractionListMaker();
      } else {
        alert("정보가 없습니다.");
      }
    },
  },

  methods: {
    loadScript() {
      const script = document.createElement("script");
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=" + this.$serviceKey + "&autoload=false";
      script.onload = () => window.kakao.maps.load(() => this.loadMap());
      document.head.appendChild(script);
    },

    loadMap() {
      const container = document.getElementById("map");
      const options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };
      this.map = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

      this.loadStartMaker();
      this.loadController();
      this.addClickEvent();

      //
    },

    // map이 호출될때 첫 세팅
    loadStartMaker() {
      //지정한 위치에 마커 불러옴
      const markerPosition = new window.kakao.maps.LatLng(33.450701, 126.570667);

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
      });
      marker.setMap(this.map);
    },

    //지도에 컨트롤러 추가
    loadController() {
      // 일반, 스카이뷰 전환 컨트롤
      const mapTypeControl = new window.kakao.maps.MapTypeControl();
      this.map.addControl(mapTypeControl, window.kakao.maps.ControlPosition.TOPRIGHT);

      // 확대 컨트롤
      const zoomControl = new window.kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
    },

    // 지도에 클릭 이벤트 등록
    // 1. 클릭한 위치에 마커 표시
    // 2. 클릭한 위치로 중심 좌표 이동
    addClickEvent() {
      let prevMarker = null; // 이전 마커 객체를 저장할 변수

      window.kakao.maps.event.addListener(this.map, "click", (mouseEvent) => {
        var latlng = mouseEvent.latLng;

        // 이전 마커가 있을 경우, 이전 마커를 지웁니다.
        if (prevMarker) {
          prevMarker.setMap(null);
        }

        // 새로운 마커를 표시합니다.
        var marker = new window.kakao.maps.Marker({
          position: latlng,
          map: this.map,
        });

        // 이전 마커 객체를 현재 마커로 업데이트합니다.
        prevMarker = marker;
      });
    },

    //검색 조건에 따른 마커 생성 및 지도 위치 이동
    loadAttractionListMaker() {
      var self = this;
      var positions = [];
      var hapLatitude = 0;
      var hapLongitude = 0;
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      var imageSize = new window.kakao.maps.Size(24, 35);
      var markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);

      // 오버레이를 커스텀하기 위한 HTML 템플릿

      for (let i = 0; i < this.attractionList.length; i++) {
        positions[i] = {
          title: this.attractionList[i].title,
          latlng: new window.kakao.maps.LatLng(
            this.attractionList[i].latitude,
            this.attractionList[i].longitude
          ),
          address: this.attractionList[i].addr1,
          imageSrc: this.attractionList[i].firstImage,
        };
        hapLatitude += this.attractionList[i].latitude;
        hapLongitude += this.attractionList[i].longitude;

        var marker = new window.kakao.maps.Marker({
          map: this.map,
          position: positions[i].latlng,
          title: positions[i].title,
          image: markerImage, // 마커 이미지
          // ...
        });
        // 오버레이를 커스텀하기 위한 HTML 템플릿

        // 클릭 이벤트의 리스너를 정의합니다.
        var clickListener = function (event) {
          event.stopPropagation();
        };

        //클릭 이벤트 등록
        window.kakao.maps.event.addListener(
          marker,
          "click",
          (function (overlay, map, position) {
            return function () {
              //오버레이에 표시할 컨텐츠 생성
              var overlayContent = `
                <div class="card"   onclick="event.stopPropagation()">
                  <div class="card-header bg-warning text-white" style="display: flex; justify-content: space-between;">
                    ${positions[i].title}
                    <button type="button" class="btn" onclick="event.target.parentNode.parentNode.parentNode.remove()">&times;</button>
                  </div>
                  <div class="card-body d-flex align-items-center">
                    <div class="mr-3">
                      <img src="${positions[i].imageSrc}" style="width: 100px; height: 100px; margin:10px;">
                    </div>
                    <div>
                      <p class="card-text text-truncate">${positions[i].address}</p>
                      <buttton id="overlayButton${i}" type="button" class="btn btn-link p-0"> 링크 이동 </buttton>
                    </div>
                  </div>
                </div>`;

              // 커스텀 오버레이 객체 생성
              var overlay = new window.kakao.maps.CustomOverlay({
                content: overlayContent,
                position: positions[i].latlng,
                yAnchor: 1,
                clickable: true, // 오버레이가 클릭 가능하도록 설정
              });

              // 커스텀 오버레이 객체 생성 후...
              var intervalId = setInterval(() => {
                var overlayButton = document.getElementById(`overlayButton${i}`);
                if (overlayButton) {
                  overlayButton.addEventListener("click", () => self.searchByTitle(positions[i]));
                  clearInterval(intervalId); // Element found, so no need to check again.
                }
              }, 100); // Check every 100ms

              // 이벤트 리스너 추가
              window.kakao.maps.event.addListener(marker, overlay, "click", clickListener);

              // 해당 마커의 오버레이를 표시
              overlay.setContent(overlayContent); // 컨텐츠 업데이트
              overlay.setPosition(position); // 위치 업데이트
              overlay.setMap(map);

              // 오버레이를 닫을 때 이벤트 리스너 제거
              window.kakao.maps.event.addListener(overlay, "closeclick", function () {
                overlay.setMap(null);
                // 이전 이벤트 리스너 제거
                window.kakao.maps.event.removeListener(marker, overlay, "click", clickListener);
              });
            };
          })(this.overlay, this.map, positions[i].latlng)
        );
      }

      var avgLatitude = hapLatitude / this.attractionList.length;
      var avgLongitude = hapLongitude / this.attractionList.length;
      var avgLatLng = new window.kakao.maps.LatLng(avgLatitude, avgLongitude);

      //지도 중심 위치 변경
      this.map.setCenter(avgLatLng);
      this.map.setLevel(8);
    },
    closeOverlay() {
      this.overlay.setMap(null);
    },
    searchByTitle(data) {
      console.log(data);
      window.open(`https://map.kakao.com/?q=${data.title}`);
    },
  },
  //커스텀 오버레이 닫는 함수
};
</script>
