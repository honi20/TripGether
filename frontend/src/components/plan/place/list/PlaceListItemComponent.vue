<script setup>
import AddBtnComponent from '@/components/plan/place/AddBtnComponent.vue'
import PlaceDetailComponent from '@/components/plan/place/detail/PlaceDetailComponent.vue'
import { ref } from 'vue';
import { usePlaceStore } from '@/stores/place';

defineProps({
	place: Object
})

const detailDialogVisible = ref(false);
const store = usePlaceStore()

// 장소 상세보기 모달창 열기
const openPlaceDetail = async (place) => {
	// 상세보기 연관장소 불러오기
	const addressParts = place.address_name.split(' ');
    const location = addressParts.slice(0, 2).join(' ');
	await store.getRelatedVlogList(place.place_name)
	await store.getRelatedPlaceList(location, place.place_name)
	detailDialogVisible.value = true
}

// 장소 상세보기 모달창 닫기
const closePlaceDetail = () => {
	detailDialogVisible.value = false
}
</script>

<template>
	<v-list-item 
	style="background-color: white; opacity: 0.95;"
	class="list-item mb-5 border rounded-lg" @click="openPlaceDetail(place)">
		<div class="d-flex justify-space-between">
			<v-list-item-title class="item-name">{{ place.place_name }}</v-list-item-title>
			<AddBtnComponent :place="place" />
		</div>

		<PlaceDetailComponent
			:dialog-visible="detailDialogVisible"
			:place="place"
			@dialog-visible-emit="closePlaceDetail"
		/>
	</v-list-item>

</template>
  
  
<style scoped>
.list-item {
	width: 60%;
	margin: 0 auto;
}
.item-name {
  display: flex;
  align-items: center;
}
</style>
  