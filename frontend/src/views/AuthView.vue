<script setup>
import AuthComponent from '@/components/users/AuthComponent.vue';

</script>

<template>
    <v-main>
        <AuthComponent />
    </v-main>
</template>

<style scoped>
.v-main {
	width: inherit;
	height: 100%;

	/* COLORS */
	--white: #e9e9e9;
	--gray: #333;
	--blue: #0367a6;
	--lightblue: #008997;

	/* RADII */
	--button-radius: 0.7rem;

	/* SIZES */
	--max-width: 65%;
	--max-height: 90%;

	font-size: 16px;
	font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
		Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;

	display: flex;
	justify-content: center;
	align-items: center;
	background-color: var(--white);
	background: url("/public/img/backgroundImg.jpg");
	background-attachment: fixed;
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
	height: 100vh;
	margin: 0;
}

</style>