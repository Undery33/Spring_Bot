/*
*  Spring Boot 어플리케이션 시작 후 Discord Slash Command 등록 실행
*
*  JDA Bean 초기화가 끝난 뒤 CommandRegistrar를 호출하여
*  명령어 등록 시점을 어플리케이션 실행 흐름과 분리합니다.
* */

package com.undery.dersoon_discord.discordinfra;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordCommandInitializer implements ApplicationRunner {

    private final JDA jda;
    private final CommandRegistrar commandRegistrar;

    // 명령어 등록 실행
    @Override
    public void run(ApplicationArguments args) {
        commandRegistrar.registerGuildCommands(jda);
    }
}