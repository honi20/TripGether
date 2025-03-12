<script setup>
import {ref,onMounted} from 'vue'

const props = defineProps({
	place: Object
})

// 카카오 맵
const { VITE_KAKAO_MAP_KEY } = import.meta.env;

const map = ref(null);
const markers = ref([]);
const latitude = ref(0);
const longitude = ref(0);

onMounted(() => {
	latitude.value = props.place.y;
	longitude.value = props.place.x;

	if (window.kakao && window.kakao.maps) {
		initMap([{
			title: props.place.title,
			latlng: new kakao.maps.LatLng(latitude.value, longitude.value)
		}]);
	} else {
		const script = document.createElement("script");
		script.onload = () => kakao.maps.load(() => initMap([]));
		script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_MAP_KEY}`;
		document.head.appendChild(script);
	}
})

const initMap = (positions) => {
	const imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
	const container = document.getElementById("detailMap");
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
	<div id="detailMap" style="width: 100%; height: 100%; margin: 0 auto"></div>
</template>

<style scoped></style>