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
package me.treyruffy.commandblocker.bukkit;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.treyruffy.commandblocker.common.commands.CommandHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * The Bukkit command handler.
 */
public class BukkitCommandHandler extends CommandHandler<CommandSender> implements CommandExecutor {

  private final CommandDispatcher<CommandSender> dispatcher = new CommandDispatcher<>();

  /**
   * The Bukkit command handler.
   */
  public BukkitCommandHandler() {
    this.dispatcher.register(this.commandBlockerNode);
  }

  /**
   * Gets the {@link CommandDispatcher}.
   *
   * @return the {@link CommandDispatcher}
   */
  public CommandDispatcher<CommandSender> dispatcher() {
    return this.dispatcher;
  }

  @Override
  public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label,
                           final @NotNull String[] args) {
    final String argsString = "commandblocker " + String.join(" ", args);

    try {
      this.dispatcher.execute(argsString, sender);
    } catch (final CommandSyntaxException e) {
      this.showHelp(sender);
    }
    return true;
  }

  @Override
  public void sendMessage(final @NotNull CommandSender sender, final @NotNull Component component) {
    CommandBlockerBukkit.adventure().sender(sender).sendMessage(component);
  }

  @Override
  public boolean hasPermission(final @NotNull CommandSender sender, final @NotNull String permission) {
    return sender.hasPermission(permission);
  }
}
