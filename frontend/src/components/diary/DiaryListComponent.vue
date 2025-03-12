<script setup>
import { ref } from "vue";
import DiaryCardComponent from '@/components/diary/DiaryCardComponent.vue';
import RegistDialogComponent from '@/components/diary/RegistDialogComponent.vue';
import { useDiaryStore } from "@/stores/diary"

const dialogVisible = ref(false);
const diaryStore = useDiaryStore()

// 기록 작성 모달창 열기
const openRegistDiary = () => {
	dialogVisible.value = true
}

// 기록 작성 모달창 닫기
const closeRegistDiary = () => {
	dialogVisible.value = false
}

const selectDiaryPost = (diaryUuid) => {
	diaryStore.getDiaryDetail(diaryUuid);
}

</script>

<template>
	<v-row>
		<v-col cols="4">
			<v-card class="d-flex align-center text-center justify-center square-card" @click="openRegistDiary">
				<v-icon icon="mdi-plus-circle" style="color: gray;" size="50" />
			</v-card>
		</v-col>

		<v-col v-for="item in diaryStore.diaryList" :key="item.diaryUuid" class="d-flex child-flex" cols="4" @click="selectDiaryPost(item.diaryUuid)">
			<div class="square-card">
				<v-img v-if="item.images.length > 0" :lazy-src="item.images[0].split('public')[1]"
					:src="item.images[0].split('public')[1]" aspect-ratio="1" class="diary-post bg-grey-lighten-2 border"
					cover>
				</v-img>
			</div>
		</v-col>
	</v-row>

	<RegistDialogComponent :dialog-visible="dialogVisible" @dialog-visible-emit="closeRegistDiary" />
</template>

<style scoped>
.v-row {
	height: min-content;
}
.v-col {
	padding: 0;
}
.square-card {
	width: 100%;
	aspect-ratio: 1;
}
</style>
