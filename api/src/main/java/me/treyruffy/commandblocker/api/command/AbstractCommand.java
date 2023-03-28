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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.InvalidCommandException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a command that can be blocked.
 *
 * @since 3.0.0
 */
public abstract class AbstractCommand {

  @NotNull String id;
  @Nullable String permission;
  @Nullable List<Component> messages;
  @Nullable Set<String> worlds;
  @Nullable List<String> playerCommands;
  @Nullable List<String> consoleCommands;
  boolean disableTabComplete;
  @Nullable Set<UUID> whitelistedPlayers;
  @Nullable Title title;
  @Nullable Component actionBar;

  /**
   * Creates a new blocked command.
   *
   * @param id                 the id of the command — for defaulting properties, so it can be anything
   * @param permission         the permission to unblock the command
   * @param messages           the messages sent when the command is executed
   * @param worlds             the worlds / servers the command is blocked in
   * @param playerCommands     the player commands executed when the blocked command is executed
   * @param consoleCommands    the console commands executed when the blocked command is executed
   * @param disableTabComplete if the command should be able to be tab-completed
   * @param whitelistedPlayers the whitelisted players for the command
   * @param title              the title to send the player who executed the command
   * @param actionBar          the action bar to send the player who executed the command
   * @since 3.0.0
   */
  public AbstractCommand(final @NotNull String id, final @Nullable String permission,
                         final @Nullable List<Component> messages, final @Nullable Set<String> worlds,
                         final @Nullable List<String> playerCommands, final @Nullable List<String> consoleCommands,
                         final boolean disableTabComplete, final @Nullable Set<UUID> whitelistedPlayers,
                         final @Nullable Title title, final @Nullable Component actionBar) {
    this.id = id;
    this.permission = permission;
    this.messages = messages;
    this.worlds = worlds;
    this.playerCommands = playerCommands;
    this.consoleCommands = consoleCommands;
    this.disableTabComplete = disableTabComplete;
    this.whitelistedPlayers = whitelistedPlayers;
    this.title = title;
    this.actionBar = actionBar;
  }

  /**
   * The type of command this is.
   * Example: "basic" or "regex".
   *
   * @return the type of command
   */
  public abstract @NotNull String type();

  /**
   * Gets the id.
   *
   * @return the id
   * @since 3.0.0
   */
  public @NotNull String id() {
    return this.id;
  }

  /**
   * Sets the id.
   * It is used to help default properties if the user does not set them.
   * Can be anything.
   *
   * @param id the blocked command id
   * @since 3.0.0
   */
  public void id(final @NotNull String id) {
    this.id = id;
  }

  /**
   * Gets the permission.
   *
   * @return the permission
   * @since 3.0.0
   */
  public @Nullable String permission() {
    if (this.permission == null) {
      return null;
    }
    return this.permission.replace(":", "").replace(" ", "");
  }

  /**
   * Sets the permission.
   *
   * @param permission the permission
   * @since 3.0.0
   */
  public void permission(final @Nullable String permission) {
    this.permission = permission;
  }

  /**
   * Gets the messages.
   *
   * @return the messages
   * @since 3.0.0
   */
  public @Nullable List<Component> messages() {
    return this.messages;
  }

  /**
   * Sets the messages.
   *
   * @param messages the messages
   * @since 3.0.0
   */
  public void messages(final @Nullable List<Component> messages) {
    this.messages = messages;
  }

  /**
   * Gets the worlds / servers of the blocked command.
   * Servers for BungeeCord and Velocity.
   * Worlds for the rest.
   *
   * @return the worlds / servers
   * @since 3.0.0
   */
  public @Nullable Set<String> worlds() {
    return (this.worlds != null && this.worlds.size() > 0) ? this.worlds : Collections.singleton("all");
  }

  /**
   * Sets the worlds / servers.
   *
   * @param worlds the worlds / servers
   * @since 3.0.0
   */
  public void worlds(final @Nullable Set<String> worlds) {
    this.worlds = worlds;
  }

  /**
   * Gets the player commands.
   *
   * @return the player commands
   * @since 3.0.0
   */
  public @Nullable List<String> playerCommands() {
    return this.playerCommands;
  }

