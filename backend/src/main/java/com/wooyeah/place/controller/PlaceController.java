package com.wooyeah.place.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.google.api.services.youtube.model.SearchListResponse;
import com.wooyeah.place.dto.ChatGPTRequest;
import com.wooyeah.place.dto.ChatGPTResponse;
import com.wooyeah.place.dto.CompletionRequestDto;
import com.wooyeah.place.dto.RequestSearchDto;
import com.wooyeah.place.service.PlaceService;

@RestController
@RequestMapping("/place")
public class PlaceController {

	private final PlaceService placeService;
	
	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}
	
	@GetMapping(value="/search", produces = "application/json;charset=utf-8")
	public ResponseEntity<?> searchPlace(@RequestParam(value="searchInput", required = false) String searchInput) throws Exception {
		String response = placeService.getPlaceList(searchInput);
		return ResponseEntity.ok().body(response);
	}
	
	@Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    // 참고 : https://jypark1111.tistory.com/203
    @GetMapping("/chat")
    public String chat(@RequestParam(name = "location") String location, @RequestParam(name = "target") String target){
    	String prompt = location + target + " 근처의 관광지를 한국어로 6개 알려줘. 근데 이름만 들어간 JSON 배열로 줘";
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGPTResponse chatGPTResponse =  template.postForObject(apiURL, request, ChatGPTResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }
    
    @GetMapping("/youtube")
    public ResponseEntity<?> youtube(@RequestParam(name="keyword") String keyword) throws Exception {
    	System.out.println(keyword);
    	SearchListResponse response = placeService.getVlogList(keyword);
    	System.out.println(response);
    	return ResponseEntity.ok().body(response);
    }
	
}
