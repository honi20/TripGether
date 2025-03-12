<script setup>
import { usePlanStore } from '@/stores/plan';
import { ref } from 'vue';

const props = defineProps({
  place: Object,
  selectedDayIndex: Number
})

const emit = defineEmits(["cardUpdateEmit"])

const store = usePlanStore()

const deletePlanCard = () => {
	store.deletePlanCard(props.selectedDayIndex, props.place)
}

const editingCard = ref(false)

const updatePlanCard = () => {
	emit("cardUpdateEmit")
	editingCard.value = true
	console.log('update')
}

const updateInfo = ref({
	place_order: props.place.place_order,
	time: props.place.time,
	name: props.place.name,
	description: props.place.description,
	budget: props.place.budget
})

// 수정
const completeEdit = () => {
	// 해당 일정 카드 정보 수정
	store.editPlanCard(props.selectedDayIndex, updateInfo.value)

	editingCard.value = false
}

// 수정 취소
const cancelEdit = () => {
	editingCard.value = false
}
</script>

<template>
    <v-card width="100%" min-width="300px" class="mt-1 ml-1 mb-5 py-2 pl-2 rounded-lg" variant="outlined">
        <!-- <v-container> -->
            <v-row>
                <v-col cols="9">
                    <v-card-title>
                    <b>{{ place.name }}</b>
                    </v-card-title>
					<!-- 기본 -->
					<template v-if="!editingCard">
						<v-card-text>
							<p class="mb-3">{{ place.description }}</p>
							<div v-if="place.budget!=0">
                            <v-icon icon="mdi-cash-multiple"/>
                            {{ Number(place.budget).toLocaleString() }}원   
                        </div>
						</v-card-text>
					</template>

					<!-- 수정 -->
					<template v-else>
						<v-card-text class="white text--primary">
							<v-text-field v-model="updateInfo.time" label="시간" type="time" variant="underlined">
							</v-text-field>
						</v-card-text>
						<v-card-text class="white text--primary">
							<v-textarea v-model="updateInfo.description" label="메모"></v-textarea>
						</v-card-text>
						<v-card-text class="white text--primary">
							<v-text-field v-model="updateInfo.budget" label="비용" type="number" variant="underlined">
							</v-text-field>
						</v-card-text>
					</template>
                </v-col>
                <v-col cols="3">
					<!-- 기본 -->
                    <div v-if="!editingCard" class="d-flex flex-row mt-3">
                        <v-icon icon="mdi-pencil-outline" @click="updatePlanCard"></v-icon>
                        <v-icon icon="mdi-delete-outline" @click="deletePlanCard"></v-icon>
                    </div>

					<!-- 수정 -->
					<div v-else class="d-flex flex-row mt-3">
                        <v-icon icon="mdi-check" @click="completeEdit"></v-icon>
                        <v-icon icon="mdi-close" @click="cancelEdit"></v-icon>
                    </div>
                </v-col>
            </v-row>
        <!-- </v-container> -->
        
    </v-card>
</template>

<style scoped>
.card {
	color: #4D7952;
}
</style>