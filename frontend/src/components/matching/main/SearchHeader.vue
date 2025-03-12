<script setup>
import { ref, computed, onMounted } from 'vue';
import { VNumberInput } from 'vuetify/labs/VNumberInput';
import { useMatchingStore } from '@/stores/matching'

onMounted(() => { 
    // alert("Header onMount")
})


const matchingStore = useMatchingStore()

const toggleFilterComponent = () => { 
    matchingStore.updateShowState(!matchingStore.showFilter)
    
    // showFilter.value = !showFilter.value
}



// test
const periodButton = ref()
const budgetButton = ref()
const personnelButton = ref()
const sortButton = ref()
const themeButton = ref()

const emit = defineEmits(["changeToSearchMode"])

const search = () => { 
    // searchCondition 보내주기
    searchCondition.value.theme = selectedThemes
    // console.log(searchCondition.value)
    matchingStore.getSearchResult(searchCondition)
    // 부모 컴포넌트의 값 true로 바꿔야 함
    emit("changeToSearchMode")
}

const searchCondition = ref({
    keyword: '',
    maxPeriod: 0,
    maxBudget: 0,
    maxPersonCnt: 0,
    sortType: '',
    theme: []  
})

const periodItems = [
	{ value: 1, text: '당일치기' },
	{ value: 3, text: '3일 이내' },
	{ value: 7, text: '일주일 이내' },
	{ value: 30, text: '한달 이내' },
	{ value: 365, text: '1년 이내' },
]
const periodButtonText = computed(() => {
      const selectedItem = periodItems.find(
          (item) => { item.value == searchCondition.value.maxPeriod }
      );
      return selectedItem ? selectedItem.text : '기간';
});

const budgetButtonText = computed(() => {
      return searchCondition.value.maxBudget
        ? `${searchCondition.value.maxBudget}만원`
        : '예산';
    });

    
const sortItems = ['최신순', '인기순']
const sortButtonText = computed(() => {
      return searchCondition.value.sortType
        ? searchCondition.value.sortType
        : '정렬';
});

const personnelButtonText = computed(() => {
      return searchCondition.value.maxPersonCnt
        ? `${searchCondition.value.maxPersonCnt}명`
        : '인원';
    });

const selectedThemes = ref([])
const themeItems = [
	{ value: 'for_energetic', text: '알찬' },
	{ value: 'for_leisurely', text: '여유로운' },
	{ value: 'for_natural', text: '자연' },
	{ value: 'for_urban', text: '도시' },
	{ value: 'for_healing', text: '힐링' },
	{ value: 'for_shopping', text: '쇼핑' },
	{ value: 'for_morning', text: '아침형' },
	{ value: 'for_nightOwl', text: '올빼미형' }
]
</script>

<template>
    <div class="d-flex flex-column">
        <div class="mb-5" style="text-align: center;"><h1>여행 검색</h1></div>
        <v-text-field
            v-model="searchCondition.keyword"
            label="Search"
            prepend-inner-icon="mdi-tune"
            @click:prepend-inner="toggleFilterComponent"
            append-inner-icon="mdi-magnify"
            @click:append-inner="search"
            variant="outlined"
            hide-details
            single-line
            min-width="500px"
            width="auto"
        ></v-text-field>

        <div class="ma-2" v-if="matchingStore.showFilter">
            <!-- 1. 기간 -->
            <v-btn ref="periodButton"
            color="#176876"
            variant="tonal"
            rounded="xl"
            append-icon="mdi-chevron-down"
            class="mx-1"
            > {{ periodButtonText }} </v-btn>
            <v-select
                :items="periodItems"
                item-title="text"
                item-value="value"
                v-model="searchCondition.maxPeriod"
                hide-details
                :menu-props="{
                            activator: periodButton,
                            openOnClick: true,
                            }"
                v-show="false"
            >
            </v-select> 
            <!-- 2. 예산 -->
            <v-menu
            v-model="budgetButton"
            :close-on-content-click="false"
            location="bottom"
            >
            <template v-slot:activator="{ props }">
                <v-btn v-bind="props"
                    color="#176876"
                    variant="tonal"
                    rounded="xl"
                    append-icon="mdi-chevron-down"
                    class="mx-1"
                    > {{ budgetButtonText }} </v-btn>
            </template>

            <v-card min-width="300">
                <v-list>
                <v-list-item>
                <v-slider
                        v-model="searchCondition.maxBudget"
                        max="100"
                        min="0"                        
                        step="1"
                        class="slider"
                        >
                        </v-slider>
                </v-list-item>
                </v-list>
            </v-card>
            </v-menu>

            <!-- 3. 인원-->
            <v-menu
            v-model="personnelButton"
            :close-on-content-click="false"
            location="bottom"
            >
            <template v-slot:activator="{ props }">
                <v-btn v-bind="props"
                    color="#176876"
                    variant="tonal"
                    rounded="xl"
                    append-icon="mdi-chevron-down"
                    class="mx-1"
                    > {{ personnelButtonText }} </v-btn>
            </template>
            <v-card min-width="300">
                <v-list>
                <v-list-item>
                    <v-number-input 
					v-model="searchCondition.maxPersonCnt"
					control-variant="split"
					:min="1"
					></v-number-input>
                </v-list-item>
                </v-list>
            </v-card>
            </v-menu>
            <!-- 4. 정렬 -->
            <v-btn ref="sortButton"
            color="#176876"
            variant="tonal"
            rounded="xl"
            append-icon="mdi-chevron-down"
            text="정렬"
            class="mx-1"
            > {{ sortButtonText }} </v-btn>
            <v-select
                :items="sortItems"
                item-title="text"
                item-value="value"
                v-model="searchCondition.sortType"
                return-object
                hide-details
                :menu-props="{
                            activator: sortButton,
                            openOnClick: true,
                            }"
                v-show="false"
            >
            </v-select> 
            <!-- 5. 테마 버튼 -->
            <v-btn ref="themeButton"
            color="#176876"
            variant="tonal"
            rounded="xl"
            append-icon="mdi-chevron-down"
            class="mx-1"
            > 여행 테마 </v-btn>
            <v-select
                :items="themeItems"
                multiple
                item-title="text"
                item-value="value"
                v-model="selectedThemes"
                hide-details
                :menu-props="{
                            activator: themeButton,
                            openOnClick: true,
                            }"
                v-show="false"
            >
            </v-select>            
        </div>
    </div>
</template>

<style scoped>
.slider-container {
  width: 200px; /* 원하는 너비로 설정하세요 */
}

.slider {
  width: 100%;
}


</style>