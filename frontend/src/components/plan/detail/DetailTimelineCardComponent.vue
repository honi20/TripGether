<script setup>
import { usePlanStore } from '@/stores/plan';

const props = defineProps({
  place: Object,
  selectedDayIndex: Number
})

</script>

<template>
    <v-card width="100%" min-width="250px" class="mt-1 mb-5 py-2 pl-2 rounded-lg" variant="outlined">
        <!-- <v-container>
            <v-row>
                <v-col cols="10"> -->
                    <v-card-title>
                    <b>{{ place.name }}</b>
                    </v-card-title>
					<!-- 기본 -->
					<v-card-text>
						<p class="mb-3">{{ place.description }}</p>
                        <div v-if="place.budget!=0">
                            <v-icon icon="mdi-cash-multiple"/>
                            {{ Number(place.budget).toLocaleString() }}원   
                        </div>
					</v-card-text>
                <!-- </v-col>
            </v-row>
        </v-container> -->
        
    </v-card>
</template>

<style scoped>
.card {
	color: #4D7952;
}
</style>