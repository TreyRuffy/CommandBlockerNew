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
package me.treyruffy.commandblocker.spongelegacy.listeners;

import me.treyruffy.commandblocker.common.blocking.TabBlocking;
import me.treyruffy.commandblocker.spongelegacy.player.CommandBlockerSpongePlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.command.TabCompleteEvent;

/**
 * Removes the blocked commands from being tab-completed.
 */
public class TabCompleteListener {

  /**
   * Checks if the tab completion should be blocked.
   *
   * @param event the tab complete event
   */
  @Listener(order = Order.LAST)
  public void onTabComplete(final @NotNull TabCompleteEvent.Command event) {
    final Player player = event.getCause().first(Player.class).orElse(null);
    if (player == null) {
      return;
    }
    final CommandBlockerSpongePlayer cbPlayer = new CommandBlockerSpongePlayer(player);
    final String[] commandArgs = event.getRawMessage().split(" ");
    final TabBlocking tabBlocking = new TabBlocking(cbPlayer, commandArgs);
    if (tabBlocking.isTabBlocked()) {
      event.setCancelled(true);
    }
  }

}
