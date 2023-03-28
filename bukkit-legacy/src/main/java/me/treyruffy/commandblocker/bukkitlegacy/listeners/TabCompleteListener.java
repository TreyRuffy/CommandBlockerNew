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
package me.treyruffy.commandblocker.bukkitlegacy.listeners;

import me.treyruffy.commandblocker.bukkitlegacy.player.CommandBlockerBukkitPlayer;
import me.treyruffy.commandblocker.common.blocking.TabBlocking;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Removes the blocked commands from being tab-completed.
 */
public class TabCompleteListener implements Listener {

  /**
   * Checks if the tab completion should be blocked.
   *
   * @param event the tab complete event
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onTabComplete(final @NotNull TabCompleteEvent event) {
    final Player player = event.getSender() instanceof Player ? (Player) event.getSender() : null;
    if (player == null) {
      return;
    }
    final CommandBlockerBukkitPlayer cbPlayer = new CommandBlockerBukkitPlayer(player);
    final TabBlocking tabBlocking = new TabBlocking(cbPlayer, event.getBuffer().split(" "));
    if (tabBlocking.isTabBlocked()) {
      event.setCancelled(true);
    }
  }

}
