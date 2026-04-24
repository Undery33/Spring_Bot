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

    @Value("${discord.guild.id}")
    private String guildId;

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