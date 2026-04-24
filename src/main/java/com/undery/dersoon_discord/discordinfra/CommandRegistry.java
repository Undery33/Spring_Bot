package com.undery.dersoon_discord.discordinfra;

import com.undery.dersoon_discord.command.SlashCommand;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommandRegistry {

    private final Map<String, SlashCommand> commandMap;

    public CommandRegistry(List<SlashCommand> commands) {
        this.commandMap = commands.stream()
                .collect(Collectors.toUnmodifiableMap(
                        SlashCommand::name,
                        Function.identity(),
                        (left, right) -> {
                            throw new IllegalStateException(
                                    "중복된 Slash Command 이름입니다. name=" + left.name()
                            );
                        }
                ));

        log.info("Slash Command 로드 완료 - count={}", commandMap.size());
    }

    public void execute(SlashCommandInteractionEvent event) {
        SlashCommand command = commandMap.get(event.getName());

        if (command == null) {
            event.reply("지원하지 않는 명령어입니다.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        try {
            command.execute(event);
        } catch (Exception exception) {
            log.error("Slash Command 실행 실패 - command={}", event.getName(), exception);

            if (!event.isAcknowledged()) {
                event.reply("명령어 실행 중 오류가 발생했습니다.")
                        .setEphemeral(true)
                        .queue();
            } else {
                event.getHook()
                        .sendMessage("명령어 실행 중 오류가 발생했습니다.")
                        .setEphemeral(true)
                        .queue();
            }
        }
    }

    public List<SlashCommand> getCommands() {
        return List.copyOf(commandMap.values());
    }
}