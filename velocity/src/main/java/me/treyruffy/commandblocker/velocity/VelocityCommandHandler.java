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
package me.treyruffy.commandblocker.velocity;

import com.mojang.brigadier.CommandDispatcher;
import com.velocitypowered.api.command.CommandSource;
import me.treyruffy.commandblocker.common.commands.CommandHandler;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

/**
 * The Velocity command handler.
 */
public class VelocityCommandHandler extends CommandHandler<CommandSource> {

  private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

  /**
   * The Velocity command handler.
   */
  public VelocityCommandHandler() {
    this.dispatcher.register(this.commandBlockerNode);
  }

  /**
   * Gets the {@link CommandDispatcher}.
   *
   * @return the {@link CommandDispatcher}
   */
  public CommandDispatcher<CommandSource> dispatcher() {
    return this.dispatcher;
  }

  @Override
  public void sendMessage(final @NotNull CommandSource sender, final @NotNull Component component) {
    sender.sendMessage(component);
  }

  @Override
  public boolean hasPermission(final @NotNull CommandSource sender, final @NotNull String permission) {
    return sender.hasPermission(permission);
  }
}
