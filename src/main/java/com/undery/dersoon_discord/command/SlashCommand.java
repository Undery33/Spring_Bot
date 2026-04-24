/*
*  Discord Slash Command 공통 계약 정의
*
*  각 명령어는 명령어 일므, Discord 등록 데이터, 실행 로직을 독립적으로 제공합니다.
*  새 명령어를 추가할 때 해당 인터페이스를 구현하면 Registry와 Registrar가 자동으로 명령어를 수집 후 처리합니다.
* */
package com.undery.dersoon_discord.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface SlashCommand {

    String name();

    CommandData commandData();

    void execute(SlashCommandInteractionEvent event);
}