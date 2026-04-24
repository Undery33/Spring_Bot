package com.undery.dersoon_discord.discordinfra;

import com.undery.dersoon_discord.command.SlashCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandRegistrar {

    private final CommandRegistry commandRegistry;

    @Value("${discord.guild.id}")
    private String guildId;

    // 명령어 등록 로직
    public void register(JDA jda) {
        for (SlashCommand command : commandRegistry.getCommands()) {
            jda.upsertCommand(command.getName(), command.getDescription()).queue(
                    success -> log.info("/{} 명령어 등록 완료", command.getName()),
                    error -> log.error("/{} 명령어 등록 실패", command.getName(), error)
            );
        }
    }

    // 명령어 삭제(Global) 로직
    // 위험!!
    public void deleteAllGlobalCommands(JDA jda) {
        jda.updateCommands().queue(
                success -> log.info("글로벌 Slash Command 전체 삭제 완료"),
                error -> log.error("글로벌 Slash Command 전체 삭제 실패", error)
        );
    }

    public void register_guild(JDA jda) {
        Guild guild = jda.getGuildById(guildId);

        if (guild == null) {
            log.warn("Guild를 찾을 수 없습니다. guildId={}", guildId);
            return;
        }

        for (SlashCommand command : commandRegistry.getCommands()) {
            guild.upsertCommand(command.getName(), command.getDescription()).queue(
                    success -> log.info("/{} Guild 명령어 등록 완료", command.getName()),
                    error -> log.error("/{} Guild 명령어 등록 실패", command.getName(), error)
            );
        }
    }
}
