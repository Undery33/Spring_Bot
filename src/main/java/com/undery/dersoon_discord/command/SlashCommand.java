package com.undery.dersoon_discord.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommand {

    String getName();

    String getDescription();

    void execute(SlashCommandInteractionEvent event);
}