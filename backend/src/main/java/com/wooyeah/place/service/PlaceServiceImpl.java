package com.wooyeah.place.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.wooyeah.place.config.WebClientConfig;
import com.wooyeah.place.dto.CompletionRequestDto;

import io.jsonwebtoken.lang.Arrays;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Value("${kakao-api.secretkey}")
    private String secretKey;
    
    @Value("${youtube-api.secretkey}")
    private String youtubeSecretKey;
    
//    @Value("${openai.model}")
//    private String model;

    private final WebClient.Builder webClientBuilder;
//    private final WebClientConfig chatGPTConfig;

    public PlaceServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public String getPlaceList(String searchInput) throws Exception {
        
        String url = "https://dapi.kakao.com/v2/local/search/keyword.JSON?query=" + searchInput;
        
        String result = webClientBuilder.build()
                .get()
                .uri(url)
                .header("Authorization", "KakaoAK " + secretKey)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 비동기 방식에서 동기 방식으로 변경 (단순화를 위해)

        System.out.println(result);
        
        return result;
    }

	@Override
	public SearchListResponse getVlogList(String keyword) throws Exception {
		JsonFactory jsonFactory = new GsonFactory();
		
		YouTube youtube = new YouTube.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
												jsonFactory, 
												request -> {})
												.build();
		
		// Youtube Search API를 위한 요청 객체 생성
		YouTube.Search.List request = youtube.search().list(Collections.singletonList("snippet"));
		LocalDateTime oneYearAgoDateTime = LocalDateTime.now().minusYears(1);
	    String dateTime = oneYearAgoDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
		
		SearchListResponse response = request
				.setKey(youtubeSecretKey)
				.setMaxResults(5L)
	            .setOrder("viewCount")
	            .setPublishedAfter(dateTime)
	            .setQ(keyword + " 브이로그")
	            .setType(Collections.singletonList("video"))
	            .setFields("items(id, snippet(title, thumbnails.default))")
	            .execute();
		
		return response;
	}
}
