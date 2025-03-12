<script setup>
import { onUpdated, ref } from "vue";
import PlaceSearchComponent from "@/components/plan/place/PlaceSearchComponent.vue";
import TimelineCardComponent from "@/components/plan/regist/TimelineCardComponent.vue";
import { usePlanStore } from "@/stores/plan";

const store = usePlanStore()

const props = defineProps({
	selectedDayIndex: Number
})
const dialogVisible = ref(false);

// 장소 검색 모달창 열기
const openPlaceSearch = () => {
	dialogVisible.value = true
}

// 장소 검색 모달창 닫기
const closePlaceSearch = () => {
	dialogVisible.value = false
}

const tab = ref(1)

onUpdated(() => {
	tab.value = props.selectedDayIndex + 1
})

const editingCardId = ref(null)
// 특정 일정 카드의 수정 요청
const updatePlanCard = (place) => {
	editingCardId.value = place.place_order
}

const completeUpdate = () => {
	editingCardId.value = null
}

</script>

<template>
	<v-card variant="outlined" style="border-color: #E0E1E3;">
		<v-card-text>
			<v-tabs-window v-model="tab">
				<v-tabs-window-item v-for="day in store.totalTravelDays" :key="day" :value="day">
					<h1 class="ml-5 mt-4 mb-5">DAY {{ day }}</h1>

					<v-timeline align="start" side="end" truncate-line="both">
						<!-- 이미 저장된 일정 보여주기 -->
						<v-timeline-item v-for="place in store.schedules[day - 1].places" :key="place.place_order"
							dot-color="#C3D3C6" fill-dot icon='mdi-map-marker-outline' size="large">
							<template v-slot:opposite>
								<div class="mt-3">{{ place.time }}</div>
							</template>

							<TimelineCardComponent
								class="card" 
								:place="place" 
								:selectedDayIndex="selectedDayIndex"
								@card-update-emit="updatePlanCard(place)" />
						</v-timeline-item>

						<!-- 새로운 장소 추가 -->
						<v-timeline-item dot-color="grey-lighten-4" fill-dot icon="mdi-plus-circle" size="large"
							@click="openPlaceSearch">
							<div class="d-flex">
								<div class="mt-1 text-h6">
									<strong>장소 추가하기</strong>
								</div>
							</div>
						</v-timeline-item>
					</v-timeline>

				</v-tabs-window-item>
			</v-tabs-window>
		</v-card-text>
	</v-card>

	<PlaceSearchComponent :dialog-visible="dialogVisible" @dialog-visible-emit="closePlaceSearch" />
</template>


<style scoped>
.v-card {
	width: 100%;
}

.v-text-field input {
	font-size: 3em;
}

.timeline-container {
	max-height: 667px;
	/* Adjust the height as needed */
	overflow-y: auto;
}
.v-timeline--vertical.v-timeline--justify-auto {
	grid-template-columns: 1fr 0fr 3fr;
}
</style>