package com.undery.dersoon_discord.command.ping;

import com.undery.dersoon_discord.command.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class PingpongCommand implements SlashCommand {

    private static final String COMMAND_NAME = "ping";

    @Override
    public String name() {
        return COMMAND_NAME;
    }

    @Override
    public CommandData commandData() {
        return Commands.slash(COMMAND_NAME, "봇 응답 테스트 명령어");
    }

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