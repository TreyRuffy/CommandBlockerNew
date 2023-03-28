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
import java.util.regex.PatternSyntaxException;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.InvalidCommandException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a regex-matching command that is blocked.
 *
 * @since 3.0.0
 */
public class RegexCommand extends AbstractCommand {

  private @NotNull String regexCommand;

  /**
   * Creates a new regex blocked command.
   *
   * @param regexCommand       the regex command to block
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
  public RegexCommand(final @NotNull String regexCommand, final @Nullable String permission,
                      final @Nullable List<Component> messages, final @Nullable Set<String> worlds,
                      final @Nullable List<String> playerCommands, final @Nullable List<String> consoleCommands,
                      final boolean disableTabComplete, final @Nullable Set<UUID> whitelistedPlayers,
                      final @Nullable Title title, final @Nullable Component actionBar) {
    super("regex_command", permission, messages, worlds, playerCommands, consoleCommands, disableTabComplete,
      whitelistedPlayers, title, actionBar);
    this.regexCommand = regexCommand;
  }

  /**
   * Creates a new regex blocked command.
   *
   * @param regexCommand       the regex-matching command to block
   * @param permission         the permission to unblock the command
   * @param messages           the messages sent when the command is executed
   * @param worlds             the worlds / servers the command is blocked in
   * @param playerCommands     the player commands executed when the blocked command is executed
   * @param consoleCommands    the console commands executed when the blocked command is executed
   * @param disableTabComplete false if the command should be able to be tab-completed
   * @since 3.0.0
   */
  public RegexCommand(final @NotNull String regexCommand, final @Nullable String permission,
                      final @Nullable List<Component> messages, final @Nullable Set<String> worlds,
                      final @Nullable List<String> playerCommands, final @Nullable List<String> consoleCommands,
                      final boolean disableTabComplete) {
    this(regexCommand, permission, messages, worlds, playerCommands, consoleCommands, disableTabComplete, null, null,
      null);
  }

  /**
   * Creates a new regex blocked command.
   *
   * @param regexCommand the regex command to block
   * @param permission   the permission to unblock the command
   * @param messages     the messages sent when the command is executed
   * @since 3.0.0
   */
  public RegexCommand(final @NotNull String regexCommand, final @Nullable String permission,
                      final @Nullable List<Component> messages) {
    this(regexCommand, permission, messages, null, null, null, true);
  }

  @Override
  public @NotNull String type() {
    return "regex";
  }

  /**
   * Gets the regex.
   *
   * @return the command regex
   * @since 3.0.0
   */
  public @NotNull String regexCommand() {
    return this.regexCommand;
  }

  /**
   * Sets the regex.
   *
   * @param regexCommand the command regex
   * @since 3.0.0
   */
  public void regexCommand(final @NotNull String regexCommand) {
    this.regexCommand = regexCommand;
  }

  @Override
  public boolean checkBlock(final @NotNull CommandBlockerPlayers<?> player,
                            final @NotNull String @NotNull [] playerCommandArguments, final boolean opCommand) throws InvalidCommandException {
    final StringBuilder fullCommandBuilder = new StringBuilder();
    for (final String commandArgument : playerCommandArguments) {
      fullCommandBuilder.append(commandArgument).append(" ");
    }
    final String fullCommand = fullCommandBuilder.substring(0, fullCommandBuilder.length() - 1);

    try {
      if (fullCommand.matches(this.regexCommand)) {
        return true;
      }
    } catch (final PatternSyntaxException exception) {
      final String errorMessage = "The regex command '" + this.regexCommand + "' is invalid!\n\tCheck your " +
      "CommandBlocker configuration folder for errors.";
      throw new InvalidCommandException(errorMessage, exception);
    }
    return false;
  }

  @Override
  public boolean isTabBlocked(final @NotNull CommandBlockerPlayers<?> player,
                              final @NotNull String @NotNull [] playerCommandArguments, final boolean opCommand) throws InvalidCommandException {
    if (!this.disableTabComplete()) {
      return false;
    }
    return this.checkBlock(player, playerCommandArguments, opCommand);
  }

  @Override
  public boolean commandListBlock(final @NotNull CommandBlockerPlayers<?> player, final @NotNull String command,
                                  final boolean opCommand) {
    return command.matches(this.regexCommand);
  }

}
