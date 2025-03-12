<script setup>
import UserInfoTable from '@/components/users/UserInfoTable.vue'
</script>

<template>
	<v-container>
		<h1>Profile</h1>
		<div class="profile-card mb-10 mt-5">
			<UserInfoTable :edit="false" />
		</div>
	</v-container>
</template>

<style scoped>
.v-container {
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	width : inherit;
	height : inherit;
	margin: auto;
	background-color: white;
	margin-top: 15px;
}
.profile-card {
	width: 70%;
	max-width: 1000px;
	height: auto;
	border: 1px solid rgb(214, 214, 214);
	border-radius: 20px;
	display: flex;
	flex-direction: column;
	align-items: center;
	position: relative;
	background-color: white;
}
</style>