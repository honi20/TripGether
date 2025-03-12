<script setup>
import { useUserStore } from "@/stores/user";
import { ref, onMounted, inject, computed } from "vue";
import {useRouter} from 'vue-router'

const router = useRouter()
const store = useUserStore()
const axios = inject('axios')

const updateInfo = ref({})
const inputPassword = ref('')
const userNewPassword = ref('')
const retryUserNewPassword = ref('')
const updateProfile = ref(null)

onMounted(() => {
	store.myPage();
	updateInfo.value = {
		userName: store.user.userName,
		userPassword: store.user.userPassword
	}
	inputPassword.value = store.user.userPassword
	userNewPassword.value = store.user.userPassword
	retryUserNewPassword.value = store.user.userPassword
})

// 수정인지 여부 확인
const props = defineProps({
	edit: Boolean
})

// ==== Parent : MyPageComponent ====
// 수정 페이지로 이동
const goToUpdate = () => {
	router.push({name: 'myPageUpdate'})
}

// ==== Parent : MyPageUpdateComponent ====
// 수정 완료
const updateUser = async () => {
	// console.log(updateInfo.value)
	if (updateInfo.value.name === '') {
		alert('이름을 입력하십시오.')
	}
	else if (inputPassword.value !== updateInfo.value.userPassword) {
		alert('기존 비밀번호가 올바르지 않습니다.')
	}
	else if (!isSamePassword.value) {
		alert('비밀번호 확인 값이 입력값과 일치하지 않습니다.')
	}
	else {
		updateInfo.value.userPassword = userNewPassword.value

		const formData = new FormData()
		formData.append("data", new Blob([JSON.stringify(updateInfo.value)], {type: 'application/json'}))
		formData.append("file", updateProfile.value)

		await axios.put('/user/update', formData, {
			headers: {'Content-Type': 'multipart/form-data'},
        })
		.then(() => {
			store.myPage()
			router.push({name: 'myPage'})
		})
	}
}

// 수정 취소
const cancelUpdate = () => {
	router.push({name: 'myPage'})
}

// 파일 반응형
const handleFileChange = (event) => {
	updateProfile.value = event.target.files[0]
}

const isSamePassword = computed(() => {
	if (retryUserNewPassword.value === '' || userNewPassword.value === '')
		return false;

    if(userNewPassword.value !== retryUserNewPassword.value) {
        return false;
    }
	return true;
})

</script>


<template>
	<v-avatar class="mt-7" size="150">
		<v-img v-show="!props.edit" :src="store.user.userProfile"></v-img>
		<input v-show="props.edit" type="file" @change="handleFileChange">
	</v-avatar>
	
	<v-table class="my-6">
		<tbody>
			<tr>
				<td>아이디</td>
				<td>{{store.user.userId}}</td>
			</tr>
			<tr>
				<td>이름</td>
				<td>{{store.user.userName}}</td>
			</tr>
			<tr v-if="props.edit">
				<td>기존 비밀번호</td>
				<td><input type="password" v-model="inputPassword"/></td>
			</tr>
			<tr v-if="props.edit">
				<td>새 비밀번호</td>
				<td><input type="password" v-model="userNewPassword"/></td>
			</tr>
			<tr v-if="props.edit">
				<td>새 비밀번호 확인</td>
				<td><input type="password" v-model="retryUserNewPassword"/></td>
			</tr>
			<tr>
				<td>성별</td>
				<td>{{store.user.userGender}}</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td>{{store.user.userBirthday}}</td>
			</tr>
		</tbody>
	
	</v-table>

	<!-- MyPageComponent인 경우 -->
	<div class="mb-7" v-if="!props.edit">
		<v-btn flat color="#2e2eff" class="mr-3" @click="goToUpdate">수정</v-btn>
	</div>

	<!-- MyPageUpdateComponent인 경우 -->
	<div class="mb-7" v-if="props.edit">
		<v-btn flat color="#2e2eff" class="mr-3" @click="updateUser">수정</v-btn>
		<v-btn flat color="#d8d8d8" class="ml-3" @click="cancelUpdate">취소</v-btn>
	</div>
</template>

<style scoped>
.v-avatar {
	border: 1px solid black;
}
.v-img {
	object-fit: cover;
}
.v-table {
	width: inherit;
	height: inherit;
	padding: 20px;
	border: 1px solid #bdd1f7;
}
tbody { 
	font-size: 17px;
}
tr {
	text-align: center;
}
tbody input {
	background-color: rgb(236, 236, 236);
	padding-left: 10px;
	text-align: center;
}
</style>