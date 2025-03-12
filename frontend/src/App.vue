<script setup>
import { RouterLink, RouterView } from 'vue-router'
import Menubar from '@/views/includes/Menubar.vue'
import { onMounted, onUpdated } from 'vue';
import { useUserStore } from './stores/user';

const userStore = useUserStore()

onMounted(() => {
  userStore.isLogined()
  userStore.myPage()
})
</script>

<template>
  <v-layout>
    <Menubar />
    <RouterView />
  </v-layout>
</template>

<style scoped>
.v-layout {
	width : 100%;
	height : 100%;
}
</style>

<style>
a {
  text-decoration: none;
  color: black;
}
</style>