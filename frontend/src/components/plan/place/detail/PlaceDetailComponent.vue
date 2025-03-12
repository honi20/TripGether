<script setup>
import PlaceDetailContentsComponent from '@/components/plan/place/detail/PlaceDetailContentsComponent.vue';
import PlaceDetailMapComponent from '@/components/plan/place/detail/PlaceDetailMapComponent.vue';
import RelatedPlaceComponent from '@/components/plan/place/detail/RelatedPlaceComponent.vue';
import RelatedVlogComponent from '@/components/plan/place/detail/RelatedVlogComponent.vue';

const props = defineProps({
	dialogVisible: Boolean,
	place: Object
})

const emit = defineEmits(["dialogVisibleEmit"])

const clickCloseBtn = () => {
	emit("dialogVisibleEmit") 
}
</script>


<template>
	<v-dialog v-model="props.dialogVisible" >
		<v-card class="pa-7" style="background-color: #C3D3C6">
			<div class="d-flex justify-space-between">
				<v-btn flat
					icon="mdi-chevron-left" 
					@click="clickCloseBtn"
				></v-btn>
			</div>
			
			<v-card-title class="ma-0"><h3 >{{ place.place_name }}</h3></v-card-title>
			<v-card-text>
				<v-row class="place-info mb-3">
					<v-col class="detail-img pa-0" md="12" lg="4">
						<PlaceDetailMapComponent :place="place" />
					</v-col>
					<v-col cols="auto" md="12" lg="8" class="rounded-lg border-md pa-2 bg-white">
						<div >
							<v-table>
								<tbody>
									<tr>
										<td><b>장소 이름</b></td>
										<td>{{ place.place_name }}</td>
									</tr>
									<tr>
										<td><b>상세 페이지 주소</b></td>
										<td>{{ place.place_url }}</td>
									</tr>
									<tr>
										<td><b>카테고리</b></td>
										<td>{{ place.category_name }}</td>
									</tr>
									<tr>
										<td><b>주소</b></td>
										<td>{{ place.address_name }}</td>
									</tr>
									<tr>
										<td><b>도로명 주소</b></td>
										<td>{{ place.road_address_name }}</td>
									</tr>
									<tr>
										<td><b>번호</b></td>
										<td>{{ place.phone }}</td>
									</tr>
								</tbody>
							</v-table>
						</div>
					</v-col>
				</v-row>

				<v-row class="pt-3 px-4 pb-2 my-3 rounded-lg bg-white">				
					<h3>V-log</h3>
					<RelatedVlogComponent class="ml-1"/>
				</v-row>

				<v-row class="pt-3 px-4 pb-1 my-3 bg-white rounded-lg">
					<h3>여행기록</h3>
					<PlaceDetailContentsComponent :placeName="place.place_name" />
				</v-row>

				<v-row class="pt-3 px-4 pb-1 my-3 bg-white rounded-lg">
					<h3>연관 장소</h3>
					<RelatedPlaceComponent class="ml-1" @place-click="clickCloseBtn"/>
				</v-row>
			</v-card-text>
		</v-card>
	</v-dialog>
</template>

<style scoped>
.v-dialog {
	width: 70%;
	max-width: 1200px;
	min-width: 600px;
}
/* .v-row {
	border: 1px solid black;
	width: 100%;
}
.v-col {
	border: 1px solid black;
} */
.v-card-text {
	padding: none;
	margin: 10px;
}
.detail-img {
	background-color: #e4e4e4;
}
</style>