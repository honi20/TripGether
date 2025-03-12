<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router'
import MapComponent from '@/components/plan/regist/MapComponent.vue';
import DetailTimelineComponent from '@/components/plan/detail/DetailTimelineComponent.vue'
import { usePlanStore } from '@/stores/plan';

const store = usePlanStore()
const router = useRouter()
const route = useRoute()

const goToList = () => {
	router.push({ name: 'planList' })
}

const travelDays = ref(0)

const props = defineProps({
	planId: String,
	viewInMatchingDetail: Boolean
})

onMounted(async () => {
	if (props.viewInMatchingDetail) { 
		await store.getPlan(props.planId)
		travelDays.value = store.planInfo.schedules.length
	}
	else {
		await store.getPlan(route.params.uuid)
		console.log(store.planInfo)
		travelDays.value = store.planInfo.schedules.length
		// selectDay(0, toggle)		
	}
})

const selectedDayIndex = ref(0);
const selectDay = (index, toggle) => {
	selectedDayIndex.value = index;
	toggle();
	console.log(selectedDayIndex.value)
};

</script>


<template>
	<!-- <v-container class="ma-4"> -->
		<v-row v-if="!viewInMatchingDetail" style="align-items: center;">
			<v-col cols="7">
				<h1>{{store.planInfo.title}}</h1>
			</v-col>
			<v-col cols="4"></v-col>
			<v-col cols="1">
				<v-btn color="#176876" variant="flat" height="50px" @click="goToList">
					목록
				</v-btn>
			</v-col>
		</v-row>
		<v-row>
			<v-col cols="5" style="display:flex;">
				<div class="border-m rounded-lg d-flex fill-height" style="width:100%;">
					<v-container>
						<v-row rows="3" class="mx-auto align-center justify-center">
							<v-col cols="3">
								<h3>여행 날짜</h3>
							</v-col>
							<v-col cols="9"></v-col>
						</v-row>
						<v-row rows="9" class="mx-auto text-center align-center justify-center">
							<v-col>
								<v-text-field readonly>{{ store.planInfo.startDate }}</v-text-field>
							</v-col>
							<span>~</span>
							<v-col>
								<v-text-field readonly>{{ store.planInfo.endDate }}</v-text-field>
							</v-col>
						</v-row>
					</v-container>
				</div>
			</v-col>
			<v-col cols="7">
				<div class="rounded-lg fill-height pa-5" style="border: 1px solid #E0E1E3">
					<h3>날짜 별 일정</h3>
					<v-item-group mandatory>
						<v-container>
							<v-row>
								<v-item v-slot="{ isSelected, toggle }" v-for="(day, index) in travelDays" :key="day"
									md="4">
									<v-card 
									:color="isSelected || (index === 0 && selectedDayIndex === 0)  ? '#4D7952' : '#C3D3C6'" 
									class="d-flex align-center ma-2"
									width="80" height="100" dark 
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
				</div>
			</v-col>
		</v-row>
		<v-row>
			<v-col cols="5">
				<div class="rounded-lg">
					<MapComponent :schedules="store.planInfo.schedules" :selectedDayIndex="selectedDayIndex" />
				</div>
			</v-col>
			<v-col cols="7">
				<div class="d-flex fill-height" style="border-color: #E0E1E3; width:100%;">
					<DetailTimelineComponent class="fill-height"
						:selectedDayIndex="selectedDayIndex" :totalTravelDays="travelDays" />
				</div>
			</v-col>
		</v-row>
	<!-- </v-container> -->

</template>

<style scoped>
/* .v-row {
  border: 1px solid black;
}

.v-col {
  border: 1px solid black;
} */

/* div {
  border: 1px solid black;
} */
.selectDate {
	display: flex;
	flex-direction: row;
	align-items: center;
}

.v-text-field {
	/* font-size: 50px; */
	margin: auto;
}

.v-overlay {
	position: relative;
}
</style>