/**
 * HealLink 약국 지도 관리 스크립트
 * [구조]
 * 1. 전역 변수 설정
 * 2. 지도 초기화 로직
 * 3. 데이터 검색 및 처리 로직
 * 4. 지도 위 요소(마커, 원) 표시 로직
 * 5. 사이드바 UI 업데이트 로직
 * 6. 공통 유틸리티 함수
 */

/**
 * HealLink 약국 지도 관리 스크립트
 * [수정 내용]
 * 1. API 무한 호출 방지 (Debouncing 적용)
 * 2. 초기 로딩 시 병원 중심 고정 및 자동 줌 최적화
 * 3. 검색 반경 및 원 크기 동기화 (1.5km)
 */

// ==========================================
// 1. 전역 변수 설정
// ==========================================
let map;
let isFirstSearch = true;
let ps;
let infowindow;
let markers = [];
let currentCircle = null;
let isDetailView = false;
let searchTimer;      // [추가] API 호출 지연을 위한 타이머
let hospitalPos;      // [추가] 병원 위치를 전역으로 저장

let detailOverlay = null; // 약국 클릭하면 네모상자에서 바꾸기 위한 용도 2026-01-21

// ==========================================
// 2. 지도 초기화 로직
// ==========================================

function initPharmacyMap() {
    kakao.maps.load(function () {
        const container = document.getElementById('map');
        if (!container) return; // 지도 컨테이너가 없으면 종료

        // 서버 데이터 읽기
        const serverLat = container.dataset.lat;
        const serverLon = container.dataset.lon;
        const hospitalName = container.dataset.name;

        // [설정] 완전 기본값 (서면) - hospitalPos를 즉시 초기화하여 에러 방지
        const defaultPos = new kakao.maps.LatLng(35.1577, 129.0591);
        hospitalPos = defaultPos;

        const options = {
            center: defaultPos,
            level: 3
        };

        map = new kakao.maps.Map(container, options);

        kakao.maps.event.addListener(map, 'click', function() {
            // 인포윈도우 대신 커스텀 오버레이 닫기
            if (detailOverlay) {
                detailOverlay.setMap(null);
            }
        });

        ps = new kakao.maps.services.Places(map);
        infowindow = new kakao.maps.InfoWindow({zIndex: 1});


        // --- 위치 결정 로직 ---
        const isValidCoord = (val) => val && !isNaN(val) && parseFloat(val) !== 0;

        if (isValidCoord(serverLat) && isValidCoord(serverLon)) {
            // 1. 예약 병원 데이터가 있는 경우
            console.log("예약 병원 위치로 이동");
            hospitalPos = new kakao.maps.LatLng(parseFloat(serverLat), parseFloat(serverLon));
            map.setCenter(hospitalPos);
            displayHospitalMarker(hospitalPos, hospitalName || "예약 병원");
            searchPharmacies();
        }
        else if (navigator.geolocation) {
            // 2. 예약은 없지만 GPS 사용이 가능한 경우
            navigator.geolocation.getCurrentPosition(
                function (position) {
                    console.log("GPS 위치 획득 성공");
                    hospitalPos = new kakao.maps.LatLng(position.coords.latitude, position.coords.longitude);
                    map.setCenter(hospitalPos);
                    searchPharmacies();
                },
                function(error) {
                    // 3. GPS 거부되거나 오류 발생 시 -> 기본값(서면) 사용
                    console.log("GPS 거부/오류: 기본 위치(서면)로 검색합니다.");
                    // hospitalPos는 이미 함수 상단에서 defaultPos(서면)로 초기화되어 있음
                    map.setCenter(hospitalPos);
                    searchPharmacies();
                },
                { timeout: 5000 } // 사용자가 5초간 무응답이면 실패로 간주
            );
        }
        else {
            // GPS 자체를 지원하지 않는 브라우저일 때
            console.log("이 브라우저는 GPS를 지원하지 않습니다.");
            map.setCenter(hospitalPos);
            searchPharmacies();
        }

        // [추가] 지도가 멈췄을 때 이벤트 리스너 재등록
        kakao.maps.event.addListener(map, 'idle', function() {
            clearTimeout(searchTimer);
            searchTimer = setTimeout(function() {
                if (!isDetailView) searchPharmacies();
            }, 500);
        });
    });

}

function moveToCurrentLocation() {
    if (map && hospitalPos) {
        // 이미 initPharmacyMap에서 결정된 hospitalPos(병원 > GPS > 서면 순)로 이동
        map.panTo(hospitalPos);

        // 이동 후 약국 재검색 (필요 시)
        setTimeout(function() {
            searchPharmacies();
        }, 500);
    } else {
        console.error("지도 또는 초기 위치 정보가 없습니다.");
    }
}

