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

import me.treyruffy.commandblocker.common.blocking.BlockedCommandOutput;
import me.treyruffy.commandblocker.common.blocking.CheckBlock;
import me.treyruffy.commandblocker.spongelegacy.player.CommandBlockerSpongePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.command.SendCommandEvent;

/**
 * Checks if the player runs a blocked command.
 */
public class CommandListener {

  /**
   * Checks if the command is blocked.
   *
   * @param event the pre-execute command event
   */
  @Listener(order = Order.LAST)
  public void onCommand(final @NotNull SendCommandEvent event) {
    final Player player = event.getCause().first(Player.class).orElse(null);
    if (player == null) {
      return;
    }
    final CommandBlockerSpongePlayer cbPlayer = new CommandBlockerSpongePlayer(player);
    final String command = event.getCommand() + " " + String.join(" ", event.getArguments());
    final @Nullable BlockedCommandOutput checkBlock = new CheckBlock(cbPlayer, command.split(" "))
      .blockedOutput();

    if (checkBlock != null) {
      event.setCancelled(true);
      checkBlock.sendAll();
    }
  }

}
