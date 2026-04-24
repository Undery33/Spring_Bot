package com.undery.dersoon_discord.config;

import com.undery.dersoon_discord.discordinfra.CommandRegistrar;
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
@RequiredArgsConstructor
@Configuration
public class DiscordConfig {

    private final DiscordEventListener discordEventListener;
    private final CommandRegistrar commandRegistrar;

    private JDA jda;

    @Bean
    public JDA jda(@Value("${discord.bot.token}") String token) throws InterruptedException {
        // Discord 연결
        log.info("Discord 봇 연결 시도 중...");

        this.jda = JDABuilder.createDefault(token)
                .addEventListeners(discordEventListener)
                .build()
                .awaitReady();

        log.info("Discord 봇 연결 완료 - botName={}", this.jda.getSelfUser().getName());

        // Command 등록
        // CommandRegistrar 함수를 가져와서 실행
        commandRegistrar.register_guild(this.jda);

        return this.jda;
    }

    @PreDestroy
    public void shutdown() {
        if (jda != null) {
            log.info("Discord 봇 종료 중...");
            jda.shutdown();
        }
    }
}