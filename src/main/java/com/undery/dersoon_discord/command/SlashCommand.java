package com.undery.dersoon_discord.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface SlashCommand {

    String name();

    CommandData commandData();

    void execute(SlashCommandInteractionEvent event);
}