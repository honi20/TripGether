<script setup>
import { ref } from 'vue'
import { useUserStore } from "@/stores/user";

const store = useUserStore()
const userInfo = ref({
	userId: '',
	userPassword: ''
})

const loginUser = async () => {
	await store.login(userInfo.value)
}
</script>

<template>
	<div class="container__form container--signin">
		<form action="#" class="form" id="form2" @submit.prevent>
			<h2 class="form__title">Sign In</h2>
			<input type="text" placeholder="ID" class="input" v-model="userInfo.userId" />
			<input type="password" placeholder="Password" class="input" v-model="userInfo.userPassword" />
			<button class="btn" @click="loginUser">Sign In</button>
		</form>
	</div>

</template>

<style scoped>
#form2 {
	width: -webkit-fill-available;
    margin-left: 15%;
    margin-right: 15%;
}
</style>