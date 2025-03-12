<script setup>
import { ref } from 'vue';
import { useMatchingStore } from "@/stores/matching";
import PlanDetailView from '@/components/plan/detail/DetailComponent.vue'

const tab = ref(null);

const matchingStore = useMatchingStore()
const viewInMatchingDetail = true;
</script>

<template>
    <div>
        <v-card>
            <v-tabs
                bg-color="#8BB4C7"
                v-model="tab"
                fixed-tabs
            >
                <v-tab value="Description">상세 설명</v-tab>

                <v-tab value="PlanDetail">여행 계획</v-tab>
            </v-tabs>

            <v-card-text>
            <v-tabs-window v-model="tab">
                <v-tabs-window-item value="Description">
                    <textarea 
                    :value="matchingStore.matchingDetail.description"
                    style="resize: vertical; margin:auto; padding: 10px 0px 0px 15px; border-radius: 5px;
                    width: 100%; min-height: 100px; background-color: #E0E1E3;" readonly>
                    </textarea>
                </v-tabs-window-item>

                <v-tabs-window-item value="PlanDetail">
                    <PlanDetailView :planId="matchingStore.matchingDetail.planUuid" :viewInMatchingDetail="viewInMatchingDetail"/>
                </v-tabs-window-item>
            </v-tabs-window>
            </v-card-text>
        </v-card>
    </div>
</template>

<style scoped>

</style>