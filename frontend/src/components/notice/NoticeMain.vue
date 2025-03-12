<script setup>
import NoticeList from "@/components/notice/NoticeList.vue";
</script>

<template>
  <v-container class="max-width-container mx-auto mt-16 pa-8 rounded-lg">
    <div class="d-flex flex-column">
        <div class="mb-4" style="text-align: center;"><h1>받은 알림</h1></div>
      <hr />
    </div>
    <div style="height: 15px"></div>
    <NoticeList />
  </v-container>
  
</template>


<style scoped>
.max-width-container {
  max-width: 1000px; /* 원하는 최대 가로 길이 설정 (예: 1200px) */
  margin: auto; /* 가운데 정렬을 위해 margin을 auto로 설정 */
}</style>
