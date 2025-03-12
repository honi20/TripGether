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
    store.getHostedTripList()
})
</script>

<template>
    <h3 class="mb-2">내가 모집한 여행</h3>
    <hr>
    <v-data-table items-per-page="5"
    :items="store.hostedTripList">
    <tbody>
        <tr
        v-for="trip in store.hostedTripList"
        :key="trip.matchingUuid"
        @click="openDetails(trip)">
            <td style="width: 55%">
                <b>{{ trip.matchingTitle }}</b>
            </td>
            <td style="color: #888888; width: 30%;">
                {{ trip.matchingRegion }}
            </td>
            <td style="width: 15%">
                {{ trip.currentPersonCnt }} / {{ trip.recruitPersonCnt }}
            </td>
        </tr>
    </tbody>        
    </v-data-table>
</template>

<style scoped>
</style>