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
package me.treyruffy.commandblocker.common.blocking;

import java.util.HashMap;
import java.util.List;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.DefaultValues;
import me.treyruffy.commandblocker.common.util.Universal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Send out the outputs from the blocked command.
 *
 * @since 3.0.0
 */
public class BlockedCommandOutput {
  private final @NotNull CommandBlockerPlayers<?> player;
  private final @NotNull AbstractCommand command;
  private final @Nullable HashMap<String, String> placeholders;

  protected BlockedCommandOutput(final @NotNull CommandBlockerPlayers<?> player, final @NotNull AbstractCommand command,
                       final @Nullable HashMap<String, String> placeholders) {
    this.player = player;
    this.command = command;
    this.placeholders = placeholders;
  }

  /**
   * Gets the blocked command.
   *
   * @return the blocked command
   * @since 3.0.0
   */
  public @NotNull AbstractCommand command() {
    return this.command;
  }

  /**
   * Sends the messages set for the blocked command.
   *
   * @since 3.0.0
   */
  public void sendMessages() {
    List<Component> messages = this.command.messages();
    if (messages == null || messages.isEmpty()) {
      messages = DefaultValues.defaultMessage(this.command.id());
    }
    if (messages.size() == 1 && messages.get(0).equals(PlainTextComponentSerializer.plainText().deserialize("none"))) {
      return;
    }
    final HashMap<String, Component> additionalComponentVariables = new HashMap<>();
    additionalComponentVariables.put("command", Component.text(this.command.id()));
    for (final Component message : messages) {
      this.player.sendMessage(Universal.get().universalMethods().translateVariables().componentVariableTranslate(message, this.player, additionalComponentVariables));
    }
  }

  /**
   * Sends the player commands set for the blocked command.
   *
   * @since 3.0.0
   */
  public void sendPlayerCommands() {
    final List<String> commands = this.command().playerCommands();
    if (commands != null) {
      for (String command : commands) {
        if (this.placeholders != null && !this.placeholders.isEmpty()) {
          for (final String placeholder : this.placeholders.keySet()) {
            command = command.replace(placeholder, this.placeholders.get(placeholder));
          }
        }
        this.player.performCommand(command.replace("%player%", this.player.name()).replace("%displayname%",
        this.player.displayName()).replace("%username%", this.player.displayName()).replace("%world%",
        this.player.world()).replace("%uuid%", this.player.uuid().toString()));
      }
    }
  }

  /**
   * Sends the console commands set for the blocked command.
   *
   * @since 3.0.0
   */
  public void sendConsoleCommands() {
    final CommandBlockerValues<?> methods = Universal.get().universalMethods();
    final List<String> commands = this.command().consoleCommands();
    if (commands != null) {
      for (String command : commands) {
        if (this.placeholders != null && !this.placeholders.isEmpty()) {
          for (final String placeholder : this.placeholders.keySet()) {
            command = command.replace(placeholder, this.placeholders.get(placeholder));
          }
        }
        methods.executeConsoleCommand(command.replace("%player%", this.player.name()).replace("%displayname%",
        this.player.displayName()).replace("%username%", this.player.displayName()).replace("%world%",
        this.player.world()).replace("%uuid%", this.player.uuid().toString()));
      }
    }
  }

  /**
   * Sends a title to the player who executed the command.
   *
   * @since 3.0.0
   */
  public void sendTitle() {
    final Title titleObject = this.command.title();
    if (titleObject != null) {
      final Component title = titleObject.title();
      final Component subtitle = titleObject.subtitle();

      final HashMap<String, Component> additionalComponentVariables = new HashMap<>();
      additionalComponentVariables.put("command", Component.text(this.command.id()));

      final Component translatedTitle =
      Universal.get().universalMethods().translateVariables().componentVariableTranslate(title, this.player,
      additionalComponentVariables);
      final Component translatedSubtitle =
      Universal.get().universalMethods().translateVariables().componentVariableTranslate(subtitle, this.player,
      additionalComponentVariables);

      final Title.Times titleTime = titleObject.times();
      final Title fullTitle;
      if (titleTime == null) {
        fullTitle = Title.title(translatedTitle, translatedSubtitle);
      } else {
        fullTitle = Title.title(translatedTitle, translatedSubtitle, titleTime);
      }
      this.player.showTitle(fullTitle);
    }
  }

  /**
   * Sends an action bar to the player who executed the command.
   *
   * @since 3.0.0
   */
  public void sendActionBar() {
    final Component actionBar = this.command.actionBar();
    if (actionBar != null) {
      final HashMap<String, Component> additionalComponentVariables = new HashMap<>();
      additionalComponentVariables.put("command", Component.text(this.command.id()));
      this.player.sendActionBar(Universal.get().universalMethods().translateVariables().componentVariableTranslate(actionBar, this.player, additionalComponentVariables));
    }
  }

  /**
   * Sends all the different outputs.
   *
   * @since 3.0.0
   */
  public void sendAll() {
    this.sendMessages();
    this.sendPlayerCommands();
    this.sendConsoleCommands();
    this.sendTitle();
    this.sendActionBar();
  }

}
