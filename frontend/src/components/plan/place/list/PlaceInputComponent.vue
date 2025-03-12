<script setup>
import { ref, watch } from 'vue'
import AddBtnComponent from '@/components/plan/place/AddBtnComponent.vue'

const props = defineProps({
	dialogVisible: Boolean,
})

const emit = defineEmits(["dialogVisibleEmit"])

const clickCloseBtn = () => {
	emit("dialogVisibleEmit")
}

const { VITE_KAKAO_MAP_KEY } = import.meta.env;
const mapContainer = ref(null)
const latitude = ref(0);
const longitude = ref(0);

const inputPlace = ref({
	place_name: "",
	y: 0,
	x: 0
})

watch(props, (newVal) => {
	if (!newVal.dialogVisible)
		return

	if (!("geolocation" in navigator)) {
		return;
	}

	navigator.geolocation.getCurrentPosition(
		(pos) => {
			inputPlace.value.y = pos.coords.latitude;
			inputPlace.value.x = pos.coords.longitude;

			if (window.kakao && window.kakao.maps) {
				initMap();
			} else {
				const script = document.createElement("script");
				script.onload = () => kakao.maps.load(() => initMap());
				script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${VITE_KAKAO_MAP_KEY}`;
				document.head.appendChild(script);
			}
		},
		(err) => {
			alert(err.message);
		}
	);
})

const initMap = () => {
	const container = document.getElementById("mapContainer");
	const options = {
		center: new kakao.maps.LatLng(inputPlace.value.y, inputPlace.value.x),
		level: 3,
	};
	map.value = new kakao.maps.Map(container, options);

	const marker = new kakao.maps.Marker({
		map: map.value, // 마커를 표시할 지도
		position: new kakao.maps.LatLng(inputPlace.value.y, inputPlace.value.x),
	});

	kakao.maps.event.addListener(map.value, "click", (mouseEvent) => {
		var latlng  = mouseEvent.latLng;

		marker.setPosition(latlng)

		// 선택한 곳으로 위도경도 갱신
		inputPlace.value.y = latlng.getLat()
		inputPlace.value.x = latlng.getLng()
	})
}


</script>

<template>
	<v-dialog v-model="props.dialogVisible">
		<v-card>
			<div class="d-flex justify-space-between">
				<v-card-title>장소 직접 입력</v-card-title>
				<v-btn flat icon="mdi-close" @click="clickCloseBtn"></v-btn>
			</div>

			<v-text-field v-model="inputPlace.place_name" label="장소 이름"></v-text-field>

			<!-- 지도 핀 지정 -->
			<div id="mapContainer" style="width: 100%; height: 450px; margin-bottom: 10px;"></div>

			<!-- 등록 여부 -->
			<AddBtnComponent :place="inputPlace" />
		</v-card>
	</v-dialog>
</template>

<style scoped>
.v-dialog {
	width: 50%;
	max-width: 1200px;
	min-width: 600px;
}
.v-card {
	padding: 50px;
}
</style>