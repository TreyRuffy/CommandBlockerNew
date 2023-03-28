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
package me.treyruffy.commandblocker.common.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * A command blocker command.
 *
 * @param <T> the sender type
 */
public abstract class CBCommand<T> {

  protected final CommandHandler<T> commandHandler;

  /**
   * Sets up a new command blocker command.
   *
   * @param commandHandler the {@link CommandHandler}
   */
  public CBCommand(final CommandHandler<T> commandHandler) {
    this.commandHandler = commandHandler;
  }

  /**
   * Register a subcommand.
   *
   * @return an argument builder.
   */
  public abstract LiteralArgumentBuilder<T> register();

  /**
   * Checks if the sender has a permission.
   *
   * @param sender the sender
   * @param permission the permission to check
   * @return {@code true} if the sender has the permission
   */
  public boolean hasPermission(final @NotNull T sender, final @NotNull String permission) {
    return this.commandHandler.hasPermission(sender, permission);
  }

  /**
   * Sends messages to the sender.
   *
   * @param sender the sender
   * @param messages the messages
   */
  public void sendMessage(final @NotNull T sender, final @NotNull TextComponent @NotNull ... messages) {
    for (final @NotNull TextComponent message : messages) {
      this.commandHandler.sendMessage(sender, message);
    }
  }

  /**
   * Gets the command handler.
   *
   * @return the {@link CommandHandler}
   */
  protected CommandHandler<T> CommandHandler() {
    return this.commandHandler;
  }
}
