<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router';
import { useMatchingStore } from "@/stores/matching"

const store = useMatchingStore()

const router = useRouter()
const openDetails = (trip) =>{
    router.push({ name: 'matchingDetail', params: { uuid: trip.matchingUuid }})
}
onMounted(() => {
    store.getAlmostFullList()
})
</script>

<!-- currentPersonCnt
: 
3
matchingTitle
: 
"바다 여행"
matchingUuid
: 
"2e392703-15c1-11ef-87b6-f4c88a25757d"
planEndDate
: 
"2024-06-21"
planStartDate
: 
"2024-06-20"
recruitPersonCnt
: 
4 -->

<template>
    <h3 class="mb-3">인원 마감 임박한 여행</h3>
    <hr>
    <v-table>
        <!-- :items-per-page="5" -->
    <tbody>
        <tr
        v-for="trip in store.almostFullList"
        :key="trip.matchingUuid"
        @click="openDetails(trip)">
            <td class="title-column">
                <b>{{ trip.matchingTitle }}</b>  
            </td>
            <td style="color: #888888;">
                {{ trip.matchingRegion }}
            </td>
            <td style="color: #888888;">
                {{ trip.planStartDate }} ~ {{ trip.planEndDate }}
            </td>
            <td>
                {{ trip.currentPersonCnt }} / {{ trip.recruitPersonCnt }}
            </td>
        </tr>  
    </tbody>
  </v-table>
  <hr>
</template>

<style scoped>
.title-column {
    width: 53%;
}
</style>