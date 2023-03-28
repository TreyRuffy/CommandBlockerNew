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
package me.treyruffy.commandblocker.bungee;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.treyruffy.commandblocker.common.commands.CommandHandler;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * The Bungee command handler.
 */
public class BungeeCommandHandler extends CommandHandler<CommandSender> {

  private final CommandDispatcher<CommandSender> dispatcher = new CommandDispatcher<>();

  /**
   * The Bungee command handler.
   */
  public BungeeCommandHandler() {
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

  /**
   * The command blocker command handler.
   *
   * @param sender the sender
   * @param args the arguments
   */
  public void onCommand(final CommandSender sender, final String[] args) {
    final String argsString = String.join(" ", args);

    try {
      this.dispatcher.execute(argsString, sender);
    } catch (final CommandSyntaxException e) {
      this.showHelp(sender);
    }
  }

  @Override
  public void sendMessage(final @NotNull CommandSender sender, final @NotNull Component textComponent) {
    CommandBlockerBungee.adventure().sender(sender).sendMessage(textComponent);
  }

  @Override
  public boolean hasPermission(final @NotNull CommandSender sender, final @NotNull String permission) {
    return sender.hasPermission(permission);
  }
}
