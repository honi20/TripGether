<script setup>
import AddBtnComponent from '@/components/plan/place/AddBtnComponent.vue'
import PlaceListItemComponent from '@/components/plan/place/list/PlaceListItemComponent.vue'
import PlaceInputComponent from '@/components/plan/place/list/PlaceInputComponent.vue';
import { usePlaceStore } from '@/stores/place';
import { ref } from 'vue';

const placeName = ref('')

const store = usePlaceStore()

const inputDialogVisible = ref(false);

// 장소 직접 입력 모달창 열기
const openPlaceInput = () => {
	console.log('open')
	inputDialogVisible.value = true
}

// 장소 직접 입력 모달창 닫기
const closePlaceInput = () => {
	inputDialogVisible.value = false
}
</script>

<template>
	<v-list style="background-color: #C3D3C6">
		<!-- 직접 입력 -->
		<v-list-item 
		style="background-color: white; opacity: 0.95;"
		class="directInput mt-8 mb-5 border rounded-lg" @click="openPlaceInput()">
			<div class="d-flex justify-space-between">
				<v-list-item-title @click="openPlaceInput">직접 입력</v-list-item-title>
			</div>

			<PlaceInputComponent 
				:dialog-visible="inputDialogVisible"
				@dialog-visible-emit="closePlaceInput"
			/>
		</v-list-item>

		<!-- 검색 결과 -->
		<PlaceListItemComponent v-for="place in store.placeList" :place="place" />
		
	</v-list>
</template>

<style scoped>
.directInput {
	width: 60%;
	margin: 0 auto;
}
</style>