package com.wooyeah.place.dto;

public class CompletionRequestDto {

	private String model;
	private String prompt;
	private float temperature;
	
	// 혹시 문제가 생긴다면 protected
	public CompletionRequestDto(String model, String prompt, float temperature) {
		super();
		this.model = model;
		this.prompt = prompt;
		this.temperature = temperature;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public float getTemperature() {
		return temperature;
	}

	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}
	
}
