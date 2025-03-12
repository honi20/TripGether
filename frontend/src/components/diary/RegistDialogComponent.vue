<script setup>
import { ref, watch } from 'vue';
import { useUserStore } from '@/stores/user'
import { useDiaryStore } from '@/stores/diary';
import { VDateInput } from 'vuetify/labs/VDateInput'
import { useMatchingStore } from '@/stores/matching';

const userStore = useUserStore()
const diaryStore = useDiaryStore()
const matchingStore = useMatchingStore()

// 기록 작성 dialog 보여짐 여부
const props = defineProps({
	dialogVisible: Boolean,
});

// dialog 끄기 버튼 클릭 시 부모 컴포넌트에게 전달
const emit = defineEmits(["dialogVisibleEmit"]);

const clickCloseBtn = () => {
	initVal()
	emit("dialogVisibleEmit");
};

const selectMatching = ref(null)
watch(selectMatching, (newVal) => {
	if (selectMatching.value != null)
		matchingUuid.value = selectMatching.value.matchingUuid
	else
		matchingUuid.value = null
})

const dateInput = ref(null)
watch(dateInput, (newVal) => {
	if (dateInput.value != null) {
		const length = dateInput.value.length
		const startDate = dateInput.value[0].getFullYear() + "/" + (dateInput.value[0].getMonth()+1) + "/" + dateInput.value[0].getDate()
		const endDate = dateInput.value[length-1].getFullYear() + "/" + (dateInput.value[length-1].getMonth()+1) + "/" + dateInput.value[length-1].getDate()
		travelDate.value = startDate + " ~ " + endDate

	}
})

const files = ref([])
const content = ref("")
const matchingUuid = ref(null)
const location = ref("")
const travelDate = ref(null)
const publicStatus = ref('openAll')

const clickSubmitBtn = () => {

	// 모든 입력 완료해야 함
	if (files.value == null || files.value.length == 0) {
		alert('파일을 1개 이상 선택하세요.')
		return
	}
	if (location.value == "") {
		location.value = "대한민국"
	}

	const postInfo = {
						"content": content.value,
						"matchingUuid": matchingUuid.value,
						"location": location.value,
						"travelDate": travelDate.value,
						"publicStatus": publicStatus.value
					}
	diaryStore.saveDiaryPost(files.value, postInfo)
	console.log(postInfo)
	initVal()
	emit("dialogVisibleEmit");
};

const initVal = () => {
	selectMatching.value = null
	dateInput.value = null
	files.value = []
	content.value = ""
	matchingUuid.value = null
	location.value = ""
	travelDate.value = ""
	publicStatus.value = 'openAll'
}
</script>

<template>
	<v-dialog v-model="props.dialogVisible">
		<v-card>
			<div class="d-flex justify-space-between" style="background-color: #C3D3C6;">
				<v-btn flat color="#C3D3C6" icon="mdi-close" @click="clickCloseBtn"></v-btn>
				<v-card-title>기록 등록</v-card-title>
				<v-btn flat color="#C3D3C6" icon="mdi-checkbox-marked-outline" @click="clickSubmitBtn"></v-btn>
			</div>
			<v-divider :thickness="2"></v-divider>

			<v-row class="d-flex align-center">
				<v-avatar class="my-6 ml-10" size="50">
					<v-img :src="userStore.user.userProfile"></v-img>
				</v-avatar>

				<v-card-title class="ml-2">
					{{ userStore.user.userId }}
				</v-card-title>
			</v-row>
			<v-divider></v-divider>

			<v-card-item>
				
				<v-file-input v-model="files" :show-size="1000" variant="outlined" color="deep-purple-accent-4" accept="image/*"
					label="사진 선택" placeholder="기록할 사진을 업로드하세요" prepend-icon="mdi-paperclip" counter multiple>
					<template v-slot:selection="{ fileNames, remove }">
						<template v-for="(fileName, index) in fileNames" :key="fileName">
							<v-chip class="me-2" color="deep-purple-accent-4" size="small" label>
								{{ fileName }}
							</v-chip>
						</template>
					</template>
				</v-file-input>

				<span>글 작성</span>
				<v-textarea v-model="content"></v-textarea>

				<span>함께한 여행</span>
				<v-select :items="matchingStore.myMatchingList" item-title="matchingTitle"
					label="여행 그룹별 보기" v-model="selectMatching" clearable return-object>
					<template v-slot:item="{ props, item }">
						<v-list-item v-bind="props" :subtitle="item.raw.planStartDate + ' ~ ' + item.raw.planEndDate"></v-list-item>
					</template>
				</v-select>

				<span>위치(장소)</span>
				<v-text-field v-model="location" variant="underlined"></v-text-field>

				<span>여행 기간</span>
				<!-- <v-text-field v-model="travelDate" variant="underlined"></v-text-field> -->
				<v-date-input
					v-model="dateInput"
					label="여행 기간 선택"
					max-width="100%"
					multiple="range"
					variant="underlined"
					class="date-input"
				></v-date-input>

				
			</v-card-item>

			<v-card-item>
				<span>기록 공개 여부</span>
				<v-radio-group inline v-model="publicStatus">
					<v-radio label="전체공개" value="openAll" color="indigo"></v-radio>
					<v-radio label="그룹공개" value="openGroup" color="indigo"></v-radio>
					<v-radio label="비공개" value="private" color="indigo"></v-radio>
				</v-radio-group>
			</v-card-item>
		</v-card>
	</v-dialog>
</template>

<style scoped>
.v-dialog {
	width: 40%;
	max-width: 1200px;
	min-width: 600px;
}
.v-input__prepend {
	display: none;
}
</style>