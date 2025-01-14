package com.spring.ai.music.controller;

import com.spring.ai.common.swagger.SwaggerCommonResponses;
import com.spring.ai.music.model.request.GenerateMusicRequest;
import com.spring.ai.music.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Music", description = "Music 관련 API")
//@SecurityRequirement(name = "bearerAuth")
@SwaggerCommonResponses
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    /**
     * @param request (text, duration)
     */
    @PostMapping("/music")
    @Operation(summary = "Music 생성", description = "Music을 생성합니다.")
    public String generateMusic(@RequestBody GenerateMusicRequest request) {
        return musicService.generateAndDownloadMusic(request);
    }

    @GetMapping("/music/process")
//    @Operation(summary = "Music 생성", description = "Music을 생성합니다.")
    public String processingMusic(@RequestParam long taskId) {
        return musicService.proccessCheck(taskId);
    }

}