// ==========================================
// 3. 데이터 검색 및 처리 로직
// ==========================================

function searchPharmacies() {
    const center = map.getCenter();

    if (currentCircle !== null) {
        currentCircle.setMap(null);
    }

    // 반경을 1.5km(1500)로 넉넉하게 조정
    currentCircle = new kakao.maps.Circle({
        center: center,
        radius: 1500,
        strokeWeight: 1,
        strokeColor: '#75B8FA',
        strokeOpacity: 0.5,
        fillColor: '#CFE7FF',
        fillOpacity: 0.2
    });
    currentCircle.setMap(map);

    ps.categorySearch('PM9', placesSearchCB, {
        location: center,
        radius: 1500
    });
}

function placesSearchCB(data, status) {
    if (status === kakao.maps.services.Status.OK) {
        removeMarkers();
        // bounds 관련 코드는 더 이상 지도를 움직이는 데 사용하지 않습니다.
        data.sort((a, b) => a.distance - b.distance);

        for (let i = 0; i < data.length; i++) {
            displayMarker(data[i], i);
        }

        // [수정] 자동으로 줌인/아웃 하지 않고, 설정한 레벨을 유지하며 병원만 중앙에 고정
        if (isFirstSearch) {
            map.setCenter(hospitalPos);
            map.setLevel(3); // 숫자 낮을수록 확대(3~4가 시내 약국 보기 가장 좋음)
            isFirstSearch = false;
        }

        if (!isDetailView) {
            displayTopPharmacies(data.slice(0, 10));
        }
    }
}

// ==========================================
// 4. 지도 위 요소 표시 로직 (마커 & 오버레이)
// ==========================================

function displayMarker(place, index) {
    const markerPosition = new kakao.maps.LatLng(place.y, place.x);
    const marker = new kakao.maps.Marker({
        map: map,
        position: markerPosition
    });
    markers.push(marker);

    // 1. 상시 노출되는 숫자 마커 (기존 유지)
    const numberContent = `
        <div style="background:#2980b9; color:white; border-radius:50%; width:20px; height:20px; 
                    line-height:20px; text-align:center; font-size:12px; font-weight:bold;
                    border:2px solid white; box-shadow:0px 2px 4px rgba(0,0,0,0.3);
                    position:relative; bottom:45px;">
            ${index + 1}
        </div>`;

    const numberOverlay = new kakao.maps.CustomOverlay({
        position: markerPosition,
        content: numberContent,
        yAnchor: 1
    });
    numberOverlay.setMap(map);
    markers.push(numberOverlay);

    // 2. 마커 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', function () {
        // 기존에 열려있는 상세창이 있다면 닫기
        if (detailOverlay) detailOverlay.setMap(null);

        // [수정] 투박한 상자 없는 커스텀 디자인
        const detailContent = `
            <div style="position: relative; bottom: 70px; cursor: default;">
                <div style="
                    padding: 8px 12px;
                    background: #2c3e50;
                    color: white;
                    border-radius: 6px;
                    font-size: 13px;
                    font-weight: bold;
                    box-shadow: 0 4px 8px rgba(0,0,0,0.3);
                    white-space: nowrap;
                ">
                    ${place.place_name}
                    <div style="
                        position: absolute;
                        bottom: -6px;
                        left: 50%;
                        transform: translateX(-50%);
                        width: 0; height: 0;
                        border-left: 6px solid transparent;
                        border-right: 6px solid transparent;
                        border-top: 6px solid #2c3e50;
                    "></div>
                </div>
            </div>`;

        detailOverlay = new kakao.maps.CustomOverlay({
            position: markerPosition,
            content: detailContent,
            yAnchor: 1
        });

        detailOverlay.setMap(map);
        map.panTo(markerPosition);
        updateSidePanel(place);
    });
}

function displayHospitalMarker(position, name) {
    const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png';
    const imageSize = new kakao.maps.Size(34, 37);
    const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    const hospitalMarker = new kakao.maps.Marker({
        map: map,
        position: position,
        title: "예약 병원: " + name,
        image: markerImage
    });

    kakao.maps.event.addListener(hospitalMarker, 'click', function() {
        if (infowindow.getMap()) {
            infowindow.close();
        } else {
            // [중요] 아래 moveToReservedHospital과 HTML 구성을 완전히 똑같이 맞춥니다.
            const content = `
                <div style="padding:10px; font-size:13px; min-width:150px; text-align:center;">
                    🏥 <b style="color:#2c3e50;">${name}</b><br>
                    <span style="font-size:11px; color:#666;">예약된 병원 위치입니다.</span>
                </div>`;
            infowindow.setContent(content);
            infowindow.open(map, hospitalMarker);
        }
    });

    return hospitalMarker;
}

