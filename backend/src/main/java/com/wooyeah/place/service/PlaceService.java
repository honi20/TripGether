package com.wooyeah.place.service;

import java.util.List;
import java.util.Map;

import com.google.api.services.youtube.model.SearchListResponse;
import com.wooyeah.place.dto.CompletionRequestDto;

public interface PlaceService {

	String getPlaceList(String searchInput) throws Exception;

	SearchListResponse getVlogList(String keyword) throws Exception;

//	List<Map<String, Object>> modelList();
//
//	Map<String, Object> isValidModel(String modelName);
//
//	Map<String, Object> prompt(CompletionRequestDto completionRequestDto);
}
