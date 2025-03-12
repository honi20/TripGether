<script setup>
import { ref } from 'vue';
import { usePlaceStore } from '@/stores/place';

const store = usePlaceStore();

const slider = ref(null);
const isDown = ref(false);
const startX = ref(0);
const scrollLeft = ref(0);
let isDragged = false;

const redirectToUrl = (videoId) => {
    window.open(`https://www.youtube.com/watch?v=${videoId}`);
};

const preventClick = (e) => {
    e.preventDefault();
    e.stopImmediatePropagation();
};

const handleMouseDown = (e) => {
    if (slider.value) {
        isDown.value = true;
        slider.value.classList.add('active');
        startX.value = e.pageX - slider.value.offsetLeft;
        scrollLeft.value = slider.value.scrollLeft;
    }
};

const handleMouseLeave = () => {
    if (slider.value) {
        isDown.value = false;
        slider.value.classList.remove('active');
    }
};

const handleMouseUp = (e) => {
    if (slider.value) {
        isDown.value = false;
        const elements = document.getElementsByClassName('item');
        if (isDragged) {
            for (let i = 0; i < elements.length; i++) {
                elements[i].addEventListener('click', preventClick);
            }
        } else {
            for (let i = 0; i < elements.length; i++) {
                elements[i].removeEventListener('click', preventClick);
            }
        }
        slider.value.classList.remove('active');
        isDragged = false;
    }
};

const handleMouseMove = (e) => {
    if (!isDown.value || !slider.value) return;
    isDragged = true;
    e.preventDefault();
    const x = e.pageX - slider.value.offsetLeft;
    const walk = (x - startX.value) * 2;
    slider.value.scrollLeft = scrollLeft.value - walk;
};
</script>

<template>
    <div class="wrapper" ref="slider"
        @mousedown="handleMouseDown"
        @mouseleave="handleMouseLeave"
        @mouseup="handleMouseUp"
        @mousemove="handleMouseMove">
        <v-card
            class="new-card item py-2"
            v-for="vlog in store.relatedVlogList"
            :key="vlog.id.videoId"
            @click="redirectToUrl(vlog.id.videoId)"
        >
            <v-img :width="250" aspect-ratio="16/9" :src="vlog.snippet.thumbnails.default.url"></v-img>
            <v-card-text nowrap><b>{{ vlog.snippet.title }}</b></v-card-text>
        </v-card>
    </div>
</template>

<style scoped>
.wrapper {
    display: flex;
    overflow-x: hidden;
    white-space: nowrap;
    cursor: grab;
    width: 100%;
    box-sizing: border-box;
}

.wrapper.active {
    cursor: grabbing;
}

.v-card {
    display: inline-block;
    width: 300px;
    text-align: center;
	margin: 10px 25px 5px 0px;
    flex-shrink: 0;
}

.v-img {
    margin: auto;
}
.v-card-text {
    white-space: normal;
    overflow-wrap: break-word;
    word-wrap: break-word;
    text-align: left;
    margin: 10px;
}
</style>
