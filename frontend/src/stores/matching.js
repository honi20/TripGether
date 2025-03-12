import { defineStore } from "pinia"
import { ref, inject } from "vue"
import { useRouter } from "vue-router"

const { VITE_SERVER_URL } = import.meta.env // http://localhost:8080/api/v1

export const useMatchingStore = defineStore('matching', () => {

    const router = useRouter()
    const axios = inject('axios')

    // 마감 임박 여행 리스트 가져오기
    const almostFullList = ref([])

    const getAlmostFullList = async () => {
        await axios.get(`/matching/list/popular`, {params: 
            {maxCnt: 3}
        })
        .then((response) => {
            // console.log(response.data)
            almostFullList.value = response.data
        })
    }

    // 유저가 포함된 리스트 가져오기
    const myMatchingList = ref([])
    const getMyMatchingList = async () => {
        await axios.get(`/matching/myList`)
        .then((response) => {
            console.log(response.data)
            myMatchingList.value = response.data
        })
    }

    // 유저가 가입한 리스트 가져오기
    const joinedTripList = ref([])
    const getJoinedTripList = async () => {
        await axios.get(`/matching/list/join`)
        .then((response) => {
            console.log(response.data)
            joinedTripList.value = response.data
        })
    }

    // 유저가 모집한 리스트 가져오기
    const hostedTripList = ref([])
    const getHostedTripList = async () => {
        await axios.get(`/matching/list/recruit`)
        .then((response) => {
            console.log(response.data)
            hostedTripList.value = response.data
        })
    }    

    // 동행매칭 생성하기
    const createMatching = async (inputData) => {
        console.log(inputData)
        await axios.post(`/matching/regist`, inputData)
        .then((response) => {
            getHostedTripList()
        })
        .catch((error)=>{
            console.log(error.response)
        })
    }

    // 동행매칭 상세정보 가져오기
    const matchingDetail = ref({})
    const matchingDetailTheme = ref({})
    /**
     * {
    "title": "같이 부산 갈 사람~!",
    "writerId": "123",
    "writerName": "테스트유저",
    "writerGender": "여성",
    "writerBirth": 2003,
    "region": "부산광역시",
    "startDate": "2024-05-23",
    "endDate": "2024-05-23",
    "recruitPersonCnt": 2,
    "currentPersonCnt": 1,
    "description": "관통끝나구 고생했는데 부산 가서 바다 볼거야아아\n회는 안 먹으려나...?",
    "totalBudget": 78000,
    "planUuid": "cb2af7f1-1706-11ef-8d7a-98838987e76f",
    "themes": {
        "forEnergetic": true,
        "forLeisurely": false,
        "forNatural": true,
        "forUrban": false,
        "forHealing": true,
        "forShopping": false,
        "forMorning": false,
        "forNightOwl": false
    }
}
     */

    const getMatchingDetail = async (matchingUuid) => { 
        await axios.get(`/matching/detail/${matchingUuid}`)
            .then((response) => { 
                matchingDetail.value = response.data
                matchingDetailTheme.value = response.data.themes
				console.log(matchingDetail.value)
            })

		// 조회수 증가
		await axios.put(`/matching/view/${matchingUuid}`)
		.then(() => {
		})
    }

    const requestGroupManageDto = ref({
        matchingUuid: '',
        userId : ''
    })
    // 그룹에 등록
    const insertToGroup = async (matchingUuid, userId) => { 
        
        requestGroupManageDto.value.matchingUuid = matchingUuid
        requestGroupManageDto.value.userId = userId
        await axios.post(`/matching/group`, requestGroupManageDto.value)
        .then((response) => { 
            // alert("그룹에 정상 등록!")
        })
    }
    // 그룹에서 삭제
    const deleteFromGroup = async (matchingUuid, userId) => { 
        await axios.delete(`/matching/group/${matchingUuid}/${userId}`)
        .then((response) => { 
            // lert("그룹에서 정상 삭제!")
            getGroupMembers(matchingUuid)
        })
    }

    const groupMemberList = ref([])
    const getGroupMembers = async (matchingUuid) => {
        await axios.get(`/matching/group/${matchingUuid}`)
            .then((response) => { 
            groupMemberList.value = response.data

        })
    }

    // 필터링 검색
    const searchResult = ref([])
    // const searchResult = ref(
    //     [
    //         {
    //             "uuid": "a36c81a4-17d4-11ef-8ef3-98838987e76f",
    //             "title": "바다 여행",
    //             "region": "강원도 강릉시",
    //             "recruit_person_cnt": 4,
    //             "current_person_cnt": 3,
    //             "description": "하루종일 수영하기",
    //             "view_cnt": 0,
    //             "created_at": "2024-05-21T12:19:59",
    //             "writer_id": "123",
    //             "plan_id": 4,
    //             "start_date": "2024-06-20",
    //             "end_date": "2024-06-21",
    //             "plan_img": null
    //         },
    //         {
    //             "uuid": "a36c81a4-17d4-11ef-8ef3-98838987e76f",
    //             "title": "바다 여행",
    //             "region": "강원도 강릉시",
    //             "recruit_person_cnt": 4,
    //             "current_person_cnt": 3,
    //             "description": "하루종일 수영하기",
    //             "view_cnt": 0,
    //             "created_at": "2024-05-21T12:19:59",
    //             "writer_id": "123",
    //             "plan_id": 4,
    //             "start_date": "2024-06-20",
    //             "end_date": "2024-06-21",
    //             "plan_img": null
    //         },
    //     ]
    // )

    const getSearchResult = (searchCondition) => { 
        // console.log(searchCondition.value)
        var params = new URLSearchParams();
        params.append("keyword", searchCondition.value.keyword);
        params.append("maxPeriod", searchCondition.value.maxPeriod);
        params.append("maxBudget", searchCondition.value.maxBudget);
        params.append("maxPersonCnt", searchCondition.value.maxPersonCnt);
        params.append("sortType", searchCondition.value.sortType);

        searchCondition.value.theme.forEach((eachTheme) => {
            params.append("theme", eachTheme);
        });
        
        var request = {
            params: params
          };

        axios.get(`/matching/list/search`, request)
        .then((response) => {            
            searchResult.value = response.data
            // alert("목록 조회 성공")
            console.log(searchResult.value)
            searchResult.value.forEach(result => {
				if (result.plan_img !== null) {
					result.plan_img = result.plan_img.split('public')[1]
				}
			});
        })
    }

    // 검색 여부 저장
    const isSearching = ref(false)
    const updateSearchState = (state) => {
        isSearching.value = state
    }

    // 필터 펼치기 여부 저장
    const showFilter = ref(false)
    const updateShowState = (state) => {
        showFilter.value = state
    }

    // 게시글 삭제
    const deleteMatchingPost = (matchingUuid) => {
        axios.delete(`/matching/${matchingUuid}`)
        .then(() => {
            router.push({name: "matchingMain"})
        })
    }

    return {
        almostFullList, getAlmostFullList, myMatchingList, getMyMatchingList, joinedTripList, getJoinedTripList, hostedTripList, getHostedTripList,
        createMatching, matchingDetail, matchingDetailTheme, getMatchingDetail, requestGroupManageDto, insertToGroup, deleteFromGroup,
        groupMemberList, getGroupMembers, searchResult, getSearchResult, isSearching, updateSearchState, showFilter, updateShowState, deleteMatchingPost
    }
})