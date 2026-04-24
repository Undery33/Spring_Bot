/*
*  Discord JDA 인스턴스 생성 후 Spring Bean으로 등록
*  Bot Token을 설정에서 받아와 Discord Gateway에 연결하고,
*  종료 시 JDA 리소스를 정리합니다.
*  명령어 등록은 CommandRegistrar 계층에 위임합니다.
* */

package com.undery.dersoon_discord.config;

import com.undery.dersoon_discord.listener.DiscordEventListener;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DiscordConfig {

    private final DiscordEventListener discordEventListener;

    private JDA jda;

    // Discord 연결 시도
    @Bean
    public JDA jda(@Value("${discord.bot.token}") String token) throws InterruptedException {
        log.info("Discord 봇 연결 시도 중...");

        this.jda = JDABuilder.createDefault(token)
                .addEventListeners(discordEventListener)
                .build()
                .awaitReady();

        log.info("Discord 봇 연결 완료 - botName={}", this.jda.getSelfUser().getName());

        return this.jda;
    }

    // Discord 연결 종료
    @PreDestroy
    public void shutdown() {
        if (jda == null) {
            return;
        }
        log.info("Discord 봇 종료 중...");
        jda.shutdown();
    }
}