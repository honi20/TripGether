<script setup>
import { useDiaryStore } from '@/stores/diary';
import { useUserStore } from '@/stores/user';
import {ref, computed, onMounted} from 'vue'

const diaryStore = useDiaryStore()
const userStore = useUserStore()

onMounted(() => {
	diaryStore.initDiaryDetail()
})

const deletePost = () => {
	if (confirm('기록을 삭제하시겠습니까?')) {
		diaryStore.deleteDiary(diaryStore.diaryDetail.diaryUuid)
		diaryStore.diaryDetail = null
	}
}
const formattedContent = computed(() => diaryStore.diaryDetail.content.replace(/\n/g, "<br>"));
</script>

<template>

	<v-card v-if="diaryStore.diaryDetail != null" 
	class="sticky-card mx-auto border" color="white"
	:title="diaryStore.diaryDetail.writerId" 
	:subtitle="diaryStore.diaryDetail.location">
		<template v-slot:prepend>
			<v-avatar size="40">
				<v-img :src="diaryStore.diaryDetail.writerProfile"></v-img>
			</v-avatar>
		</template>

		<v-carousel hide-delimiters show-arrows="hover" aspect-ratio="16/9">
			<v-carousel-item v-for="imgPath in diaryStore.diaryDetail.images" :key="imgPath" :src="imgPath.split('public')[1]" cover></v-carousel-item>
		</v-carousel>

		<v-card-text style="font-size: 16px;" v-html="formattedContent"></v-card-text>

		<v-card-item>
			<v-col class="d-flex justify-space-between" style="align-items: center;" v-if="diaryStore.diaryDetail.writerId === userStore.user.userId">
				<v-chip
				color="pink"
				label
				>
					<v-icon icon="mdi-label" start></v-icon>
					{{ diaryStore.diaryDetail.publicStatus }}
				</v-chip>
				<v-btn @click="deletePost" color="#F5F5F5">삭제</v-btn>
			</v-col>
			<div class="d-flex justify-end mr-3" style="color: gray;">작성일 : {{ diaryStore.diaryDetail.created_at.split('T')[0] }}</div>
		</v-card-item>
	</v-card>

</template>

<style scoped>
.sticky-card {
	position: sticky;
	top: 100px;
	/* z-index: 10; */
	padding-top: 0px;
}
</style>
