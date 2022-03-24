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

import me.treyruffy.commandblocker.bukkit.logger.Log;
import me.treyruffy.commandblocker.common.CommandBlockerValues;
import me.treyruffy.commandblocker.common.ServerTypes;
import me.treyruffy.commandblocker.common.TranslateVariables;
import me.treyruffy.commandblocker.common.logging.Logger;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Sets up the universal methods for Bukkit.
 */
public class SetupValues implements CommandBlockerValues {
    @Override
    public String getCommandBlockerVersion() {
        return CommandBlockerBukkit.get().getDescription().getVersion();
    }

    @Override
    public String getServerVersion() {
        return CommandBlockerBukkit.get().getServer().getVersion();
    }

    @Override
    public ServerTypes getServerType() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return ServerTypes.PAPER;
        } catch (final ClassNotFoundException e) {
            try {
                Class.forName("org.spigotmc.SpigotConfig");
                return ServerTypes.SPIGOT;
            } catch (final ClassNotFoundException e1) {
                return ServerTypes.BUKKIT;
            }
            // Do nothing
        }
    }

    @Override
    public Logger log() {
        return new Log();
    }

    @Override
    public void setupMetrics() {

    }

    @Override
    public void executeConsoleCommand(final String command) {
        Bukkit.dispatchCommand(CommandBlockerBukkit.get().getServer().getConsoleSender(), command);
    }

    @Override
    public void executePlayerCommand(final Object player, final String command) {
        Bukkit.dispatchCommand((Player) player, command);
    }

    @Override
    public void sendMessage(final Object commandSender, final Component message) {
        if (LegacyComponentSerializer.legacySection().serialize(message).equalsIgnoreCase(""))
            return;
        if (!(commandSender instanceof CommandSender)) {
            this.log().warn(ChatColor.RED + "Tried sending message as a non command sender.");
            return;
        }
        final CommandSender sender = (CommandSender) commandSender;
        final BukkitAudiences adventure = CommandBlockerBukkit.adventure();
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            adventure.player(player).sendMessage(message);
        } else {
            adventure.sender(sender).sendMessage(message);
        }
    }

    @Override
    public TranslateVariables translateVariables() {
        return new Variables();
    }

}
