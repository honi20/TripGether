<script setup>
import { useDiaryStore } from '@/stores/diary';
import { onMounted, ref } from 'vue';

const diaryStore = useDiaryStore()

const slider = ref(null);
const isDown = ref(false);
const startX = ref(0);
const scrollLeft = ref(0);
let isDragged = false;

const props = defineProps({
	placeName: String
})

onMounted(() => {
	diaryStore.getRelatedDiary(props.placeName)
	console.log(diaryStore.relatedDiaryList)
})

const preventClick = (e) => {
    e.preventDefault();
    e.stopImmediatePropagation();
};

const handleMouseDown = (e) => {
    isDown.value = true;
    slider.value.classList.add("active");
    startX.value = e.pageX - slider.value.offsetLeft;
    scrollLeft.value = slider.value.scrollLeft;
};

const handleMouseLeave = () => {
    isDown.value = false;
    slider.value.classList.remove("active");
};

const handleMouseUp = (e) => {
  isDown.value = false;
  const elements = document.getElementsByClassName("item");
  if (isDragged) {
    for (let i = 0; i < elements.length; i++) {
      elements[i].addEventListener("click", preventClick);
    }
  } else {
    for (let i = 0; i < elements.length; i++) {
      elements[i].removeEventListener("click", preventClick);
    }
  }
  slider.value.classList.remove("active");
  isDragged = false;
};

const handleMouseMove = (e) => {
  if (!isDown.value) return;
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
        
        <template v-for="diary in diaryStore.relatedDiaryList" :key="diary.diaryUuid">
            <div class="item">
				<v-img :src="diary.images[0].split('public')[1]" width="100%" height="100%"></v-img>
			</div>
        </template>
    </div>
</template>

<style scoped>
.wrapper {
    position: relative;
    display: flex;
    min-width: 100%;
    overflow: hidden;
}

.item {
    width: auto;
    height: 20vh;
    min-width: 20vw;
    margin: 10px;
    /* background-color: #E3F2FD; */
}

.active {
    cursor: grabbing;
}
</style>
