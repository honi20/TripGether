<script setup>
import { ref, computed, inject } from 'vue'
import router from '@/router'

const emit = defineEmits(['signUpComplete'])

const userInfo = ref({
	userId: '',
	userName: '',
	userPassword: '',
	userGender: '',
	userBirthday: '',
})

const retryUserPassword = ref('')
const userProfile = ref(null)

const checkIdMessage = ref('')
const checkIdMessageColor = ref('')

const axios = inject('axios')

// 아이디 중복 여부 확인
const checkUserId = () => {
	axios.get(`/user/checkId/${userInfo.value.userId}`, { withCredentials: true })
		// 중복된 아이디 없음
		.then((response) => {
			checkIdMessage.value = '사용 가능한 아이디입니다.'
			checkIdMessageColor.value = 'text-green'
		})
		// 중복된 아이디 존재
		.catch((error) => {
			checkIdMessage.value = '사용 불가능한 아이디입니다.'
			checkIdMessageColor.value = 'text-red'
		})
}

// 회원가입
const submitForm = async () => {
	// 회원가입 불가능
	if (!isSamePassword.value) {
		alert('비밀번호가 일치하지 않습니다.')
	}
	else if (checkIdMessageColor.value === 'text-red' || retryUserPassword.value === '') {
		alert('중복된 아이디입니다.')
	}
	else if (userInfo.value.userId === '') {
		alert('아이디를 입력하십시오.')
	}
	else if (userInfo.value.userName === '') {
		alert('이름을 입력하십시오.')
	}
	else if (userInfo.value.userPassword === '') {
		alert('비밀번호를 입력하십시오.')
	}
	else if (userInfo.value.userGender === '') {
		alert('성별을 입력하십시오.')
	}
	else if (userInfo.value.userBirthday === '') {
		alert('생년월일을 입력하십시오.')
	}
	else {
		const formData = new FormData()
		formData.append("data", new Blob([JSON.stringify(userInfo.value)], { type: 'application/json' }))
		// 프로필 파일 존재 시
		formData.append("file", userProfile.value)

		await axios.post('/user/signUp', formData, {
			headers: { 'Content-Type': 'multipart/form-data' },
		})
		.then(() => {
			alert('회원가입이 완료되었습니다.')

			// 입력값 초기화
			userInfo.value = {}
			userInfo.value.userBirthday = ''
			retryUserPassword.value = ''
			userProfile.value = null
			checkIdMessage.value = ''
			checkIdMessageColor.value = ''
	
			emit('signUpComplete')
			router.push({ name: 'auth' })
		})
	}
}

const handleFileChange = (event) => {
	userProfile.value = event.target.files[0]
}

const isSamePassword = computed(() => {
	if (retryUserPassword.value === '')
		return true;

	if (userInfo.value.userPassword !== retryUserPassword.value) {
		return false;
	}
	return true;
})

</script>

<template>
	<div class="container__form container--signup">
		<form action="#" class="form" id="form1" @submit.prevent>
			<h2 class="form__title">Sign Up</h2>

			<!-- ID (Chcek) -->
			<input type="text" placeholder="ID" class="input" v-model="userInfo.userId" @keyup="checkUserId" />
			<template v-if="checkIdMessage !== ''">
				<span :class="checkIdMessageColor" style="font-size: 14px;">{{ checkIdMessage }}</span>
			</template>

			<input type="text" placeholder="Name" class="input" v-model="userInfo.userName" />
			<input type="password" placeholder="Password" class="input" v-model="userInfo.userPassword" />
			<input type="password" placeholder="PasswordCheck" class="input" v-model="retryUserPassword" />
			<template v-if="isSamePassword === false">
				<span class="text-red">비밀번호가 일치하지 않습니다.</span>
			</template>

			<!-- Gender -->
			<select v-model="userInfo.userGender" class="input">
				<option value="">Gender</option>
				<option value="남성">Male</option>
				<option value="여성">Female</option>
			</select>

			<input type="date" placeholder="Birth" class="input" v-model="userInfo.userBirthday" />
			<input type="file" placeholder="Profile" class="input" id="fileInput" @change="handleFileChange" />
			<button class="btn" @click="submitForm">Sign Up</button>
		</form>
	</div>

</template>

<style scoped>
#form1 {
	width: -webkit-fill-available;
    margin-left: 15%;
    margin-right: 15%;
}
#fileInput {
	height: max-content;
}
</style>