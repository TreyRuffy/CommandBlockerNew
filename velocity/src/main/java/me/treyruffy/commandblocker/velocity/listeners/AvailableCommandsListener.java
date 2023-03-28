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
package me.treyruffy.commandblocker.velocity.listeners;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.PlayerAvailableCommandsEvent;
import com.velocitypowered.api.proxy.Player;
import me.treyruffy.commandblocker.common.blocking.NodeCommandListBlock;
import me.treyruffy.commandblocker.velocity.player.CommandBlockerVelocityPlayer;

/**
 * Removes blocked commands from the tab completion list.
 */
public class AvailableCommandsListener {

  /**
   * Removes blocked commands from the tab completion list.
   *
   * @param event the player available commands event
   */
  //TODO: Setup root command check
  @SuppressWarnings("UnstableApiUsage")
  @Subscribe(order = PostOrder.FIRST)
  public void onCommandSend(final PlayerAvailableCommandsEvent event) {
    final Player player = event.getPlayer();
    final CommandBlockerVelocityPlayer cbPlayer = new CommandBlockerVelocityPlayer(player);

    new NodeCommandListBlock(cbPlayer, event.getRootNode()).removeBlockedCommands();
  }

}
