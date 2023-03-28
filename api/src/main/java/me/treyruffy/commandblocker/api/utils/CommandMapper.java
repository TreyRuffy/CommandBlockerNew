/*
 * This file is part of Command Blocker for Minecraft.
 * Copyright (C) 2023 TreyRuffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.treyruffy.commandblocker.api.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.BasicCommand;
import me.treyruffy.commandblocker.api.command.RegexCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/**
 * Converts to and from the configuration.
 *
 * @since 3.0.0
 */
public final class CommandMapper {

  private CommandMapper() {
    // Utility class
  }

  /**
   * Converts a {@link AbstractCommand} to a {@link ConfigurationCommand}.
   *
   * @param command the {@link AbstractCommand}
   * @return the converted {@link ConfigurationCommand}
   * @since 3.0.0
   */
  public static @NotNull ConfigurationCommand commandToConfiguration(final AbstractCommand command) {
    final ConfigurationCommand configurationCommand = new ConfigurationCommand();
    if (command instanceof BasicCommand) {
      configurationCommand.args = ((BasicCommand) command).args();
    } else if (command instanceof RegexCommand) {
      configurationCommand.regex = ((RegexCommand) command).regexCommand();
    }
    configurationCommand.permission = command.permission();

    final List<Component> messages = command.messages();
    if (messages != null) {
      configurationCommand.messages = new String[messages.size()];
      for (int i = 0; i < messages.size(); i++) {
        configurationCommand.messages[i] = MiniMessage.miniMessage().serialize(messages.get(i));
      }
    } else {
      configurationCommand.messages = new String[0];
    }

    final Set<String> worlds = command.worlds();
    if (worlds != null) {
      configurationCommand.worlds = worlds.toArray(new String[0]);
    }

    final List<String> playerCommands = command.playerCommands();
    if (playerCommands != null) {
      configurationCommand.playerCommands = playerCommands.toArray(new String[0]);
    }
    final List<String> consoleCommands = command.consoleCommands();
    if (consoleCommands != null) {
      configurationCommand.consoleCommands = consoleCommands.toArray(new String[0]);
    }

    configurationCommand.disableTabCompletion = command.disableTabComplete();

    final Set<UUID> whitelistedPlayers = command.whitelistedPlayers();
    if (whitelistedPlayers != null) {
      configurationCommand.worlds = whitelistedPlayers.stream().map(UUID::toString).toArray(String[]::new);
    }

    final Title title = command.title();
    final Component titleComponent = title != null ? title.title() : null;
    configurationCommand.title = new ConfigurationTitle();
    if (titleComponent != null) {
      configurationCommand.title.title = MiniMessage.miniMessage().serialize(titleComponent);
    }
    final Component subtitle = title != null ? title.subtitle() : null;
    if (subtitle != null) {
      configurationCommand.title.subtitle = MiniMessage.miniMessage().serialize(subtitle);
    }
    final Title.Times times = title != null ? title.times() : null;
    if (times != null) {
      configurationCommand.title.times = new ConfigurationTitleTime();

      configurationCommand.title.times.fadeIn = times.fadeIn().toMillis();
      configurationCommand.title.times.stay = times.stay().toMillis();
      configurationCommand.title.times.fadeOut = times.fadeOut().toMillis();
    }

    final Component actionBar = command.actionBar();
    if (actionBar != null) {
      configurationCommand.actionBar = MiniMessage.miniMessage().serialize(actionBar);
    }

    return configurationCommand;
  }

