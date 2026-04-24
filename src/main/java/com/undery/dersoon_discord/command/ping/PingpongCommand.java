/*
*  /ping 명령어
*
*  명령어 등록 정보와 실행 로직을 함께 캡슐화하였습니다.
* */

package com.undery.dersoon_discord.command.ping;

import com.undery.dersoon_discord.command.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class PingpongCommand implements SlashCommand {

    private static final String COMMAND_NAME = "ping";

    // 이름 정의
    @Override
    public String name() {
        return COMMAND_NAME;
    }

    // 커멘드 설명 정의
    @Override
    public CommandData commandData() {
        return Commands.slash(COMMAND_NAME, "봇 응답 테스트 명령어");
    }

    // 커멘드 명령어 반환
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        long startTime = System.currentTimeMillis();

        event.reply("Pong!")
                .flatMap(reply ->
                        event.getHook().editOriginalFormat(
                                "Pong! %dms",
                                System.currentTimeMillis() - startTime
                        )
                )
                .queue();
    }
}