import MainView from '@/views/MainView.vue'
import NoticeView from '@/views/NoticeView.vue'
import PlanView from '@/views/PlanView.vue'
import DiaryView from '@/views/DiaryView.vue'

import UserView from '@/views/UserView.vue'
import AuthView from '@/views/AuthView.vue'
import LoginComponent from '@/components/users/LoginComponent.vue'
import SignUpComponent from '@/components/users/SignUpComponent.vue'
import MyPageComponent from '@/components/users/MyPageComponent.vue'
import MyPageUpdateComponent from '@/components/users/MyPageUpdateComponent.vue'
import PlanListComponent from '@/components/plan/main/ListComponent.vue'
import PlanCreateForm from '@/components/plan/regist/CreateForm.vue'

import { createRouter, createWebHistory } from 'vue-router'
import { useCookies } from 'vue3-cookies'

import DetailComponent from '@/components/plan/detail/DetailComponent.vue'
import MatchingView from '@/views/MatchingView.vue'
import MatchingMainComponent from '@/components/matching/main/MatchingMainComponent.vue'
import MatchingCreateForm from '@/components/matching/regist/CreateComponent.vue'
import MatchingDetailComponent from '@/components/matching/detail/DetailComponent.vue'
import AuthComponent from '@/components/users/AuthComponent.vue'

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
			path: '/',
			name: 'main',
			component: MainView,
			meta: { requiresAuth: true }
		},
		{
			path: '/notice',
			name: 'notice',
			component: NoticeView,
			meta: { requiresAuth: true }
		},
		{
			path: '/auth',
			name: 'auth',
			component: AuthView
		},
		{
			path: '/user',
			name: 'user',
			component: UserView,
			meta: { requiresAuth: true },
			children: [
				{
					path: 'myPage',
					name: 'myPage',
					component: MyPageComponent,
				},
				{
					path: 'update',
					name: 'myPageUpdate',
					component: MyPageUpdateComponent,
				}
			]
		},
		{
			path: '/plan',
			name: 'plan',
			component: PlanView,
			meta: { requiresAuth: true },
			children: [
				{
					path: '',
					name: 'planList',
					component: PlanListComponent,
				},
				{
					path: 'regist',
					name: 'planCreateForm',
					component: PlanCreateForm,
				},
				{
					path: 'detail/:uuid',
					name: 'planDetail',
					component: DetailComponent
				}
			]
		},
		{
			path: '/matching',
			name: 'matching',
			component: MatchingView,
			meta: { requiresAuth: true },
			children: [
				{
					path: '',
					name: 'matchingMain',
					component: MatchingMainComponent,
				},
				{
					path: 'regist/:plan_uuid',
					name: 'matchingCreateForm',
					component: MatchingCreateForm,
					meta: { requiresAuth: true },
				},
				{
					path: 'detail/:uuid',
					name: 'matchingDetail',
					component: MatchingDetailComponent,
					meta: { requiresAuth: true },
				}
			]
		},
		{
			path: '/diary',
			name: 'diary',
			component: DiaryView,
			meta: { requiresAuth: true },
		}
	]
})

const {cookies} = useCookies();

router.beforeEach(async (to, from, next) => {
	// 로그인이 필요한 페이지인지 확인
	if (to.meta.requiresAuth) {
		const id = cookies.get('id')

		if (!id)
			next('/auth')
		else
			next()
	}
	else {
		// 로그인이 필요하지 않은 페이지는 그냥 진행
		next()
	}
})

export default router;
