<script setup>
import { useMatchingStore } from "@/stores/matching";
import { useNoticeStore } from "@/stores/notice";
import { useUserStore } from "@/stores/user";
import { computed, onUpdated, ref } from 'vue'
import { RouterLink } from 'vue-router';

const drawer = ref(null)

// 로그아웃
const store = useUserStore()
const noticeStore = useNoticeStore()
const matchingStore = useMatchingStore()

const logout = () => {
  store.logout()
}

const noticeCnt = computed(() => {
	return noticeStore.noticeList.filter(notice => notice.read_status === 0).length;
})

const initMatchingList = () => {
	matchingStore.updateSearchState(false)
	matchingStore.updateShowState(false)
}
</script>

<template>
	<v-navigation-drawer 
		v-model="drawer"
		style="background-color: #176876;"
	>
		<v-list-item nav class="side-bar-title my-3">
			<span>TripGether</span>
			<template v-slot:append>
				<v-btn
				icon="mdi-chevron-left"
				variant="text"
				@click="drawer = !drawer"
				></v-btn>
			</template>
		</v-list-item>

		<v-list density="compact" nav>
			<RouterLink :to="{name: 'planList'}">
				<v-list-item
				value="myPlan"
				><v-icon icon="mdi-notebook" size="35" class="mr-3" color="white"/><span>나의 계획</span></v-list-item>
			</RouterLink>
			<RouterLink :to="{name: 'matchingMain'}">
				<v-list-item @click="initMatchingList"
					value="matching"
				><v-icon icon="mdi-account-group" size="35" class="mr-3" color="white"/><span>동행 찾기</span></v-list-item>
			</RouterLink>
			<RouterLink :to="{name: 'diary'}">
				<v-list-item
					value="diary"
				><v-icon icon="mdi-pencil" size="35" class="mr-3" color="white"/><span>여행 기록</span></v-list-item>
			</RouterLink>
			<RouterLink :to="{name: 'myPage'}">
				<v-list-item
				value="myPage"
				><v-icon icon="mdi-home-account" size="35" class="mr-3" color="white"/><span>마이페이지</span></v-list-item>      
			</RouterLink>
		</v-list>

	</v-navigation-drawer>

	<v-app-bar style="background-color: #8BB4C7;">
		<v-app-bar-nav-icon v-if="!drawer" @click="drawer = !drawer"></v-app-bar-nav-icon>
		<v-app-bar-title v-if="!drawer">TripGether</v-app-bar-title>

		<v-list-item nav class="app-bar-list ml-auto mr-8" v-if="store.loginState">
			<RouterLink :to="{name: 'myPage'}">
				<template #default>
					<v-avatar prepend size="45" class="mr-7">
						<v-img :src="store.user.userProfile"></v-img>
					</v-avatar>
				</template>
			</RouterLink>
	
			<RouterLink :to="{name: 'notice'}">
				<v-badge color="red" class="icon-notice mr-3" v-if="noticeCnt > 0">
					<template v-slot:badge>
						<span>{{ noticeCnt }}</span>
					</template>
					<v-icon icon="mdi-bell" size="25"/>
				</v-badge>

				<template v-else class="icon-notice mr-3">
					<v-icon icon="mdi-bell" size="25"/>
				</template>
			</RouterLink>
	
			<v-icon icon="mdi-logout"
				size="25"
				class="ml-5"
				@click="logout"
			/>
		</v-list-item>
	</v-app-bar>
</template>

<style scoped>
.v-navigation-drawer {
	background-color: #bdd1f7;
	padding-left: 5px;
	font-family: 'SCDream-Medium';
}
.v-navigation-drawer span {
	color: white;
}
.side-bar-title {
	text-align: center;
	color: white;
	font-size: 32px;
	font-family: 'SCDream-ExtraBold';
}
.v-app-bar {
	height: 80px;
	display: flex;
	justify-content: center;
	align-items: center;
	padding-left: 10px;
}
.v-app-bar-title {
	color: black;
	font-size: 32px;
	margin-top: 3px;
	font-family: 'SCDream-ExtraBold';
}
.v-list-item {
	margin-bottom: 5px;
}
.v-img {
	object-fit: cover;
}
</style>
