<script setup>
import SearchCardComponent from "@/components/matching/main/search/SearchCardComponent.vue";

import { onMounted } from "vue";
import { useMatchingStore } from "@/stores/matching";

const matchingStore = useMatchingStore()


</script>

<template>
    <div class="d-flex justify-start">
        <v-row>
            <template v-if="matchingStore.searchResult.length === 0">
                <div class="d-flex flex-column mx-auto text-color-gray">
                    <div class="mx-auto"><v-icon icon="mdi-emoticon-sad-outline" size="300" color="grey"></v-icon></div>
                    <p class="text-h5">검색 조건과 일치하는 여행이 없습니다.</p>

                </div>
            </template>
            <template v-else>
                <v-col cols="4"
                v-for="matching in matchingStore.searchResult" :key="matching.uuid">
                    <SearchCardComponent :matching="matching"/>
                </v-col>
            </template>
        </v-row>
    </div>
</template>

<style scoped>
/* div {
   border: 1px solid black; 
} */
</style>