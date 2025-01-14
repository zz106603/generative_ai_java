package com.spring.ai.music.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ai.music.model.request.GenerateMusicRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class MusicServiceImpl implements MusicService {

    private final WebClient webClient;

    public MusicServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://70ad-34-83-60-227.ngrok-free.app").build();
    }

    @Override
    public String generateAndDownloadMusic(GenerateMusicRequest request) {
        // Python API 호출
        String pythonResponse = webClient.post()
                .uri("/generate-music")
                .header("Content-Type", "application/json")
                .bodyValue("{\"text\": \"" + request.text() + "\", \"duration\": " + request.duration() + "}")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Python 응답에서 fileUrl 추출
        String fileUrl = extractFileUrlFromResponse(pythonResponse);

        System.out.println(pythonResponse);

        // 클라이언트에 즉시 응답 반환
        return fileUrl;
    }

    @Override
    public String proccessCheck(long taskId) {
        // Python API 호출
        String pythonResponse = webClient.get()
                .uri("/check-task/" + taskId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(pythonResponse);

        // 클라이언트에 즉시 응답 반환
        return pythonResponse;
    }

    // 응답에서 fileUrl 추출
    private String extractFileUrlFromResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.get("file_url").asText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Python response", e);
        }
    }
}
