<script setup>
import { usePlanStore } from '@/stores/plan';
import { ref, watch, onMounted } from 'vue'
const { VITE_KAKAO_MAP_KEY } = import.meta.env

const props = defineProps({
	schedules: Object,
	selectedDayIndex: String
})

const store = usePlanStore()
const places = ref([])

onMounted(() => {
	loadKakaoMap(mapContainer.value)
})

watch(props, (newVal, oldVal) => {
	places.value = props.schedules[props.selectedDayIndex].places

	console.log(places.value)
	// console.log(places.value)
	// console.log('call kakao')
	loadKakaoMap(mapContainer.value)
})

const mapContainer = ref(null)
const linePath = ref([])
const markers = ref([])

const loadKakaoMap = (container) => {
	console.log('kakao')
	const script = document.createElement('script')
	script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_KEY}&autoload=false`
	document.head.appendChild(script)

	script.onload = () => {
		window.kakao.maps.load(() => {
			var lat, lon;

			// 현재 계획 일정이 있는 경우
			if (places.value.length > 0) {
				lat = places.value[0].latitude
				lon = places.value[0].longitude
			}
			// 없다면 현재 위치 기반으로
			else {
				lat = 33.450701;
				lon = 126.570667;
			}

			const options = {
				center: new kakao.maps.LatLng(lat, lon), // 지도의 중심좌표
				level: 8, // 지도의 확대 레벨
				maxLevel: 14, // 지도 축소 제한 레벨
			}

			const mapInstance = new window.kakao.maps.Map(container, options) // 지도 생성

			const newLinePath = []

			const imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

			// 기존 마커 제거
			markers.value.forEach(marker => marker.setMap(null))
			markers.value = []

			places.value.forEach(function (place) {
				newLinePath.push(new kakao.maps.LatLng(place.latitude, place.longitude)); // 배열 끝에 값 추가하기

				const imageSize = new kakao.maps.Size(24, 35);

				const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

				// 마커 생성
				const marker = new kakao.maps.Marker({
					position: new kakao.maps.LatLng(place.latitude, place.longitude),
					title: place.name,
					image: markerImage
				})
				marker.setMap(mapInstance)

				// 각 마커에 클릭 이벤트 추가
				var infowindow = new kakao.maps.InfoWindow({
					content: place.name,
					removable: true
				});

				// 클로저를 사용하여 클릭 이벤트 핸들러 정의
				kakao.maps.event.addListener(marker, "click", (function (marker, infowindow) {
					return function () {
						infowindow.open(mapInstance, marker); // 해당 마커의 정보 창 열기
					};
				})(marker, infowindow));

				markers.value.push(marker)
			});

			// 기존 선 제거
			if (linePath.value.length > 0)
				linePath.value = []

			linePath.value = newLinePath

			console.log(linePath.value)

			const polyline = new kakao.maps.Polyline({
				path: linePath.value, // 선을 구성하는 좌표배열 입니다
				strokeWeight: 5, // 선의 두께 입니다
				strokeColor: '#176876', // 선의 색깔입니다
				strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
				strokeStyle: 'solid' // 선의 스타일입니다
			});

			polyline.setMap(mapInstance);

			// 맵의 중심과 확대 수준을 모든 마커가 보이도록 조정
			// const bounds = new kakao.maps.LatLngBounds();
			// places.forEach((place) => bounds.extend(new kakao.maps.LatLng(place.latitude, place.longitude)));
			// mapInstance.setBounds(bounds);

		})
	}
}
</script>

<template>
	<div ref="mapContainer" style="width:100%; height: 70vh"></div>
</template>

<style scoped></style>