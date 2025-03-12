<script setup>
import { useUserStore } from '@/stores/user'
import { useMatchingStore } from '@/stores/matching'
import { onMounted, ref, watch } from 'vue'
import { useDiaryStore } from '@/stores/diary';

const userStore = useUserStore()
const matchingStore = useMatchingStore()
const diaryStore = useDiaryStore()

const selectMatching = ref(null)

watch(selectMatching, (newValue) => {
	console.log(newValue)

	// 그룹 기록 리스트
	if (newValue != null) {
		diaryStore.getGroupList(newValue.matchingUuid)
		diaryStore.updateSelectMatching(newValue.matchingUuid)
	}
	// 개인 기록 리스트
	else {
		diaryStore.getUserList();
		diaryStore.updateSelectMatching(null)
	}
})
</script>

<template>
	<v-col cols="6" style="margin: auto; padding-right: 100px; padding-left: 50px;">
		<div class="nametag d-flex flex-no-wrap justify-items-center align-center">
			<v-avatar class="mt-7 ma-10" :size="125">
				<v-img :src="userStore.user.userProfile"></v-img>
			</v-avatar>

			<v-card-title class="text-h5">
				{{ userStore.user.userName }}님의 여행기록
			</v-card-title>
		</div>
	</v-col>
	<v-col cols="6" class="d-flex flex-no-wrap text-center align-center" style="padding-left: 130px; padding-right: 50px;">
		<v-select :items="matchingStore.myMatchingList" item-title="matchingTitle"
					label="여행 그룹별 보기" v-model="selectMatching" clearable return-object>
			<template v-slot:item="{ props, item }">
				<v-list-item prepend-icon="mdi-account-multiple" color="#176876" v-bind="props" :subtitle="item.raw.planStartDate + ' ~ ' + item.raw.planEndDate"></v-list-item>
			</template>
		</v-select>
	</v-col>
</template>

<style scoped>
.v-img {
	object-fit:scale-down;
}
</style>