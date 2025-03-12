<script setup>
import { useRouter } from 'vue-router';
import { usePlanStore } from '@/stores/plan';

const props = defineProps({
	plan: Object
})

const router = useRouter()

const store = usePlanStore()

const goToMatching = () => {
	// alert("동행매칭 페이지로 이동! (계획의 uuid = " + props.plan.uuid + ")");
	router.push({ name: 'matchingCreateForm', params: { plan_uuid: props.plan.uuid } })
}

const goToSharing = () => {
	alert("계획공유 페이지로 이동! (계획의 uuid = " + props.plan.uuid + ")");
}

const editPlan = () => {
	alert("계획 수정");
}
const deletePlan = () => {
	store.deletePlan(props.plan.uuid)
}
</script>

<template>
	<v-hover v-slot="{ isHovering, props }">
		<v-card class="card justify-center opacity-80 mx-7" color="surface-variant" :image="plan.planImage"
			v-bind="props" :elevation="isHovering ? 24 : 6">
			<v-card-item class="justify-end">
				<v-menu location="bottom">
					<template v-slot:activator="{ props }">
						<v-btn density="compact" icon="mdi-dots-vertical" size="30" color="white" base-color="#564734"
							v-bind="props"></v-btn>
					</template>
					<v-list>
						<v-list-item v-if="!plan.outOfDate" @click="goToMatching">
							<v-list-item-title>모집하기</v-list-item-title>
						</v-list-item>
						<v-list-item @click="deletePlan">
							<v-list-item-title>삭제하기</v-list-item-title>
						</v-list-item>
					</v-list>
				</v-menu>
			</v-card-item>
			<RouterLink :to="{ name: 'planDetail', params: { uuid: plan.uuid } }" class="router-link"
				style="height: 100%; text-decoration: none;">
				<v-card-text>
					<span class="font-weight-black text-h5 mt-6 mb-3">{{ plan.title }}</span>
					<span class="text-subtitle-1">{{ plan.startDate }} ~ {{ plan.endDate }}</span>
				</v-card-text>
			</RouterLink>
		</v-card>
	</v-hover>
</template>

<style scoped>
.v-card {
	background-color: transparent;
	height: 400px;
	/* gap: 20px; */
	/* border: 1px solid rgb(77, 121, 82); */
}

.btn {
	text-align: end;
}

.v-card-text {
	background-color: #ffffff78;
	/* border-top: 1px solid black; */
	color: black;
	margin-top: 225px;
	display: flex;
	flex-direction: column;
	margin-bottom: 0px;
	padding-top: 0px;
	padding-bottom: 30px;
	/* height: 100%; */
}

.v-list-item--highlighted {
	background-color: lightgray !important;
}
</style>