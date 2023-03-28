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
package me.treyruffy.commandblocker.api.players;

import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

/**
 * Common elements for players for each platform.
 *
 * @param <T> the player type
 * @since 3.0.0
 */
public interface CommandBlockerPlayers<T> {

  /**
   * Gets the player back as the original object.
   * Will convert back to whatever platform the plugin is from.
   *
   * @return the original player object
   * @since 3.0.0
   */
  @NotNull T player();

  /**
   * Gets the name.
   *
   * @return the name
   * @since 3.0.0
   */
  @NotNull String name();

  /**
   * Gets the display name.
   *
   * @return the display name
   * @since 3.0.0
   */
  @NotNull String displayName();

  /**
   * Checks if the player is an operator.
   *
   * @return {@code true} if the player is an operator
   * @since 3.0.0
   */
  boolean isOp();

  /**
   * Gets the UUID.
   *
   * @return the UUID
   * @since 3.0.0
   */
  @NotNull UUID uuid();

  /**
   * Sends the player a message.
   *
   * @param message the message component to send
   * @since 3.0.0
   */
  void sendMessage(@NotNull Component message);

  /**
   * Performs a command as the player.
   *
   * @param command the command to perform
   * @since 3.0.0
   */
  void performCommand(@NotNull String command);

  /**
   * Sends an action bar message.
   *
   * @param message the message component to send
   * @since 3.0.0
   */
  void sendActionBar(@NotNull Component message);

  /**
   * Sends a title message.
   *
   * @param title the title to send
   * @since 3.0.0
   */
  void showTitle(@NotNull Title title);

  /**
   * Checks if the player has the permission.
   *
   * @param permission the permission to check
   * @return {@code true} if the player has the permission
   * @since 3.0.0
   */
  boolean hasPermission(@NotNull String permission);

  /**
   * Gets the player's world or server.
   * Checks the world on most server types.
   * Checks the server on BungeeCord or Velocity.
   *
   * @return the world / server the player is currently in
   * @since 3.0.0
   */
  @NotNull String world();
}
