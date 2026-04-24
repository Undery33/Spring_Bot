/*
*  Discord에서 발생한 Slash Command 이벤트 수신
*
*  이벤트 수신과 로깅만 담당하며, 실제 명령어 탐색과 실행은 CommandRegistry에 위임합니다.
* */

package com.undery.dersoon_discord.listener;

import com.undery.dersoon_discord.discordinfra.CommandRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DiscordEventListener extends ListenerAdapter {

    private final CommandRegistry commandRegistry;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        log.info("Slash Command 수신 - command={}, user={}",
                event.getName(),
                event.getUser().getName()
        );

        commandRegistry.execute(event);
    }
}