// ==========================================
// 5. 사이드바 UI 업데이트 로직 (함수들은 기존과 동일)
// ==========================================

function displayTopPharmacies(topList) {
    // [수정] side-panel이 아니라 side-panel-content를 가져옵니다.
    const contentArea = document.getElementById('side-panel-content');

    // 선택창은 그대로 두고 내부 내용만 바꿉니다.
    contentArea.innerHTML = `
        <div style="display: flex; flex-direction: column; height: 100%;">
            <div style="padding: 20px; background: #fff; border-bottom: 1px solid #eee;">
                <h3 style="color: #2c3e50; margin: 0;">📍 주변 약국 목록</h3>
                <p style="font-size: 12px; color: #7f8c8d; margin-top: 5px;">현재 지도 중심에서 가까운 순서</p>
            </div>
            <div id="pharmacy-list" style="flex: 1; overflow-y: auto; padding: 15px;"></div>
        </div>
    `;

    const listContainer = document.getElementById('pharmacy-list');
    let cardsHtml = '';
    topList.forEach((place, index) => {
        const distanceStr = place.distance ? `${place.distance}m` : "측정중";
        cardsHtml += `
            <div onclick="focusPharmacy(${index})" style="cursor:pointer; border: 1px solid #eee; padding: 15px; border-radius: 8px; margin-bottom: 12px; background: #fff; box-shadow: 0 2px 4px rgba(0,0,0,0.05);">
                <div style="display: flex; justify-content: space-between;">
                    <strong style="color: #2980b9;">${index + 1}. ${place.place_name}</strong>
                    <span style="font-size: 12px; color: #3498db; font-weight: bold;">${distanceStr}</span>
                </div>
                <p style="margin: 8px 0 0 0; font-size: 13px; color: #666;">${place.address_name}</p>
                <p style="margin: 4px 0 0 0; font-size: 12px; color: #95a5a6;">📞 ${place.phone || '번호 없음'}</p>
            </div>
        `;
    });
    listContainer.innerHTML = cardsHtml;
    window.currentTopList = topList;
}


function focusPharmacy(index) {
    const place = window.currentTopList[index];
    const moveLatLon = new kakao.maps.LatLng(place.y, place.x);
    map.panTo(moveLatLon);
    updateSidePanel(place);
}

function updateSidePanel(place) {
    isDetailView = true;
    // [수정] 여기도 side-panel-content를 타겟으로 합니다.
    const contentArea = document.getElementById('side-panel-content');

    contentArea.innerHTML = `
        <div style="padding: 20px;">
            <button onclick="backToList()" style="cursor:pointer; border:none; background:#eee; padding:5px 10px; border-radius:4px; margin-bottom:15px;">
                ← 목록으로 돌아가기
            </button>
            <h2 style="color: #2c3e50; margin-bottom: 5px;">${place.place_name}</h2>
            <hr>
            <p>📍 주소: ${place.road_address_name || place.address_name}</p>
            <p>📞 전화: ${place.phone || '정보 없음'}</p>
            <a href="${place.place_url}" target="_blank" style="display:block; text-align:center; padding:15px; background:#ffeb00; text-decoration:none; border-radius:8px; font-weight:bold; color:#000;">
               영업시간 확인(카카오맵으로 이동합니다)
            </a>
        </div>
    `;
}

function backToList() {
    isDetailView = false;
    searchPharmacies();
}

function removeMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
}

/**
 * 예약 목록에서 병원을 선택했을 때 지도를 이동하고 약국을 검색하는 함수
 */
function moveToReservedHospital(data) {
    if (!data) return;

    const parts = data.split('|');
    const lat = parseFloat(parts[0]);
    const lon = parseFloat(parts[1]);
    const name = parts[2];

    const movePos = new kakao.maps.LatLng(lat, lon);
    hospitalPos = movePos;
    isDetailView = false;
    isFirstSearch = true;

    map.setCenter(movePos);

    removeMarkers();
    const newHospitalMarker = displayHospitalMarker(movePos, name);

    // [중요] displayHospitalMarker의 클릭 시 내용과 100% 일치시킵니다.
    const content = `
        <div style="padding:10px; font-size:13px; min-width:150px; text-align:center;">
            🏥 <b style="color:#2c3e50;">${name}</b><br>
            <span style="font-size:11px; color:#666;">예약된 병원 위치입니다.</span>
        </div>`;

    infowindow.setContent(content);
    infowindow.open(map, newHospitalMarker);

    setTimeout(function() {
        searchPharmacies();
    }, 300);
}