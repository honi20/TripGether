<script setup>
import { ref, computed } from 'vue';
import { useRoute } from 'vue-router'
const route = useRoute()
import { useMatchingStore } from "@/stores/matching";
import { useUserStore }from "@/stores/user";

import DialogComponent from '@/components/matching/detail/modal/DialogComponent.vue';

const userStore = useUserStore()
const matchingStore = useMatchingStore()

const themeNames = [
	{ key: 'forEnergetic', label: '알찬' },
	{ key: 'forLeisurely', label: '여유로운' },
	{ key: 'forNatural', label: '자연' },
	{ key: 'forUrban', label: '도시' },
	{ key: 'forHealing', label: '힐링' },
	{ key: 'forShopping', label: '쇼핑' },
	{ key: 'forMorning', label: '아침형' },
	{ key: 'forNightOwl', label: '올빼미형' }
];

const isJoined = computed(() => { 
    return matchingStore.joinedTripList.some(trip => trip.matchingUuid === route.params.uuid)
})

const dialogVisible = ref(false);
const modalType = ref('');

// 모달창 열기
const openDialog = (type) => {
    dialogVisible.value = true
    modalType.value = type
}
// 모달창 닫기
const closeDialog = () => {
	dialogVisible.value = false
}
// dialog visible 변수
const dialogVisibleEmit = () => {
  dialogVisible.value = false;
}

// 버튼 클릭시 실행
const openGroupManage = () => {
    openDialog('그룹 멤버 관리')
}
const deleteMatching = () => { 
    if (confirm('이 게시물을 삭제하시겠습니까? 멤버들에게 삭제 알림이 전송되며 관련 기록이 삭제됩니다.')) { 
        // alert("삭제 완료")
        matchingStore.deleteMatchingPost(route.params.uuid)
    }
}

const submitJoin = () => { 
    openDialog('동행 신청')
}

const cancelJoin = () => { 
    openDialog('취소 신청')
}

</script>

<template>
        <div class="d-flex justify-space-between">
            <b style="font-size: 35px;">{{ matchingStore.matchingDetail.title }}</b>
            <!-- 내가 작성한 글일때 -->
            <div v-if="userStore.user.userId===matchingStore.matchingDetail.writerId">
                <v-btn 
                class="mr-3"
                color="#176876" variant="flat" height="50px" @click="openGroupManage">
					그룹 관리
				</v-btn>
                <v-btn
                color="#176876" variant="outlined" height="50px" @click="deleteMatching">
					삭제
				</v-btn>
            </div>
            <!-- 내가 작성 X && 가입X -->
            <div v-else-if="!isJoined">
                <v-btn
                color="#176876" variant="flat" height="50px" @click="submitJoin">
					동행 신청
				</v-btn>
            </div>
            <!-- 내가 작성 X && 가입 O -->
            <div v-else-if="isJoined">
                <v-btn
                color="#176876" variant="flat" height="50px" @click="cancelJoin">
				    동행 취소 신청
				</v-btn>
            </div>
        </div>
        <v-container class="mt-4">
            <v-row class="my-4">
                <v-col cols="2"><b>작성자</b></v-col>
                <v-col cols="10" class="pl-0">{{ matchingStore.matchingDetail.writerName }} ({{ matchingStore.matchingDetail.writerGender }}, {{ matchingStore.matchingDetail.writerBirth }}년생)</v-col>
            </v-row>
            <v-row class="my-4">
            <v-col cols="2"><b>여행 일정</b></v-col>
            <v-col cols="10" class="pl-0">
                {{ matchingStore.matchingDetail.startDate }} ~ {{ matchingStore.matchingDetail.endDate }}
            </v-col>
        </v-row>
        <v-row class="my-4">
            <v-col cols="2"><b>테마</b></v-col>
            <v-col cols="10" class="pa-0">	
                <!-- <v-chip-group
                selected-class="text-#176876"
                multiple
                > -->
                <v-chip
                    v-for="theme in themeNames"
                    :key="theme.key"
                    :text="`# ${theme.label}`"
                    :model-value="matchingStore.matchingDetailTheme[theme.key]"
                    color="#176876"
                    class="mt-1 mr-2"
                    ></v-chip>
                <!-- </v-chip-group>	 -->
            </v-col>
        </v-row>
        <v-row class="mt-4">
            <v-col cols="4">
                <b>지역</b> 
                <input type="text" :value="matchingStore.matchingDetail.region" 
                style="margin-left:20px; padding: 3px 0px 4px 10px; width: 80%;"
                readonly>
            </v-col>
            <v-col cols="4"><b>비용</b>
                <input type="text" :value="`${Number(matchingStore.matchingDetail.totalBudget).toLocaleString()}원`"
                style="margin-left:20px; padding: 3px 0px 4px 10px; width: 80%;" readonly/>
            </v-col>
            <v-col cols="4">
                <b class="mr-5">인원</b>
                {{ matchingStore.matchingDetail.currentPersonCnt }} / {{ matchingStore.matchingDetail.recruitPersonCnt }} 명
            </v-col>
        </v-row>

    </v-container>

    <DialogComponent :dialog-visible="dialogVisible" :modalType="modalType" @dialog-visible-emit="closeDialog" />
</template>

<style scoped>
/* .v-container{
    border: 1px solid black;
}
.v-row {
    border: 1px solid black;
}
.v-col {
    border: 1px solid black;
}
div{
    border: 1px solid black;
} */
</style>