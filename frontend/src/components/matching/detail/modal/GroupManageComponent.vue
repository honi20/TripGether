<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useMatchingStore } from "@/stores/matching";
import { useNoticeStore } from "@/stores/notice";
const matchingStore = useMatchingStore()
const noticeStore = useNoticeStore()

onMounted(() => {
    matchingStore.getGroupMembers(route.params.uuid)
    // matchingStore.getGroupMembers(matchingStore.matchingDetail.matchingUuid)
})

const emit = defineEmits(["dialogVisibleEmit"]);

const pushCancel = () => { 
    emit("dialogVisibleEmit");
}

const noticeContent = ref({
    receiverId: '',
    type: '',
    title: ''
})

const route = useRoute()

const dismiss = (member) => {
    alert(`${member.userName}님을 현재 그룹에서 내보내시겠습니까?`)
    // 그룹에서 멤버 빼기
    matchingStore.deleteFromGroup(matchingStore.matchingDetail.matchingUuid, member.userId)
    // 멤버에게 알림 전송
    noticeContent.value.receiverId = `${member.userId}`
    noticeContent.value.type = '탈퇴'
    noticeContent.value.title = `${matchingStore.matchingDetail.title} 여행에서 탈퇴되었습니다.`;
    noticeStore.sendReturn(matchingStore.matchingDetail.matchingUuid, noticeContent.value)
}
</script>

<template>
    <v-card-text>
        <h4>현재 그룹 멤버</h4>
        <v-table class="mt-3">
        <tbody>
        <tr>
            <td><b>{{ matchingStore.matchingDetail.writerName }}</b></td>
            <td>방장</td>
        </tr>
        <template
        v-for="member in matchingStore.groupMemberList"
        :key="member.userId">
            <tr
            v-if="member.userId !== matchingStore.matchingDetail.writerId "
            >
                <td><b>{{ member.userName }}</b></td>
                <td>
                    <v-btn
                    color="red-lighten-4"
                    variant="flat"
                    text="내보내기"
                    @click="dismiss(member)"></v-btn>
                </td>
            </tr>
        </template>        
        </tbody>
  </v-table>
    </v-card-text>

    <v-card-actions class="mb-2 d-flex justify-center">
        <v-btn
        class="text-none"
        color="#8BB4C7"
        rounded="m"
        text="닫기"
        variant="outlined"
        @click="pushCancel"
        ></v-btn>
    </v-card-actions>
</template>

<style scoped>
.v-table {
    border: 1px solid gray;
}

</style>