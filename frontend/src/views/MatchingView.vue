<template>
    <v-main>
      <v-container class="mt-15">
        <!-- <h1>MatchingView 입니다.</h1> -->
        <RouterView />
      </v-container>
    </v-main>
</template>

<script setup>
import { useMatchingStore } from '@/stores/matching';
import { onMounted, onUpdated } from 'vue';
import { usePlanStore } from '@/stores/plan'
import { useRoute, useRouter } from 'vue-router';

const matchingStore = useMatchingStore()
const planStore = usePlanStore()
const route = useRoute();

// onMounted(async () => {
//   // alert('onMount')
//   await matchingStore.getAlmostFullList()
//   await matchingStore.getHostedTripList()
//   await matchingStore.getJoinedTripList()
//   await matchingStore.getMatchingDetail(route.params.uuid)
//   await planStore.getPlan(route.params.plan_uuid)
// })
</script>

<style scoped>
.v-container { 
  width: 90%;
}
</style>