  /**
   * Converts a {@link ConfigurationCommand} to a {@link AbstractCommand}.
   *
   * @param blockedCommand the {@link ConfigurationCommand}
   * @return the converted {@link AbstractCommand}
   */
  public static @Nullable AbstractCommand commandFromConfig(final @NotNull ConfigurationCommand blockedCommand) {
    if (blockedCommand.disabled) return null;

    final List<Component> messageList;
    if (blockedCommand.messages != null) {
      messageList = new ArrayList<>();
      for (final String messageLine : blockedCommand.messages) {
        if (messageLine != null) messageList.add(PlainTextComponentSerializer.plainText().deserialize(messageLine));
      }
    } else {
      messageList = null;
    }

    final Set<String> worldSet;
    if (blockedCommand.worlds != null) {
      worldSet = new HashSet<>(Arrays.asList(blockedCommand.worlds));
    } else {
      worldSet = null;
    }

    final Set<UUID> whitelistSet;
    if (blockedCommand.whitelistedPlayers != null) {
      final String[] whitelistList = blockedCommand.whitelistedPlayers;
      final List<UUID> whitelistListUUID = new ArrayList<>();
      for (final String uuid : whitelistList) {
        whitelistListUUID.add(UUID.fromString(uuid));
      }
      whitelistSet = new HashSet<>(whitelistListUUID);
    } else {
      whitelistSet = null;
    }

    Component title = null;
    Component subtitle = null;
    Title.Times times = null;
    if (blockedCommand.title != null) {
      if (blockedCommand.title.title != null) {
        title = PlainTextComponentSerializer.plainText().deserialize(blockedCommand.title.title);
      }

      if (blockedCommand.title.subtitle != null) {
        subtitle = PlainTextComponentSerializer.plainText().deserialize(blockedCommand.title.subtitle);
      }

      if (blockedCommand.title.times != null) {
        final Duration fadeIn;
        final Duration stay;
        final Duration fadeOut;
        if (blockedCommand.title.times.fadeIn > 0) {
          fadeIn = Duration.ofMillis(blockedCommand.title.times.fadeIn);
        } else {
          fadeIn = Duration.ofMillis(0);
        }
        if (blockedCommand.title.times.stay > 0) {
          stay = Duration.ofMillis(blockedCommand.title.times.stay);
        } else {
          stay = Duration.ofMillis(0);
        }
        if (blockedCommand.title.times.fadeOut > 0) {
          fadeOut = Duration.ofMillis(blockedCommand.title.times.fadeOut);
        } else {
          fadeOut = Duration.ofMillis(0);
        }
        times = Title.Times.times(fadeIn, stay, fadeOut);
      }
    }

    final Component actionBar;
    if (blockedCommand.actionBar != null) {
      actionBar = PlainTextComponentSerializer.plainText().deserialize(blockedCommand.actionBar);
    } else {
      actionBar = null;
    }

    final Title titleObject;
    if (title != null && subtitle != null) {
      if (times == null) {
        titleObject = Title.title(title, subtitle);
      } else {
        titleObject = Title.title(title, subtitle, times);
      }
    } else {
      titleObject = null;
    }

    final AbstractCommand command;
    if (blockedCommand.args != null && blockedCommand.args.length > 0) {
      command = new BasicCommand(blockedCommand.args, blockedCommand.permission, messageList, worldSet,
        Arrays.asList(blockedCommand.playerCommands), Arrays.asList(blockedCommand.consoleCommands),
        blockedCommand.disableTabCompletion, whitelistSet, titleObject, actionBar);
    } else if (blockedCommand.regex != null) {
      command = new RegexCommand(blockedCommand.regex, blockedCommand.permission, messageList, worldSet,
        Arrays.asList(blockedCommand.playerCommands), Arrays.asList(blockedCommand.consoleCommands),
        blockedCommand.disableTabCompletion, whitelistSet, titleObject, actionBar);
    } else {
      command = null;
    }

    return command;
  }

  /**
   * The middleman between the configuration and the {@link AbstractCommand}.
   *
   * @since 3.0.0
   */
  @ConfigSerializable
  public static class ConfigurationCommand {
    String[] args;
    @Nullable String regex;
    boolean disabled = false;
    @Nullable String permission;
    @Nullable String[] messages;
    @Nullable String[] worlds;
    @Setting("playerCommands")
    @Nullable String[] playerCommands;
    @Setting("consoleCommands")
    @Nullable String[] consoleCommands;
    @Setting("disableTabCompletion")
    boolean disableTabCompletion = true;
    @Setting("whitelistedPlayers")
    @Nullable String[] whitelistedPlayers;
    @Nullable ConfigurationTitle title;
    @Setting("actionBar")
    @Nullable String actionBar;
  }

  /**
   * A class to store the title information.
   *
   * @since 3.0.0
   */
  @ConfigSerializable
  public static class ConfigurationTitle {
    @Nullable String title;
    @Nullable String subtitle;
    @Nullable ConfigurationTitleTime times;
  }

  /**
   * A class to store the title time.
   *
   * @since 3.0.0
   */
  @ConfigSerializable
  public static class ConfigurationTitleTime {
    @Setting("fadeIn")
    long fadeIn = 0;
    long stay = 0;
    @Setting("fadeOut")
    long fadeOut = 0;
  }
}
