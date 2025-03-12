import { defineStore } from "pinia"
import { ref, inject } from "vue"

export const usePlaceStore = defineStore('place', () => {

    const axios = inject('axios')
    
	const placeList = ref([])

	// 검색 조건에 맞는 장소 리스트 가져오기
	const getPlaceList = async (input) => {
		await axios.get('/place/search', {params: 
			{searchInput: input}
		})
			.then((response) => {
				console.log(response.data.documents)
				placeList.value = response.data.documents
				console.log(placeList.value)
			})
			.catch((error) => {
				console.log(error)
			})
	}

	const relatedPlaceList = ref([])
	// 검색 조건에 맞는 장소 리스트 가져오기
	const getRelatedPlaceList = async (location, target) => {
		await axios.get('/place/chat', {params: 
			{location:location, target: target}
		})
			.then((response) => {
				// console.log(response.data)
				relatedPlaceList.value = response.data
				// console.log(relatedPlaceList.value)
			})
			.catch((error) => {
				console.log(error)
			})
	}

	const relatedVlogList = ref([])
	const getRelatedVlogList = async (keyword) => {
		relatedVlogList.value = []
		console.log('get vlog')
		await axios.get('/place/youtube', {params: 
			{keyword: keyword}
		})
		.then((response) => {
			relatedVlogList.value = response.data.items
			console.log(relatedVlogList.value)
		})
	}

    return {
		placeList, getPlaceList, relatedPlaceList, getRelatedPlaceList, relatedVlogList, getRelatedVlogList
    }
})