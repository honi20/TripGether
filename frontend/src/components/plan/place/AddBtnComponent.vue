<script setup>
import {ref} from 'vue'
import { usePlanStore } from '@/stores/plan';

const props = defineProps({
	place: Object
})

const store = usePlanStore()

const highlightedIndex = ref(null)

const highlightItem = (day) => {
	highlightedIndex.value = day
}

const unhighlightItem = () => {
	highlightedIndex.value = null
}

const selectItem = (day) => {
	console.log(day + "에 장소 추가! : " + props.place)

	// plan store에 해당 장소 추가
	store.addPlaceToPlans(props.place, day-1)
}

</script>

<template>
	<v-menu location="bottom">
		<template v-slot:activator="{ props }">
			<v-btn 
				variant="tonal"
				icon="mdi-plus" 
				color="#176876"
				size="30"
				v-bind="props"
			></v-btn>
		</template>
  
		<v-list>
			<v-list-item
				v-for="day in store.totalTravelDays"
				:key="day"
				@mouseenter="highlightItem(day)"
				@mouseleave="unhighlightItem(day)"
				@click="selectItem(day)"
			>
				<v-list-item-title>{{ day }}일차</v-list-item-title>
			</v-list-item>
		</v-list>
	</v-menu>
</template>
  
  
<style scoped>
.v-list-item--highlighted {	
	background-color: lightgray !important;
}
</style>
  