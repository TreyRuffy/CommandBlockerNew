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
package me.treyruffy.commandblocker.bukkit.listeners;

import me.treyruffy.commandblocker.bukkit.player.CommandBlockerBukkitPlayer;
import me.treyruffy.commandblocker.common.blocking.CommandListBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Removes blocked commands from the tab completion list.
 */
public class CommandSendListener implements Listener {

  /**
   * Removes blocked commands from the tab completion list.
   *
   * @param event the player command send event
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onCommandSend(final @NotNull PlayerCommandSendEvent event) {
    final Player player = event.getPlayer();
    final CommandBlockerBukkitPlayer cbPlayer = new CommandBlockerBukkitPlayer(player);
    final CommandListBlock commandListBlock = new CommandListBlock(cbPlayer, event.getCommands());
    event.getCommands().retainAll(commandListBlock.removeBlockedCommands());
  }

}
