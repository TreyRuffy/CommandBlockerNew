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

import java.util.ArrayList;
import java.util.UUID;
import me.treyruffy.commandblocker.common.players.CommandBlockerPlayers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

/**
 * The player object for Command Blocker bukkit.
 */
public class CommandBlockerBukkitPlayer implements CommandBlockerPlayers {

    private final Player player;

    /**
     * Sets up the player for Command Blocker.
     *
     * @param player the player to set up
     */
    public CommandBlockerBukkitPlayer(final Player player) {
        this.player = player;
    }

    @Override
    public Object player() {
        return this.player;
    }

    @Override
    public String name() {
        return this.player.getName();
    }

    @SuppressWarnings("deprecation")
    @Override
    public String displayName() {
        return this.player.getDisplayName();
    }

    @Override
    public boolean isOp() {
        return this.player.isOp();
    }

    @Override
    public UUID uuid() {
        return this.player.getUniqueId();
    }

    @Override
    public String uuidAsString() {
        return this.player.getUniqueId().toString();
    }

    @Override
    public void sendMessage(final Component message) {
        CommandBlockerBukkit.adventure().player(this.player).sendMessage(message);
    }

    @Override
    public void performCommand(final String command) {
        this.player.performCommand(command);
    }

    @Override
    public void performCommand(final ArrayList<String> commands) {
        commands.stream().iterator().forEachRemaining(this.player::performCommand);
    }

    @Override
    public void sendActionBar(final Component message) {
        CommandBlockerBukkit.adventure().player(this.player).sendActionBar(message);
    }

    @Override
    public void sendTitle(final Title title) {
        CommandBlockerBukkit.adventure().player(this.player).showTitle(title);
    }

    @Override
    public boolean hasPermission(final String permission) {
        return this.player.hasPermission(permission);
    }

    @Override
    public String world() {
        return this.player.getWorld().getName();
    }
}
