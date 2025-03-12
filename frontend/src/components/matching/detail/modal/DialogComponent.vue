<script setup>
import GroupManageComponent from "@/components/matching/detail/modal/GroupManageComponent.vue";
import SubmitJoinComponent from "@/components/matching/detail/modal/SubmitJoinComponent.vue";
import CancelJoinComponent from "@/components/matching/detail/modal/CancelJoinComponent.vue";

// dialogVisible
const props = defineProps({
	dialogVisible: Boolean,
	modalType: String,
});
// dialog 끄기 버튼 클릭 시 부모 컴포넌트에게 전달
const emit = defineEmits(["dialogVisibleEmit"]);

const clickCloseBtn = () => {
	emit("dialogVisibleEmit");
};


</script>

<template>
    <v-dialog v-model="props.dialogVisible" >
        <v-card class="pa-7">
            <div class="d-flex justify-space-between px-1">
				<v-card-title ><h3>{{ modalType }}</h3></v-card-title>
			</div>
			<template v-if="modalType==='그룹 멤버 관리'"><GroupManageComponent @dialog-visible-emit="clickCloseBtn"/> </template>
			<template v-else-if="modalType==='동행 신청'"><SubmitJoinComponent @dialog-visible-emit="clickCloseBtn"/> </template>
			<template v-else-if="modalType==='취소 신청'"><CancelJoinComponent @dialog-visible-emit="clickCloseBtn"/> </template>
        </v-card>
    </v-dialog>
</template>

<style scoped>
.v-dialog {
	width: 50%;
	max-width: 700px;
	min-width: 500px;
}
</style>