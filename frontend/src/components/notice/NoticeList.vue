<script setup>
import { ref, onMounted, onUpdated } from "vue";
import NoticeDetail from "./NoticeDetail.vue";
import { useNoticeStore } from "@/stores/notice"
import { useRouter } from "vue-router";
import axios from "axios";

const { VITE_SERVER_URL } = import.meta.env // http://localhost:8080/api/v1

const store = useNoticeStore()
// 컴포넌트가 연결될 때 전체 목록을 불러온다.
onMounted(() => {
  store.getNoticeList()
})

/**
created_at : "2024-05-12T11:27:53.000+00:00"
processed_status : 1
read_status : 1
title : "벨님이 제주 올레길 여행에 새로운 동행 신청을 보냈습니다"
type : "신청 알림"
uuid : "abf77422-1052-11ef-96f7-f4c88a25757d"
 */

const selectedUuid = ref("")
const dialogVisible = ref(false);
// const selectedNotice = ref({})

const openDetails = function (notice) {
  // 해당되는 notice를 store.notice에 저장
  store.getNoticeDetail(notice.uuid); 
  if (notice.type !== '삭제 알림') {
    selectedUuid.value = notice.uuid
    dialogVisible.value = true;    
  }
  // 클릭시 해당 notice 읽음 처리
  if (notice.read_status===0){
    store.readNotice(notice.uuid)
  }
}
// dialog visible 변수
const dialogVisibleEmit = () => {
  dialogVisible.value = false;
}

const deleteNotice = function (notice) {
  store.deleteNotice(notice.uuid)
}

</script>

<template>
    <v-data-table :items-per-page="5">
    <tbody>
      <tr
        v-for="notice in store.noticeList"
        :key="notice.id"
        :class="{'unread-notice': notice.read_status === 0}"
      >
      <td>
        <v-icon :icon="notice.read_status === 0 ? 'mdi-email' : 'mdi-email-open'" />
      </td>
      <td>{{ notice.type }}</td>
      <td @click="openDetails(notice)">{{ notice.title }}</td>
      <td style="color: #888888;">{{ notice.created_at }}</td>
      <td><v-icon icon="mdi-check" 
        v-if="notice.processed_status==1"
        color="green"/></td>
      <td><v-icon icon="mdi-delete" @click="deleteNotice(notice)"/></td>
      </tr>
    </tbody>
  </v-data-table>

  <NoticeDetail
    :selectedUuid="selectedUuid"
    :dialogVisible="dialogVisible"
    @dialogVisibleEmit="dialogVisibleEmit"
  />
  
</template>

<style scoped>
.unread-notice {
  background-color: #C3D3C6; /* 읽지 않은(notice.read_status === 0) 공지사항의 배경색 */
}

</style>
