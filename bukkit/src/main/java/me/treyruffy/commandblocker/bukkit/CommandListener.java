/*
 * Copyright (C) 2015-2021 TreyRuffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.treyruffy.commandblocker.bukkit;

import me.treyruffy.commandblocker.common.blocking.BlockedCommandOutput;
import me.treyruffy.commandblocker.common.blocking.CheckBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the player runs a blocked command.
 */
public class CommandListener implements Listener {

    /**
     * Checks if the command is blocked.
     *
     * @param event the player command preprocess event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(final @NotNull PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final CommandBlockerBukkitPlayer cbPlayer = new CommandBlockerBukkitPlayer(player);
        final @Nullable BlockedCommandOutput checkBlock = new CheckBlock(cbPlayer,
            event.getMessage().substring(1).split(" ")).blockedOutput();

        if (checkBlock != null) {
            event.setCancelled(true);
            checkBlock.sendAll();
        }
    }
}
