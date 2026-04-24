/*
*  Slash Command를 Discord Guild에 등록
*
*  CommandRegistry에서 수집한 명령어 등록 정보를 가져와 Discord API에 동기화하는 책임만 담당합니다.
*  실제 명령어 실행 로직은 각 SlashCommand 구현체에 위임됩니다.
* */

package com.undery.dersoon_discord.discordinfra;

import com.undery.dersoon_discord.command.SlashCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandRegistrar {

    private final CommandRegistry commandRegistry;

    // Global과 Guild가 있지만, Guild로 한 이유는 빠른 테스팅을 위해서이다.
    // 추후 Public으로 전환 시 Global에 맞게 수정이 필요하다. (jdk)
    @Value("${discord.guild.id}")
    private String guildId;

    // Guild ID는 Test용 서버 ID임.
    public void registerGuildCommands(JDA jda) {
        Guild guild = jda.getGuildById(guildId);

        if (guild == null) {
            throw new IllegalStateException("Guild를 찾을 수 없습니다. guildId=" + guildId);
        }

        List<CommandData> commandDataList = commandRegistry.getCommands()
                .stream()
                .map(SlashCommand::commandData)
                .toList();

        guild.updateCommands()
                .addCommands(commandDataList)
                .queue(
                        success -> log.info("Guild Slash Command 등록 완료 - count={}", commandDataList.size()),
                        error -> log.error("Guild Slash Command 등록 실패", error)
                );
    }
}