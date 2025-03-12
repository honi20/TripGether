import { defineStore } from "pinia"
import { ref, inject } from "vue"

const { VITE_SERVER_URL } = import.meta.env // http://localhost:8080/api/v1

export const useNoticeStore = defineStore('notice', () => {

    const axios = inject('axios')

    // send Notice    
    const sendNotice = (matchingUuid, notice) => { 
        // console.log(notice)
        axios.post(`/notice/create/apply/${matchingUuid}`, notice)
        .then((response) => {
            alert("알림 전송!")
            })
        .catch((error) => { 
            console.log(error)
        })   

    }

    const sendReturn = (matchingUuid, notice) => { 
        axios.post(`/notice/create/return/${matchingUuid}`, notice)
        .then((response) => {
            alert("알림 전송!")
            })
        .catch((error) => { 
            console.log(error)
        })
    }
    
    // read totalNoticeList
    const noticeList = ref([])    
    const getNoticeList = () => {
        // console.log(axios.defaults.headers.common['Authorization'])
        axios.get(`/notice/list`)
        .then((response) => {
            noticeList.value = response.data
        })
        .catch((error) => {
            console.log(error)
        })
    }
    
    // read noticeDetail
    const notice = ref({})
    const getNoticeDetail = (uuid) => {
        axios.get(`/notice/detail/${uuid}`)
            .then((response) => {
                notice.value = response.data
                console.log(notice.value)
        })
    }

    // update notice read_status
    const readNotice = (uuid) => {
        axios.put(`${VITE_SERVER_URL}/notice/read/${uuid}` )
            .then(() => { 
                getNoticeList()
         })
    }

    // update notice process_status
    const processNotice = (uuid) => {
        axios.put(`${VITE_SERVER_URL}/notice/process/${uuid}` )
            .then(() => { 
                getNoticeList()
            })        
    }

    // delete notice
    const deleteNotice = (uuid) => { 
        axios.delete(`/notice/${uuid}`)
            .then(() => { 
            getNoticeList()
         })
    }

    return {
        sendNotice, noticeList, getNoticeList, notice, getNoticeDetail, readNotice, deleteNotice, processNotice, sendReturn
    }
})