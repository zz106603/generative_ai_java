package com.spring.ai.common;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    @BeforeAll
    public static void setUp() throws InterruptedException {
        // .env 파일 로드
        Dotenv dotenv = Dotenv.configure().load();

        // 환경 변수 값으로 시스템 속성 설정
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));

        System.setProperty("spring.profiles.active", dotenv.get("SPRING_PROFILES_ACTIVE"));
    }
}
