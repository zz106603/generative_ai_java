package com.spring.ai.music.service;

import com.spring.ai.music.model.request.GenerateMusicRequest;

public interface MusicService {
    String generateAndDownloadMusic(GenerateMusicRequest request);

    String proccessCheck(long taskId);
}
