<script setup>
import { useUserStore } from "@/stores/user";
import ListItemsComponent from "@/components/plan/main/ListItemsComponent.vue"

const userStore = useUserStore()
</script>

<template>
    <!-- 프로필 사진, {{사용자}}님의 여행  -->
    <div class="d-flex flex-no-wrap justify-items-center align-center">
		<v-avatar class="mt-7 ma-10" size="125">
			<v-img :src="userStore.user.userProfile"></v-img>
		</v-avatar>

        <v-card-title class="text-h5">
			{{ userStore.user.userName }}님의 여행
		</v-card-title>
	</div>
    <ListItemsComponent class="my-10"/>
</template>

<style scoped>

</style>