<script setup>
import { ref } from 'vue';
import { usePlaceStore } from '@/stores/place';

defineProps({
  targetInfo: String
})

const store = usePlaceStore()

const slider = ref(null);
const isDown = ref(false);
const startX = ref(0);
const scrollLeft = ref(0);
let isDragged = false;

const searchRelatedPlace = (relatedPlace) => {
  console.log("장소 찾기!")
  // 연관 장소를 검색
  store.getPlaceList(relatedPlace)
  // 카카오맵 검색 창으로 이동
  emit("place-click");
}

const emit = defineEmits(["place-click"])

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
    <div class="wrapper my-2" ref="slider"
        @mousedown="handleMouseDown"
        @mouseleave="handleMouseLeave"
        @mouseup="handleMouseUp"
        @mousemove="handleMouseMove">
        
        <template v-for="relatedPlace in store.relatedPlaceList" :key="relatedPlace">
          
          <div class="d-inline-flex item"
              @click="searchRelatedPlace(relatedPlace)"
            >
            <v-chip 
            color="#176876"
            variant="tonal"
            @click="searchRelatedPlace(relatedPlace)"
            class="mt-1 mr-3 px-3">
            # {{ relatedPlace }} </v-chip>
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
    display: inline-block;
    /* v-chip으로 바꾸면서 필요없어짐 
    align-items: center;
    text-align: center;
    width: auto;
    height: 30px;
    min-width: 20vh;
    margin: 10px; */
}

.active {
    cursor: grabbing;
}
</style>
