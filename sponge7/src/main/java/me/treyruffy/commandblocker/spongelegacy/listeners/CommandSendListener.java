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

import me.treyruffy.commandblocker.common.blocking.CommandListBlock;
import me.treyruffy.commandblocker.spongelegacy.player.CommandBlockerSpongePlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.command.TabCompleteEvent;

/**
 * Removes blocked commands from the tab completion list.
 */
public class CommandSendListener {

  /**
   * Removes blocked commands from the tab completion list.
   *
   * @param event the tab complete event
   */
  @Listener(order = Order.LAST)
  public void onCommandSend(final TabCompleteEvent.@NotNull Command event) {
    final Player player = event.getCause().first(Player.class).orElse(null);
    if (player == null) {
      return;
    }
    final CommandBlockerSpongePlayer cbPlayer = new CommandBlockerSpongePlayer(player);
    final CommandListBlock commandListBlock = new CommandListBlock(cbPlayer, event.getRawMessage(),
      event.getOriginalTabCompletions());
    event.getTabCompletions().retainAll(commandListBlock.removeBlockedCommands());
  }

}
