<script setup>
import DiaryHeaderComponent from '@/components/diary/DiaryHeaderComponent.vue';
import DiaryListComponent from '@/components/diary/DiaryListComponent.vue';
import DiaryDetailComponent from '@/components/diary/DiaryDetailComponent.vue';
import { useMatchingStore } from '@/stores/matching';
import { useDiaryStore } from '@/stores/diary';
import { onMounted, onUpdated } from 'vue'

const store = useMatchingStore()
const diaryStore = useDiaryStore()

onMounted(async () => {
	await store.getMyMatchingList();
	await diaryStore.getUserList();
})
</script>

<template>
	<v-main>
		<v-container class="mt-16">
			<!-- <h1>DiaryView 입니다.</h1> -->
			<v-row>
				<DiaryHeaderComponent />
			</v-row>
			<v-row>
				<v-col cols="7" class="list-container">
					<DiaryListComponent />
				</v-col>
				<v-col cols="5" class="sticky-container">
					<DiaryDetailComponent />
				</v-col>
			</v-row>
		</v-container>
	</v-main>
</template>

<style scoped>
.v-main {
	height: max-content;
	padding-bottom: 100px;
	/* height: 100%; */
}
.v-container {
	width: 80%;
	/* border: 1px solid black; */
	/* border-radius: 12px; */
	height: 100%;
}
.list-container {
	display: flex;
	justify-content: center;
	padding-right: 120px;
	padding-left: 50px;
	
}

.sticky-container {
	position: relative;
	height: max-content;
	/* padding-left: 100px; */
	padding-right: 50px;
}
</style>