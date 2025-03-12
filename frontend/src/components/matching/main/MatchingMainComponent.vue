<script setup>
import { onMounted, ref } from 'vue';
import SearchHeader from '@/components/matching/main/SearchHeader.vue'
import AlmostFullComponent from '@/components/matching/main/AlmostFullComponent.vue'
import JoinedTrip from '@/components/matching/main/JoinedTrip.vue'
import HostedTrip from '@/components/matching/main/HostedTrip.vue'
import SearchResultComponent from "@/components/matching/main/search/SearchResultComponent.vue";
import { useMatchingStore } from '@/stores/matching';

const matchingStore = useMatchingStore()

const search = () => { 
    matchingStore.updateSearchState(true)
}

</script>

<template>
    <!-- <v-container style="height:fit-content"> -->
        <div class="d-flex justify-center">
            <SearchHeader @change-to-search-mode="search"/>
        </div>
        <template v-if="!matchingStore.isSearching" >
            <!-- <v-row style="align-items: center;">
                
            </v-row>  -->
            <v-row class="mt-13 mb-4 px-3 rounded-lg border-md">
                    <v-col cols="12" class="my-3">
                        <AlmostFullComponent />
                    </v-col>  
            </v-row>
            <v-row class="justify-space-between">
                <v-col class="mb-3 pt-6 px-6 rounded-lg border-md">
                    <JoinedTrip />
                </v-col>
                <div style="width:20px"></div>
                <v-col class="mb-3 pt-6 px-6 rounded-lg border-md">
                    <HostedTrip />
                </v-col>
            </v-row>
        </template>
        <template v-else class="mt-5">
            <SearchResultComponent/>
        </template>
    <!-- </v-container> -->
</template>

<style scoped>
/* .v-container{
    border: 1px solid black;
}
.v-row {
    border: 1px solid black;
}
.v-col {
    border: 1px solid black;
}
div{
    border: 1px solid black;
} */
</style>