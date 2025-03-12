import { defineStore } from "pinia"
import router from '@/router'
import { ref, inject } from "vue"

export const usePlanStore = defineStore('plan', () => {

    const axios = inject('axios')
     
    // read totalPersonalPlanList
    const planList = ref([])

    const getPlanList = () => {
        axios.get(`/plan/list`)
        .then((response) => {
            planList.value = response.data

			planList.value.forEach(plan => {
				if (plan.planImage !== null) {
					plan.planImage = plan.planImage.split('public')[1]
				}
				else
					plan.planImage = "/img/noPlanImg.png"
			});

			console.log(planList.value)
        })
        .catch((error) => {
            console.log(error)
        })
    }

	const totalTravelDays = ref(0)
	const startDate = ref(null)
	const endDate = ref(null)

	// 계획의 전체적인 정보
	const plan = ref({})

	// 계획의 각 일정만 저장
	const schedules = ref([])

	// 계획의 썸네일 이미지
	const thumbnail = ref(null)
    
	// 여행 날짜 정보 저장
	const initTravelDays = (startDateFormated, endDateFormated, travelDays) => {
		startDate.value = startDateFormated
		endDate.value = endDateFormated
		totalTravelDays.value = travelDays

		// 계획에 추가한 여행 배열 초기화
		schedules.value = []
		for (let day = 0; day < totalTravelDays.value; day++) {
			schedules.value.push({
				'day': day,
				'places': [],
				'idx': 0
			})
		}
		// console.log(schedules.value)
	}

	// 장소 추가에서 저장된 장소 저장
	const addPlaceToPlans = (place, day) => {
		schedules.value[day].places.push({
			'place_order': schedules.value[day].idx++,
			'time': "",
			'name': place.place_name,
			'description': '',
			'budget': 0,
			'latitude': place.y,
			'longitude': place.x
		})
		// console.log(schedules.value)
	}

	// 일정 카드 삭제
	const deletePlanCard = (selectedDayIndex, place) => {
		const idx = schedules.value[selectedDayIndex].places.findIndex((element) => element === place)
		if (idx >= 0)
			schedules.value[selectedDayIndex].places.splice(idx, 1);
	}

	// 일정 카드 수정
	const editPlanCard = (selectedDayIndex, updateInfo) => {
		console.log(updateInfo)
		const idx = schedules.value[selectedDayIndex].places.findIndex((element) => element.place_order == updateInfo.place_order)
		console.log(idx)
		if (idx >= 0) {
			schedules.value[selectedDayIndex].places[idx].time = updateInfo.time
			schedules.value[selectedDayIndex].places[idx].description = updateInfo.description
			schedules.value[selectedDayIndex].places[idx].budget = updateInfo.budget
		}
	}

	// 계획 등록
	const createPlan = async (inputTitle, thumbnailFile) => {
		console.log("create")
		plan.value = {
			title: inputTitle,
			startDate: startDate.value,
			endDate: endDate.value,
			schedules: schedules.value
		}
		thumbnail.value = thumbnailFile

		console.log('plan')
		console.log(plan.value)
		console.log('thumbnail file')
		console.log(thumbnail.value)

		// axios 전달
		const formData = new FormData()
		formData.append("data", new Blob([JSON.stringify(plan.value)], {type: 'application/json'}))
		formData.append("file", thumbnail.value)

		await axios.post(`/plan/regist`, formData, {
			headers: {'Content-Type': 'multipart/form-data'},
		})
		.then((response) => {
			// 초기화
			totalTravelDays.value = 0
			startDate.value = null
			endDate.value = null
			plan.value = {}
			schedules.value = []
			thumbnail.value = null

			getPlanList()

			// 계획 리스트로 이동
			router.push({name: 'planList'})
		})
	}

	// 계획 삭제
	const deletePlan = (uuid) => {
		axios.delete(`/plan/${uuid}`)
		.then(() => {
			getPlanList()
		})
	}

	// 보여질 계획
	const planInfo = ref({})
	const getPlan = async (uuid) => {
		await axios.get(`/plan/${uuid}`)
		.then((response) => {
			planInfo.value = response.data

			if (planInfo.value.startDate !== null)
				planInfo.value.startDate = planInfo.value.startDate.split(" ")[0]
			if (planInfo.value.endDate !== null)
				planInfo.value.endDate = planInfo.value.endDate.split(" ")[0]

			console.log(planInfo.value)
		})
	}

    return {
		planList, getPlanList, totalTravelDays, startDate, endDate, schedules, 
		initTravelDays, addPlaceToPlans, deletePlanCard, editPlanCard, createPlan,
		deletePlan, getPlan, planInfo,
    }
})

/**
plan = {
	title: {제목},
	startDate: {시작 날짜 (yyyy-mm-dd)},
	endDate: {종료 날짜},
	schedules: [
		{
			'day': {k일차 (0부터 시작)},
			'idx': {장소 추가할 때 id 값 지정 시 사용}
			'places': [
				{
					'place_order': {식별자값},
					'time': {시간 (HH:MM)},
					'name': {장소 이름},
					'description': {설명},
					'budget': {예산},
					'latitude': {위도},
					'longitude': {경도}
				},
				{
					"place_order": schedules.value[0].idx++,
					"time": '08:00',
					"name": '집',
					"description": '출발',
					"budget": 0, 
					"latitude": "127.06887740990024",
					"longitude": "37.204942434595004"
				},
				...
			],
		},
		{
			'day': 1,
			'idx': 0
			'places': [
				...
			],
		},
		...
	]
}
thumbnail = {계획 썸네일 이미지 파일}
*/
