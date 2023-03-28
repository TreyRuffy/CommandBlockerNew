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

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.treyruffy.commandblocker.common.commands.subcommands.ReloadCommand;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * The common command handler.
 *
 * @param <T> the sender type
 */
public abstract class CommandHandler<T> {

  protected LiteralArgumentBuilder<T> commandBlockerNode = this.basicSetup();

  @SafeVarargs
  protected CommandHandler(final LiteralArgumentBuilder<T> @NotNull ... subcommands) {
    for (final LiteralArgumentBuilder<T> subcommand : subcommands) {
      this.commandBlockerNode.then(subcommand);
    }
  }

  private LiteralArgumentBuilder<T> basicSetup() {
    return LiteralArgumentBuilder
    .<T>literal("commandblocker")
    .executes(context -> {
      final T source = context.getSource();
      this.showHelp(source);
      return Command.SINGLE_SUCCESS;
    })
    .then(new ReloadCommand<>(this).register());
  }

  /**
   * Gets the {@link LiteralArgumentBuilder}.
   *
   * @return the {@link LiteralArgumentBuilder}
   */
  public LiteralArgumentBuilder<T> commandBlockerNode() {
    return this.commandBlockerNode;
  }

  /**
   * Shows the default help menu.
   *
   * @param sender the sender
   */
  public void showHelp(final @NotNull T sender) {
    // TODO: Add translations
    this.sendMessage(sender, Component.text("/cb reload"));
  }

  /**
   * Sends a message to the sender.
   *
   * @param sender the sender
   * @param component the component message.
   */
  public abstract void sendMessage(final @NotNull T sender, final @NotNull Component component);

  /**
   * Checks if the sender has a permission.
   *
   * @param sender the sender
   * @param permission the permission
   * @return {@code true} if the sender has permission
   */
  public abstract boolean hasPermission(final @NotNull T sender, final @NotNull String permission);
}
