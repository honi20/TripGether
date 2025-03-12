<script setup>
import { ref } from 'vue';
import { useNoticeStore } from "@/stores/notice"
import { useMatchingStore } from "@/stores/matching";
import { useRoute, useRouter } from 'vue-router';
const route = useRoute()
const router = useRouter()
const store = useNoticeStore()
const matchingStore = useMatchingStore()

const props = defineProps(["selectedUuid", "dialogVisible"])

const emit = defineEmits(["dialogVisibleEmit"])

const moveToMatching = () => {
    // 모달 닫고
    emit("dialogVisibleEmit")
    router.push({name:'matchingDetail', params: { uuid: store.notice.matchingUuid }})
}

const closeModal = () => { 
    emit("dialogVisibleEmit")
}

const acceptJoin = (notice) => { 
    console.log("수락")
    // 알림 처리
    store.processNotice(notice.uuid)
    // 그룹에 해당 사람 추가
    matchingStore.insertToGroup(notice.matchingUuid, notice.sender_id)
    // 수락 알림 보내기
    noticeContent.value.receiverId = `${ store.notice.sender_id }`
    noticeContent.value.type = '수락'
    noticeContent.value.title = `${store.notice.matchingTitle}에 보낸 동행 신청이 수락되었습니다.`;
    // 모달 닫기
    emit("dialogVisibleEmit")
}

const refuseJoin = (notice) => { 
    console.log("거절")
    // 알림 처리
    store.processNotice(notice.uuid)
    // 거절 알림 보내기
    noticeContent.value.receiverId = `${store.notice.sender_id}`
    noticeContent.value.type = '거절'
    noticeContent.value.title = `${store.notice.matchingTitle}에 보낸 동행 신청이 거절되었습니다.`;
    store.sendReturn(store.notice.matchingUuid, noticeContent.value)
    // 모달 닫기
    emit("dialogVisibleEmit")
}

const acceptCancel = (notice) => { 
    console.log("수락")
    // 알림 처리
    store.processNotice(notice.uuid)
    // 그룹에 해당 사람 빼기
    matchingStore.deleteFromGroup(notice.matchingUuid, notice.sender_id)
    // 수락 알림 보내기
    noticeContent.value.receiverId = `${ store.notice.sender_id }`
    noticeContent.value.type = '수락'
    noticeContent.value.title = `${store.notice.matchingTitle}에 보낸 동행 취소 신청이 수락되었습니다.`;
    // 모달 닫기
    emit("dialogVisibleEmit")
}

const refuseCancel = (notice) => { 
    console.log("거절")
    // 알림 처리
    store.processNotice(notice.uuid)
    // 거절 알림 보내기
    noticeContent.value.receiverId = `${store.notice.sender_id}`
    noticeContent.value.type = '거절'
    noticeContent.value.title = `${store.notice.matchingTitle}에 보낸 동행 취소 신청이 거절되었습니다.`;
    store.sendReturn(store.notice.matchingUuid, noticeContent.value)
    // 모달 닫기
    emit("dialogVisibleEmit")
}

const noticeContent = ref({
    receiverId: '',
    type: '',
    title: ''
})