  /**
   * Sets the player commands.
   *
   * @param playerCommands the player commands
   * @since 3.0.0
   */
  public void playerCommands(final @Nullable List<String> playerCommands) {
    this.playerCommands = playerCommands;
  }

  /**
   * Gets the console commands.
   *
   * @return the console commands
   * @since 3.0.0
   */
  public @Nullable List<String> consoleCommands() {
    return this.consoleCommands;
  }

  /**
   * Sets the console commands of the blocked command.
   *
   * @param consoleCommands the console commands
   * @since 3.0.0
   */
  public void consoleCommands(final @Nullable List<String> consoleCommands) {
    this.consoleCommands = consoleCommands;
  }

  /**
   * Gets if the command can be tab-autocompleted.
   *
   * @return {@code true} if the command cannot be tab-autocompleted
   * @since 3.0.0
   */
  public boolean disableTabComplete() {
    return this.disableTabComplete;
  }

  /**
   * Sets if the command can be tab-autocompleted.
   *
   * @param disableTabComplete {@code true} if the command cannot be tab-autocompleted
   * @since 3.0.0
   */
  public void disableTabComplete(final boolean disableTabComplete) {
    this.disableTabComplete = disableTabComplete;
  }

  /**
   * Gets the whitelisted players of the blocked command.
   *
   * @return the UUIDs of the whitelisted players
   * @since 3.0.0
   */
  public @Nullable Set<UUID> whitelistedPlayers() {
    return this.whitelistedPlayers;
  }

  /**
   * Sets the whitelisted players of the blocked command.
   *
   * @param whitelistedPlayers the UUIDs of the whitelisted players
   * @since 3.0.0
   */
  public void whitelistedPlayers(final @Nullable Set<UUID> whitelistedPlayers) {
    this.whitelistedPlayers = whitelistedPlayers;
  }

  /**
   * Gets the title of the blocked command.
   *
   * @return the title
   * @since 3.0.0
   */
  public @Nullable Title title() {
    return this.title;
  }

  /**
   * Sets the title of the blocked command.
   *
   * @param title the title
   * @since 3.0.0
   */
  public void title(final @Nullable Title title) {
    this.title = title;
  }

  /**
   * Gets the action bar of the blocked command.
   *
   * @return the action bar
   * @since 3.0.0
   */
  public @Nullable Component actionBar() {
    return this.actionBar;
  }

  /**
   * Sets the action bar of the blocked command.
   *
   * @param actionBar the action bar
   * @since 3.0.0
   */
  public void actionBar(final @Nullable Component actionBar) {
    this.actionBar = actionBar;
  }

  /**
   * The test to run to check if this command is blocked.
   *
   * @param player                 the player who executed the command
   * @param playerCommandArguments the command arguments executed by the player
   * @param opCommand              {@code true} if the command is executed by an operator
   * @return {@code true} if the command matches the test
   * @since 3.0.0
   */
  public abstract boolean checkBlock(final @NotNull CommandBlockerPlayers<?> player,
                                     final @NotNull String @NotNull [] playerCommandArguments,
                                     final boolean opCommand) throws InvalidCommandException;

  /**
   * The test to run to check if this command is blocked from tab completion.
   *
   * @param player                 the player who is tab completing the command
   * @param playerCommandArguments the command arguments executed by the player
   * @param opCommand              {@code true} if the command is executed by an operator
   * @return {@code true} if the command matches the test
   * @since 3.0.0
   */
  public abstract boolean isTabBlocked(final @NotNull CommandBlockerPlayers<?> player,
                                       final @NotNull String @NotNull [] playerCommandArguments,
                                       final boolean opCommand) throws InvalidCommandException;

  /**
   * The test to run to check if this command is blocked from command completion.
   *
   * @param player    the player who is tab completing the command
   * @param command   the command to check against
   * @param opCommand {@code true} if the command is executed by an operator
   * @return {@code true} if the command matches the test
   * @since 3.0.0
   */
  public abstract boolean commandListBlock(final @NotNull CommandBlockerPlayers<?> player,
                                           final @NotNull String command, final boolean opCommand) throws InvalidCommandException;
}
