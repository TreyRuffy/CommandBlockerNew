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
package me.treyruffy.commandblocker.api.command;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a basic command that is blocked.
 *
 * @since 3.0.0
 */
public class BasicCommand extends AbstractCommand {

  private @NotNull String[] args;

  /**
   * Creates a new basic blocked command.
   *
   * @param args               the command arguments to block
   * @param permission         the permission to unblock the command
   * @param messages           the messages sent when the command is executed
   * @param worlds             the worlds / servers the command is blocked in
   * @param playerCommands     the player commands executed when the blocked command is executed
   * @param consoleCommands    the console commands executed when the blocked command is executed
   * @param disableTabComplete false if the command should be able to be tab-completed
   * @param whitelistedPlayers the whitelisted players for the command
   * @param title              the title to send the player who executed the command
   * @param actionBar          the action bar to send the player who executed the command
   * @since 3.0.0
   */
  public BasicCommand(final @NotNull String[] args, final @Nullable String permission,
                      final @Nullable List<Component> messages, final @Nullable Set<String> worlds,
                      final @Nullable List<String> playerCommands, final @Nullable List<String> consoleCommands,
                      final boolean disableTabComplete, final @Nullable Set<UUID> whitelistedPlayers,
                      final @Nullable Title title, final @Nullable Component actionBar) {
    super(args[0], permission, messages, worlds, playerCommands, consoleCommands, disableTabComplete,
      whitelistedPlayers, title, actionBar);
    this.args = args;
  }

  /**
   * Creates a new basic blocked command.
   *
   * @param args               the command arguments to block
   * @param permission         the permission to unblock the command
   * @param messages           the messages sent when the command is executed
   * @param worlds             the worlds / servers the command is blocked in
   * @param playerCommands     the player commands executed when the blocked command is executed
   * @param consoleCommands    the console commands executed when the blocked command is executed
   * @param disableTabComplete false if the command should be able to be tab-completed
   * @since 3.0.0
   */
  public BasicCommand(final @NotNull String[] args, final @Nullable String permission,
                      final @Nullable List<Component> messages, final @Nullable Set<String> worlds,
                      final @Nullable List<String> playerCommands, final @Nullable List<String> consoleCommands,
                      final boolean disableTabComplete) {
    this(args, permission, messages, worlds, playerCommands, consoleCommands, disableTabComplete, null, null, null);
  }

  /**
   * Creates a new basic blocked command.
   *
   * @param args       the command arguments to block
   * @param permission the permission to unblock the command
   * @param messages   the messages sent when the command is executed
   * @since 3.0.0
   */
  public BasicCommand(final @NotNull String[] args, final @Nullable String permission,
                      final @Nullable List<Component> messages) {
    this(args, permission, messages, null, null, null, true);
  }

  @Override
  public @NotNull String type() {
    return "basic";
  }

  /**
   * Gets the command arguments of the blocked command.
   *
   * @return the command arguments
   * @since 3.0.0
   */
  public @NotNull String[] args() {
    return this.args;
  }

  /**
   * Sets the command arguments.
   *
   * @param args the command arguments
   * @since 3.0.0
   */
  public void args(final @NotNull String[] args) {
    this.args = args;
  }

  @Override
  public boolean checkBlock(final @NotNull CommandBlockerPlayers<?> player,
                            final @NotNull String @NotNull [] playerCommandArguments, final boolean opCommand) {
    // Check if the first String in the blocked command and the executed command are the same.
    if (this.args[0].equalsIgnoreCase(playerCommandArguments[0])) {

      // Check if the arguments in the executed command and the blocked command are the same.
      boolean argumentMatches = true;
      if (this.args.length > playerCommandArguments.length) {
        argumentMatches = false;
      } else {
        for (int i = 0; i < this.args.length; i++) {
          if (!this.args[i].equalsIgnoreCase(playerCommandArguments[i])) {
            argumentMatches = false;
            break;
          }
        }
      }

      // If the commands match, block the executed command
      return argumentMatches;
    }
    return false;
  }

  @Override
  public boolean isTabBlocked(final @NotNull CommandBlockerPlayers<?> player,
                              final @NotNull String @NotNull [] playerCommandArguments, final boolean opCommand) {
    // Check if the command matches the command the player is trying to tab
    if (!("/" + this.args()[0].trim()).equalsIgnoreCase(playerCommandArguments[0].trim())) {
      return false;
    }
    // Check if the command disables tab completion
    if (this.disableTabComplete()) {
      final StringBuilder commandStringBuilder = new StringBuilder();
      for (int i = 0; i < this.args().length; i++) {
        commandStringBuilder.append(this.args()[i]);
      }
      final StringBuilder incompleteStringBuilder = new StringBuilder();
      for (final String commandArgument : playerCommandArguments) {
        incompleteStringBuilder.append(commandArgument);
      }
      return CommandBlockerAPI.startsWithIgnoreCase("/" + commandStringBuilder, incompleteStringBuilder.toString());
    }
    return false;
  }

  @Override
  public boolean commandListBlock(final @NotNull CommandBlockerPlayers<?> player, final @NotNull String command,
                                  final boolean opCommand) {
    if (!this.disableTabComplete()) return false;
    return this.args()[0].equalsIgnoreCase(command);
  }

}
