<script setup>
import PlaceListComponent from "@/components/plan/place/list/PlaceListComponent.vue";
import { ref, watch } from "vue";
import { usePlaceStore } from "@/stores/place";

// 장소 검색 dialog 보여짐 여부
const props = defineProps({
	dialogVisible: Boolean,
});

// dialog 끄기 버튼 클릭 시 부모 컴포넌트에게 전달
const emit = defineEmits(["dialogVisibleEmit"]);

const store = usePlaceStore();

const clickCloseBtn = () => {
	searchInput.value = ''
	emit("dialogVisibleEmit");
};

// 입력된 검색어 기반으로 장소 리스트 가져오기
const searchInput = ref("");

const searchPlace = async () => {
	await store.getPlaceList(searchInput.value);
	// console.log(store.placeList)
	if (window.kakao && window.kakao.maps) {
		updateMap();
	}
};

// 카카오 맵
const { VITE_KAKAO_MAP_KEY } = import.meta.env;

const map = ref(null);
const markers = ref([]);
const latitude = ref(0);
const longitude = ref(0);

// 검색어가 입력될 때마다 들어온 장소를 여러 개의 마커로 표시
const updateMap = () => {
	// var positions = []
	var positions = []

	if (store.placeList.length > 0) {
		for (var i = 0; i < store.placeList.length; i++) {
			const position = {
							title: store.placeList[i].place_name,
							latlng: new kakao.maps.LatLng(store.placeList[i].y, store.placeList[i].x)
						}
	
			positions.push(position)
		}
		if (window.kakao && window.kakao.maps) {
			initMap(positions)
		}

	}
		
}

// 장소 검색 모달창이 처음 켜질 때만 현재 위치 기반 장소 보여짐
watch(props, (newValue, oldValue) => {
	if (!newValue.dialogVisible) return;

	if (!("geolocation" in navigator)) {
		return;
	}

	navigator.geolocation.getCurrentPosition(
		(pos) => {
			latitude.value = pos.coords.latitude;
			longitude.value = pos.coords.longitude;

			if (window.kakao && window.kakao.maps) {
				initMap([{
					title: "myPosition",
					latlng: new kakao.maps.LatLng(latitude.value, longitude.value)
				}]);
			} else {
				const script = document.createElement("script");
				script.onload = () => kakao.maps.load(() => initMap([]));
				script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_MAP_KEY}`;
				document.head.appendChild(script);
			}
		},
		(err) => {
			alert(err.message);
		}
	);
});

const initMap = (positions) => {
	const imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
	const container = document.getElementById("map");
	const options = {
		center: positions[0].latlng,
		level: 3,
	};
	map.value = new kakao.maps.Map(container, options);

	const newMarkers = []
	for (var i = 0; i < positions.length; i++) {
		const imageSize = new kakao.maps.Size(24, 35);
		
		const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
		
		const marker = new kakao.maps.Marker({
			map: map.value, // 마커를 표시할 지도
			position: positions[i].latlng, // 마커를 표시할 위치
			title: positions[i].title, // 마커의 타이틀
			image: markerImage, // 마커 이미지 
			// clickable: true
		});
		
		// 각 마커에 클릭 이벤트 추가
        var infowindow = new kakao.maps.InfoWindow({
			content : positions[i].title,
			removable : true
		});

		// 클로저를 사용하여 클릭 이벤트 핸들러 정의
        kakao.maps.event.addListener(marker, "click", (function (marker, infowindow) {
            return function () {
                infowindow.open(map.value, marker); // 해당 마커의 정보 창 열기
            };
        })(marker, infowindow));

		newMarkers.push(marker)
	}
	
	// 기존 마커 삭제
	if (markers.value.length > 0)
		markers.value.forEach(marker => marker.setMap(null));

	markers.value = newMarkers;

	// 맵의 중심과 확대 수준을 모든 마커가 보이도록 조정
	const bounds = new kakao.maps.LatLngBounds();
	positions.forEach((position) => bounds.extend(position.latlng));
	map.value.setBounds(bounds);
};

</script>

<template>
	<v-dialog v-model="props.dialogVisible">
		<v-card class="pa-7" style="background-color: #C3D3C6">
			<div class="d-flex justify-space-between">
				<v-card-title><h3>장소 검색</h3></v-card-title>
				<v-btn flat icon="mdi-close" @click="clickCloseBtn"></v-btn>
			</div>

			<v-card-text>
				<!-- 검색창 -->
				<v-text-field
				bg-color="white"
				append-icon="mdi-magnify" class="searchInput" placeholder="장소 검색"
					@click:append="searchPlace" v-model="searchInput">
				</v-text-field>

				<!-- 지도 -->
				<div id="map" style="width: 60%; height: 400px; margin: 0 auto; border-radius: 5px;"></div>

				<!-- 장소 목록 -->
				<PlaceListComponent />
			</v-card-text>
		</v-card>
	</v-dialog>
</template>

<style scoped>
.v-dialog {
	width: 70%;
	max-width: 1200px;
	min-width: 600px;
}

.searchInput {
	width: 60%;
	margin: 0 auto;
}
</style>
