<script setup>
import { ref } from 'vue';
import { useRoute } from 'vue-router';
import { useUserStore } from "@/stores/user";
import { useMatchingStore } from "@/stores/matching";
import { useNoticeStore } from "@/stores/notice";
const route = useRoute()
const userStore = useUserStore()
const noticeStore = useNoticeStore()
const matchingStore = useMatchingStore()

const emit = defineEmits(["dialogVisibleEmit"]);

const pushCancel = () => { 
    emit("dialogVisibleEmit");
}
const pushSubmit = () => { 
    noticeContent.value.title = `${userStore.user.userName}님이 ${matchingStore.matchingDetail.title}에 동행 신청을 보냈습니다.`;
    noticeStore.sendNotice(route.params.uuid, noticeContent.value)
    emit("dialogVisibleEmit");
}

const noticeContent = ref({
    type: '동행신청 알림',
    title: '',
    msg: ''
})

</script>

<template>
    <v-card-text>
        <div class="mb-4">
            <b>{{ matchingStore.matchingDetail.title }}</b>의 <br>
            주최자 <b>{{ matchingStore.matchingDetail.writerName }}</b>님께 동행 신청을 보냅니다.
        </div>

        <div class="mb-2 text-medium-emphasis">신청 메세지</div>
        <v-textarea
            :counter="300"
            class="mb-1"
            rows="10"
            variant="outlined"
            persistent-counter
            v-model="noticeContent.msg"
        ></v-textarea>
    </v-card-text>

    <v-card-actions class="mb-2 d-flex justify-center">
        <v-btn
        class="text-none"
        color="#bdd1f7"
        rounded="m"
        text="취소"
        variant="outlined"
        @click="pushCancel"
        ></v-btn>
        <v-btn
            class="text-none"
            color="#bdd1f7"
            rounded="m"
            text="신청"
            variant="flat"
            @click="pushSubmit"
        ></v-btn>
    </v-card-actions>
</template>

<style scoped>
</style>