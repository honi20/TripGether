import { defineStore } from "pinia"
import { ref, inject } from "vue"

export const useDiaryStore = defineStore('diary', () => {
    const axios = inject('axios')

    // 여행 기록 저장
    const saveDiaryPost = async (files, postInfo) => {

        // axios 전달
        const formData = new FormData()
        formData.append("data", new Blob([JSON.stringify(postInfo)], {type: 'application/json'}))
        
        for (var idx = 0; idx < files.length; idx++) {
            formData.append("files", files[idx])
        }

        await axios.post(`/diary/regist`, formData, {
            headers: {'Content-Type': 'multipart/form-data'},
        })
        .then(() => {
			console.log('기록 등록 완료')
			if (selectMatching.value === null) {
				getUserList()
			}
			else {
				getGroupList(selectMatching.value)
			}
		})
        .catch((err) => {
            console.log(err)
        })
    }

	const diaryList = ref([])
	const selectMatching = ref(null)

	// 선택 그룹
	const updateSelectMatching = (matchingUuid) => {
		selectMatching.value = matchingUuid
	}

	// 개인 기록 리스트
	const getUserList = async () => {
		await axios.get(`/diary/list`)
		.then((response) => {
			diaryList.value = response.data
			console.log(response.data)
		})
	}

	// 그룹 기록 리스트
	const getGroupList = async (matchingUuid) => {
		await axios.get(`/diary/list/${matchingUuid}`)
		.then((response) => {
			diaryList.value = response.data
			console.log(response.data)
		})
	}

	// 기록 상세보기
	const diaryDetail = ref(null)
	const getDiaryDetail = async (diaryUuid) => {
		await axios.get(`/diary/detail/${diaryUuid}`)
		.then((response) => {
			diaryDetail.value = response.data
			if (diaryDetail.value.writerProfile !== null)
				diaryDetail.value.writerProfile = diaryDetail.value.writerProfile.split('public')[1]
			else
				diaryDetail.value.writerProfile = "/img/noProfile.png"
		})
	}

	// 기록 삭제
	const deleteDiary = async (diaryUuid) => {
		await axios.delete(`/diary/delete/${diaryUuid}`)
		.then(() => {
			if (selectMatching.value === null) {
				getUserList()
			}
			else {
				getGroupList(selectMatching.value)
			}
		})
	}

	const relatedDiaryList = ref([])
	const getRelatedDiary = (placeName) => {

		axios.get(`/diary/search`, {params: 
            {location: placeName}
        })
		.then((response) => {
			// console.log(response.data)
			relatedDiaryList.value = response.data
		})
	}

	const initDiaryDetail = () => {
		diaryDetail.value = null
	}

    return {
		selectMatching, updateSelectMatching, initDiaryDetail,getRelatedDiary,relatedDiaryList,
        saveDiaryPost, diaryList, getUserList, getGroupList, diaryDetail, getDiaryDetail, deleteDiary
    }
})