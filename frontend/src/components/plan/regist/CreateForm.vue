<script setup>
import { ref, watch } from 'vue';
import MapComponent from '@/components/plan/regist/MapComponent.vue';
import TimelineComponent from '@/components/plan/regist/TimelineComponent.vue';
import { usePlanStore } from '@/stores/plan';

const store = usePlanStore()

// date format 'yyyy-mm-dd'
const formatDate = (date) => {
	const year = date.value.getFullYear();
	const month = String(date.value.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 두 자리로 패딩
	const day = String(date.value.getDate()).padStart(2, '0'); // 일을 두 자리로 패딩
	return `${year}-${month}-${day}`;
}

const dateDiff = (start_date, end_date) => {
	let dateDiff = Math.ceil((end_date.value.getTime() - start_date.value.getTime())
		/ (1000 * 60 * 60 * 24)) + 1;
	return dateDiff;
}

const startDate = ref(new Date());
const startDateFormated = ref(formatDate(startDate))
const endDate = ref(null)
const endDateFormated = ref('')
const travelDays = ref(0); // 총 여행일자

// startDate, endDate가 변경될 때마다 Formatted 업데이트  
watch([startDate, endDate], ([newStartDate, newEndDate]) => {
	startDateFormated.value = formatDate(startDate);
	if (newEndDate === null)
		return
	endDateFormated.value = formatDate(endDate);
	travelDays.value = dateDiff(startDate, endDate);

	// 변경된 사항을 store에 저장
	store.initTravelDays(startDateFormated.value, endDateFormated.value, travelDays.value)
	console.log(travelDays.value);
});

const menu1 = ref(false)
const menu2 = ref(false);

// Variable to store the index of the selected day
const selectedDayIndex = ref(0);

const selectDay = (index, toggle) => {
	selectedDayIndex.value = index;
	toggle();
	console.log(selectedDayIndex.value)
};

const inputTitle = ref("")
const submitForm = () => {
	store.createPlan(inputTitle.value, thumbnail.value)
};

// 썸네일 이미지
const thumbnail = ref(null)

const handleFileChange = (event) => {
	thumbnail.value = event.target.files[0]
	console.log(thumbnail.value)
}
</script>


<template>
		<v-row class="justify-space-between pa-3 mt-10">
		<!--<v-row style="align-items: center;">-->
			<input type="text" v-model="inputTitle" placeholder="여행 이름을 입력하세요." variant="plain" style="font-size: 30px; padding-left: 10px; width: 80%">
			<v-btn class="text-h6" color="#176876" variant="flat" height="50px" @click="submitForm">
				<p class="mx-4">등록</p>
			</v-btn>
		</v-row>
		<v-row>
			<v-col cols="6" style="display:flex;">
				<div class="d-flex fill-height rounded-lg border-md pa-2" style="width:100%;">
					<v-container>
						<v-row rows="3">
							<v-col cols="2" class="px-2 py-1 text-30px">
								<b>여행 날짜</b>
							</v-col>
							<v-col cols="9" justify="start" class="selectDate pt-1">
								<v-menu v-model="menu1" :close-on-content-click="false" transition="bottom" offset-y
									attach>
									<template v-slot:activator="{ attr1 }">
										<!-- style="position: absolute; top: 250px; left:200px;"> -->
										<v-text-field placeholder="출발일" v-model="startDateFormated" prepend-inner-icon="mdi-calendar-edit"
											readonly v-bind="attr1" max-width="160px" max-height="50px" solo
											@click="menu1 = !menu1"></v-text-field>
									</template>
									<v-date-picker v-model="startDate" @input="menu1 = false"></v-date-picker>
								</v-menu>

								<span>~</span>

								<v-menu v-model="menu2" :close-on-content-click="false" transition="bottom" offset-y
									attach>
									<template v-slot:activator="{ attr2 }">
										<v-text-field placeholder="도착일" v-model="endDateFormated" prepend-inner-icon="mdi-calendar-edit"
											readonly v-bind="attr2" max-width="160px" solo
											@click="menu2 = !menu2"></v-text-field>
									</template>
									<v-date-picker v-model="endDate" @input="menu2 = false"></v-date-picker>
								</v-menu>
							</v-col>
						</v-row>
						<v-row rows="9" class="justify-start">
							<v-item-group mandatory>
								<v-container>
									<v-row>
										<v-item v-slot="{ isSelected, toggle }"
											v-for="(day, index) in travelDays" :key="day" md="4">
											<v-card :color="isSelected || (index === 0 && selectedDayIndex === 0)  ? '#4D7952' : '#C3D3C6'"
												class="d-flex align-center ma-2" width="80" height="100" dark
												@click="selectDay(index, toggle)">
												<!-- 원래 @click="toggle" 이었다 -->
												<v-scroll-y-transition>
													<div class="flex-grow-1 text-center">
														Day {{ index + 1 }}
													</div>
												</v-scroll-y-transition>
											</v-card>
										</v-item>
									</v-row>
								</v-container>
							</v-item-group>
						</v-row>
					</v-container>
				</div>
			</v-col>
			<v-col cols="6">
				<div class="px-5 py-4 text-30px bg-grey-lighten-4 rounded-lg fill-height">
					<p class="mb-3"><b>커버 사진</b></p>
					<v-file-input
						accept="image/*"
						label="File input"
						@change="handleFileChange"
					></v-file-input>
				</div>
			</v-col>
		</v-row>
		<v-row>
			<v-col cols="5">
				<div style="border-radius: 10%;">
					<MapComponent :schedules="store.schedules" :selectedDayIndex="selectedDayIndex" />
				</div>
			</v-col>
			<v-col cols="7">
				<div class="d-flex rounded-lg #E0E0E0 fill-height border" style="width:100%">
					<TimelineComponent class="rounded-lg #E0E0E0 fill-height border"
						:selectedDayIndex="selectedDayIndex" />
				</div>
			</v-col>
		</v-row>

</template>

<style scoped>
/* .v-conatiner {
	border: 1px solid black;
}

.v-row {
  border: 1px solid black;
}

.v-col {
  border: 1px solid black;
}

div {
  border: 1px solid black;
} */
.selectDate {
	display: flex;
	flex-direction: row;
	align-items: center;
}

.v-text-field input {
	font-size: 50px;
}

.v-overlay {
	position: relative;
}
</style>