/*  
{
    "uuid": "11c66988-1730-11ef-8d7a-98838987e76f",
    "type": "신청 알림",
    "sender_id": "123",
    "userName": "테스트유저",
    "userGender": "여성",
    "userBirthYear": "2003",
    "matchingTitle": "마이데이와 함께하는 도운투어",
    "msg": "msg",
    "matchingUuid": "bdcf45f1-171e-11ef-8d7a-98838987e76f",
    "matchingId": 7,
    "processed_status": 0
}
*/
</script>
a
<template>
    <v-dialog v-model="$props.dialogVisible" max-width="500">
        <v-card class="pa-7" 
        v-if="store.notice.type==='동행신청 알림'">
            <div class="d-flex justify-space-between px-1">
				<v-card-title ><h3>받은 신청</h3></v-card-title>
			</div>
            <v-card-text>
                <div class="mb-4">
                    <b>{{ store.notice.userName }} 님 ({{ store.notice.userGender }}, {{ store.notice.userBirthYear }}년 생)</b>께서<br> 
                    <b>{{ store.notice.matchingTitle }}</b>에<br>
                    새로운 동행 신청을 보냈습니다.
                </div>

                <div class="mb-2 text-medium-emphasis">신청 메세지</div>
                <v-textarea
                    :counter="300"
                    class="mb-1"
                    rows="10"
                    variant="outlined"
                    persistent-counter
                    :value="store.notice.msg"
                    readonly
                ></v-textarea>
            </v-card-text>
            <v-card-actions class="d-flex justify-space-between px-5">
                <div>
                    <v-btn color="primary" @click="moveToMatching">해당 게시물로 이동</v-btn>
                </div>
                <!-- 얘네도 이미 처리된 상태면 보이면 안됨 -->
                <div v-show="store.notice.processed_status==0">
                    <v-btn
                    class="text-none"
                    color="#bdd1f7"
                    rounded="m"
                    text="수락"
                    variant="flat"
                    @click="acceptJoin(store.notice)"
                    ></v-btn>
                    <v-btn
                        class="text-none"
                        color="#bdd1f7"
                        rounded="m"
                        text="거절"
                        variant="outlined"
                        @click="refuseJoin(store.notice)"
                    ></v-btn>
                </div>
            </v-card-actions>
        </v-card>
        <v-card class="pa-7" 
        v-if="store.notice.type==='취소신청 알림'">
            <div class="d-flex justify-space-between px-1">
				<v-card-title ><h3>받은 신청</h3></v-card-title>
			</div>
            <v-card-text>
                <div class="mb-4">
                    <b>{{ store.notice.userName }} 님 ({{ store.notice.userGender }}, {{ store.notice.userBirthYear }}년 생)</b>께서<br> 
                    <b>{{ store.notice.matchingTitle }}</b>에<br>
                    동행 취소 신청을 보냈습니다.
                </div>

                <div class="mb-2 text-medium-emphasis">취소신청 메세지</div>
                <v-textarea
                    :counter="300"
                    class="mb-1"
                    rows="10"
                    variant="outlined"
                    persistent-counter
                    :value="store.notice.msg"
                    readonly
                ></v-textarea>
            </v-card-text>
            <v-card-actions class="d-flex justify-space-between px-5">
                <div>
                    <v-btn color="primary" @click="moveToMatching">해당 게시물로 이동</v-btn>
                </div>
                <!-- 얘네도 이미 처리된 상태면 보이면 안됨 -->
                <div v-show="store.notice.processed_status==0">
                    <v-btn
                    class="text-none"
                    color="#bdd1f7"
                    rounded="m"
                    text="수락"
                    variant="flat"
                    @click="acceptCancel(store.notice)"
                    ></v-btn>
                    <v-btn
                        class="text-none"
                        color="#bdd1f7"
                        rounded="m"
                        text="거절"
                        variant="outlined"
                        @click="refuseCancel(store.notice)"
                    ></v-btn>
                </div>
            </v-card-actions>
        </v-card>
        <v-card class="pa-7" 
        v-else-if="store.notice.type==='수락'">
            <div class="d-flex justify-space-between px-1">
				<v-card-title ><h3>신청 결과</h3></v-card-title>
			</div>
            <v-card-text>
                <div class="mb-4">
                    <b>{{ store.notice.matchingTitle }}</b>에 보낸<br> 동행 신청이 수락되었습니다.
                </div>
            </v-card-text>
            <v-card-actions class="d-flex justify-center px-5">
                <v-btn
                class="text-none"
                color="#bdd1f7"
                rounded="m"
                text="해당 게시물로 이동"
                variant="flat"
                @click="moveToMatching"
                ></v-btn>
                <v-btn
                    class="text-none"
                    color="#bdd1f7"
                    rounded="m"
                    text="닫기"
                    variant="outlined"
                    @click="closeModal"
                ></v-btn>
            </v-card-actions>
        </v-card>
        <v-card class="pa-7" 
        v-else-if="store.notice.type==='거절'">
            <div class="d-flex justify-space-between px-1">
				<v-card-title ><h3>신청 결과</h3></v-card-title>
			</div>
            <v-card-text>
                <div class="mb-4">
                    <b>{{ store.notice.matchingTitle }}</b>에 보낸<br> 동행 신청이 거절되었습니다.
                </div>
            </v-card-text>
            <v-card-actions class="d-flex justify-center px-5">
                <v-btn
                class="text-none"
                color="#bdd1f7"
                rounded="m"
                text="해당 게시물로 이동"
                variant="flat"
                @click="moveToMatching"
                ></v-btn>
                <v-btn
                    class="text-none"
                    color="#bdd1f7"
                    rounded="m"
                    text="닫기"
                    variant="outlined"
                    @click="closeModal"
                ></v-btn>
            </v-card-actions>
        </v-card>
        <v-card class="pa-7" 
        v-else-if="store.notice.type==='탈퇴'">
            <div class="d-flex justify-space-between px-1">
				<v-card-title ><h3>탈퇴 알림</h3></v-card-title>
			</div>
            <v-card-text>
                <div class="mb-4">
                    <b>{{ store.notice.matchingTitle }}</b> 여행에서<br> 탈퇴되었습니다.
                </div>
            </v-card-text>
            <v-card-actions class="d-flex justify-center px-5">
                <v-btn
                class="text-none"
                color="#bdd1f7"
                rounded="m"
                text="해당 게시물로 이동"
                variant="flat"
                @click="moveToMatching"
                ></v-btn>
                <v-btn
                    class="text-none"
                    color="#bdd1f7"
                    rounded="m"
                    text="닫기"
                    variant="outlined"
                    @click="closeModal"
                ></v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

  

<style scoped>
/* div {
    border: 1px solid black;
} */
</style>