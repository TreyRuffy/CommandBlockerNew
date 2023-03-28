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
package me.treyruffy.commandblocker.common.util;

import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The accessor for each platform's main methods.
 *
 * @param <T> the type of the player
 * @since 3.0.0
 */
public interface CommandBlockerValues<T> {

  /**
   * Gets the plugin version.
   *
   * @return the plugin version as a string
   * @since 3.0.0
   */
  @NotNull String commandBlockerVersion();

  /**
   * Gets the server version.
   *
   * @return the server version as a string
   * @since 3.0.0
   */
  @NotNull String serverVersion();

  /**
   * The universal logger.
   *
   * @return the logger
   * @since 3.0.0
   */
  @NotNull Logger log();

  /**
   * Sets up the metrics for each platform.
   *
   * @since 3.0.0
   */
  void setupMetrics();

  /**
   * Executes a console command.
   *
   * @param command the command to execute
   * @since 3.0.0
   */
  void executeConsoleCommand(@NotNull String command);

  /**
   * Executes a player command.
   *
   * @param player  the player to execute the command
   * @param command the command to execute
   * @since 3.0.0
   */
  void executePlayerCommand(@NotNull CommandBlockerPlayers<T> player, @NotNull String command);

  /**
   * Sends a message.
   *
   * @param commandSender the sender of the message
   * @param message       the message to send
   * @since 3.0.0
   */
  void sendMessage(@NotNull CommandBlockerPlayers<T> commandSender, @NotNull Component message);

  /**
   * The universal component translator.
   *
   * @return the component translator
   * @since 3.0.0
   */
  @NotNull ComponentTranslator translateVariables();

  /**
   * Internationalization.
   *
   * @param key the key to find
   * @param sender where the message is being sent
   * @param formatters string formatters
   * @return a translated string
   * @since 3.0.0
   */
  @NotNull Component i18n(@NotNull String key, @Nullable Object sender, Object... formatters);
}
