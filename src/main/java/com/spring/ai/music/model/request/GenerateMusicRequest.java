package com.spring.ai.music.model.request;

public record GenerateMusicRequest(
        String text,
        int duration
) {
}
