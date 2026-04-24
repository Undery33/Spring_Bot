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

    @Override
    public void run(ApplicationArguments args) {
        commandRegistrar.registerGuildCommands(jda);
    }
}