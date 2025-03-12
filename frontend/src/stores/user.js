import { defineStore } from 'pinia'
import router from '@/router'
import { ref, inject, computed } from 'vue'
import { useCookies } from 'vue3-cookies'
import { useNoticeStore } from './notice'

export const useUserStore = defineStore('user', () => {

	const axios = inject('axios')
	const { cookies } = useCookies()
	const loginState = ref(false)
	const noticeStore = useNoticeStore()

	const isLogined = () => {
		if (loginState.value)
			return true

		// 새로고침 같은 이유로 pinia에는 없지만, 쿠키에 로그인 정보가 있는 경우
		if (cookies.get("id") != null) {
			loginState.value = true
			return true
		}

		return false
	}

	// 로그인
	const login = async (userInfo) => {
		await axios.post('/auth/login', userInfo, {withCredentials: true})
		// 로그인 성공
		.then((response) => {
			axios.defaults.headers.common['Authorization'] = response.headers.authorization
			loginState.value = true
			cookies.set("id", Date.now())

			myPage()
			noticeStore.getNoticeList()
			router.push({name: 'planList'})
		})
		// 로그인 실패
		.catch((error) => {
			// 사용자 찾을 수 없음
			if (error.response.data.httpStatus === 400) {
				alert('아이디 또는 비밀번호가 올바르지 않습니다.')
			}
			router.push({name: 'auth'})
		}) 
	}

	// 로그아웃
	const logout = () => {
		axios.delete("/auth/logout")

		.then((response) => {
			axios.defaults.headers.common['Authorization'] = null
			loginState.value = false;
			cookies.remove("id")
			router.push({name: 'auth'})
		})
		.catch((error) => {
			console.log(error)
		})
	}

	// 마이페이지
	const user = ref({
		"userId" : ''
	})

	const myPage = () => {
		axios.get('/user/myPage')
		.then((response) => {
			user.value = response.data
			user.value.userBirthday = user.value.userBirthday.split('T')[0]
			if (user.value.userProfile !== null) {
				user.value.userProfile = user.value.userProfile.split('public')[1]
			}
			else {
				user.value.userProfile = "/img/noProfile.png"
			}
			console.log(user.value)
		})
		.catch((error) => {
			console.log(error)
		})
	}

	return {
		loginState, isLogined, login, user, myPage, logout
	}
})