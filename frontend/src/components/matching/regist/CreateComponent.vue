<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { VNumberInput } from 'vuetify/labs/VNumberInput'
import { usePlanStore } from '@/stores/plan'
import { useMatchingStore } from '@/stores/matching';

const planStore = usePlanStore()
const matchingStore = useMatchingStore()
const route = useRoute();
const router = useRouter();

onMounted(() => {
	planStore.getPlan(route.params.plan_uuid)
})

const inputData = ref({
    planUuid: route.params.plan_uuid,
    title: '',
    region: '',
    recruitPersonCnt: 1, 
    description: '',
	themes: {
		forEnergetic: 0,
		forLeisurely: 0,
		forNatural: 0,
		forUrban: 0,
		forHealing: 0,
		forShopping: 0,
		forMorning: 0,
		forNightOwl: 0
    }
})

const themeNames = [
	{ key: 'forEnergetic', label: '알찬' },
	{ key: 'forLeisurely', label: '여유로운' },
	{ key: 'forNatural', label: '자연' },
	{ key: 'forUrban', label: '도시' },
	{ key: 'forHealing', label: '힐링' },
	{ key: 'forShopping', label: '쇼핑' },
	{ key: 'forMorning', label: '아침형' },
	{ key: 'forNightOwl', label: '올빼미형' }
];

const toggleTheme = (key) => {
	inputData.value.themes[key] = inputData.value.themes[key] ? 0 : 1;
};

const submitForm = () => {
	// console.log(inputData.value)
	matchingStore.createMatching(inputData.value);
	// matchingStore.getHostedTripList();
	router.push({name: 'matchingMain'})
};
</script>

<template>
    <v-container class="ma-4">
        <v-row style="align-items: center;">
			<v-col cols="7">
				<input type="text" v-model="inputData.title" placeholder="여행 이름을 입력하세요." variant="plain" style="font-size: 30px; padding-left: 10px; width: 80%">
				<!-- <v-text-field v-model="inputTitle" variant="plain" placeholder="여행 이름을 입력하세요."></v-text-field> -->
			</v-col>
			<v-col cols="4"></v-col>
			<v-col cols="1">
				<v-btn color="#bdd1f7" variant="flat" height="50px" @click="submitForm">
					등록
				</v-btn>
			</v-col>
		</v-row>
		<v-container class="mt-4">
			<v-row class="my-4">
				<v-col cols="2" class="pt-4"><b>여행 일정</b></v-col>
				<v-col cols="10" class="pl-0">
					<input type="text" :value="planStore.planInfo.startDate" 
					style="text-align: center; border: 1px solid lightgray; border-radius: 5px; padding: 3px 4px 4px 0px; width: 110px;" readonly/>
					<!-- :size="planStore.planInfo.startDate.length" 로 사이즈 조절하고 싶다...-->
					~
					<input type="text" :value="planStore.planInfo.endDate" 
					style="text-align: center; border: 1px solid lightgray; border-radius: 5px; padding: 3px 4px 4px 0px; width: 110px;" readonly/>
				</v-col>
			</v-row>
			<v-row class="my-4">
				<v-col cols="2"><b>테마</b></v-col>
				<v-col cols="10" class="pa-0">					
					<v-chip-group
					selected-class="text-primary"
					multiple
					>
					<v-chip
						v-for="theme in themeNames"
						:key="theme.key"
						:text="`# ${theme.label}`"
						@click="toggleTheme(theme.key)"
					></v-chip>
					</v-chip-group>					
				</v-col>
			</v-row>
			<v-row class="mt-4">
				<v-col cols="4">
					<label><b>지역</b></label>
					<input type="text" v-model="inputData.region" 
					placeholder="여행지역을 입력하세요." variant="outlined" 
					style="border: 1px solid lightgray; border-radius: 5px; 
					margin-left:20px; padding: 3px 0px 4px 10px; width: 80%;">
				</v-col>
				<v-col cols="4"><b>비용</b>
					<input type="text" :value="planStore.planInfo.totalBudget"
					style="border: 1px solid lightgray; border-radius: 5px; 
					margin-left:20px; padding: 3px 0px 4px 10px; width: 80%;" readonly/>
				</v-col>
				<v-col cols="1" class="pl-13 pr-1">
					<b>인원</b>
				</v-col>
				<v-col cols="2" class="pl-0 pr-10">
					<v-number-input 
					v-model="inputData.recruitPersonCnt"
					control-variant="split"
					:min="1"
					></v-number-input>
					<!-- style="border-radius: 5px;" -->
				</v-col>
				<v-col cols="1"></v-col>
			</v-row>
			<v-row class="mb-4">
				<v-col cols="2"><b>계획 이름</b></v-col>
				<v-col cols="10" class="pl-0">
					<input type="text" :value="planStore.planInfo.title"
					style="border: 1px solid lightgray; border-radius: 5px; padding: 3px 0px 4px 10px; width: 70%;" readonly/>
				</v-col>
			</v-row>
			<v-row class="mt-4">
				<v-col cols="2"><b>상세 설명</b></v-col>
			</v-row>
			<v-row class="d-flex flex-column px-3">
				<textarea 
				v-model="inputData.description"
				placeholder="상세설명을 입력하세요." 
				style="resize: vertical; margin:auto; padding: 10px 0px 0px 15px; border-radius: 5px;
				width: 100%; min-height: 100px; background-color: lightgrey;">
				</textarea>
			</v-row>
		</v-container>
    </v-container>
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
/* number input 전체를 감싸는 div */
.v-number-input { 
	display: block;
	border-radius: 5px;
}
/* .v-input__control{
	width: 50px;
	
}
/**number input 밑의 나머지 */
.v-input__details{ 
	display: none;
}
.v-messages {
	display: none;
}
</style>