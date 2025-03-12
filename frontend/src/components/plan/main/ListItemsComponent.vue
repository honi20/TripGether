<script setup>
import { usePlanStore } from '@/stores/plan';
import CardComponent from '@/components/plan/main/CardComponent.vue';
import { onMounted } from 'vue';

const store = usePlanStore()

onMounted(() => {
	store.getPlanList()
})

</script>

<template>
	<v-row>
		<v-col cols="4">
			<v-hover v-slot="{ isHovering, props }">
			<!--RouterLink로 작성폼으로 바로 이동-->
				<RouterLink :to="{ name:'planCreateForm' }" style="width: -webkit-fill-available;">
					<v-card class="new-card mx-7" v-bind="props" :elevation="isHovering ? 24 : 6">
						<v-icon icon="mdi-plus-circle" style="color:gray;" size="50"/>
						<v-card-title>새로운 여행을 떠나볼까요?</v-card-title>
					</v-card>
				</RouterLink>
			</v-hover>
		</v-col>
		<v-col v-for="(plan, index) in store.planList" :key="plan.uuid" :plan="plan" cols="4">
			<CardComponent :plan="plan"  />	
		</v-col>
  	</v-row>
</template> 


<style scoped>
a {
    display: inline-block;
}
/* .v-card {
	border: 1px solid gray;
} */
.new-card {
	height : 400px;
	/* border: 1px solid gray; */
	justify-content: center;
	text-align: center;
    align-content: center;
}
</style>