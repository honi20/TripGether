<script setup>
import { onMounted, onUpdated, ref } from "vue";
import DetailTimelineCardComponent from "@/components/plan/detail/DetailTimelineCardComponent.vue";
import { usePlanStore } from "@/stores/plan";

const store = usePlanStore()

const props = defineProps({
	selectedDayIndex: Number,
	totalTravelDays: Number
})

const tab = ref(1)

onUpdated(() => {
	tab.value = props.selectedDayIndex + 1
})
</script>

<template>
	<v-card variant="outlined" style="border-color: #E0E1E3;">
		<v-card-text>
			<v-tabs-window v-model="tab">
				<v-tabs-window-item v-for="day in totalTravelDays" :key="day" :value="day">
					<h1 class="ml-5 mt-4 mb-5">DAY {{ day }}</h1>

					<v-timeline align="start" side="end" truncate-line="both" class="ml-3">
						<!-- 이미 저장된 일정 보여주기 -->
						<v-timeline-item v-for="place in store.planInfo.schedules[day - 1].places" :key="place.place_order"
							dot-color="#C3D3C6" fill-dot icon='mdi-map-marker-outline' size="large">
							<template v-slot:opposite>
								<div class="mt-3">{{ place.time }}</div>
							</template>

							<DetailTimelineCardComponent
								class="card" 
								:place="place" 
								:selectedDayIndex="selectedDayIndex" />
						</v-timeline-item>
					</v-timeline>

				</v-tabs-window-item>
			</v-tabs-window>
		</v-card-text>
	</v-card>
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

.v-timeline-item__body {
	width: 400px;
}
</style>