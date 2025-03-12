<script setup>
import { onMounted } from "vue";
import { useRoute } from 'vue-router'
import { useMatchingStore } from "@/stores/matching";
import HeaderComponent from "@/components/matching/detail/HeaderComponent.vue"
import TabComponent from "@/components/matching/detail/TabComponent.vue"
import { onUpdated } from 'vue';

const route = useRoute()
const matchingStore = useMatchingStore()
onMounted(()=>{
    matchingStore.getMatchingDetail(route.params.uuid)
})

const props = defineProps({
    type: String
})

</script>

<template>
    <HeaderComponent />
    <TabComponent />
</template>

<style scoped>
</style>
