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
package me.treyruffy.commandblocker.bungee.listeners;

import me.treyruffy.commandblocker.bungee.player.CommandBlockerBungeePlayer;
import me.treyruffy.commandblocker.common.blocking.CommandListBlock;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.jetbrains.annotations.NotNull;

/**
 * Removes blocked commands from the tab completion list.
 */
public class CommandSendListener implements Listener {

  /**
   * Removes blocked commands from the tab completion list.
   *
   * @param event the tab complete event
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onCommandSend(final @NotNull TabCompleteEvent event) {
    final Connection sender = event.getSender();
    if (!(sender instanceof ProxiedPlayer)) {
      return;
    }
    final ProxiedPlayer player = (ProxiedPlayer) sender;
    final CommandBlockerBungeePlayer cbPlayer = new CommandBlockerBungeePlayer(player);
    final CommandListBlock commandListBlock = new CommandListBlock(cbPlayer, event.getCursor(), event.getSuggestions());
    event.getSuggestions().retainAll(commandListBlock.removeBlockedCommands());
  }

}
