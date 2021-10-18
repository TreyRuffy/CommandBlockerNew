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
package me.treyruffy.commandblocker.bungee;

import me.treyruffy.commandblocker.bungee.logger.Log;
import me.treyruffy.commandblocker.common.CommandBlockerValues;
import me.treyruffy.commandblocker.common.ServerTypes;
import me.treyruffy.commandblocker.common.TranslateVariables;
import me.treyruffy.commandblocker.common.logging.Logger;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Sets up the universal methods for Bungee.
 */
public class SetupValues implements CommandBlockerValues {

    @Override
    public String getCommandBlockerVersion() {
        return CommandBlockerBungee.get().getDescription().getVersion();
    }

    @Override
    public String getServerVersion() {
        return CommandBlockerBungee.get().getProxy().getVersion();
    }

    @Override
    public ServerTypes getServerType() {
        return ServerTypes.BUNGEECORD;
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
        CommandBlockerBungee.get().getProxy().getPluginManager().dispatchCommand(CommandBlockerBungee.get().getProxy().getConsole(), command);
    }

    @Override
    public void executePlayerCommand(final Object player, final String command) {
        CommandBlockerBungee.get().getProxy().getPluginManager().dispatchCommand((ProxiedPlayer) player, command);
    }

    @Override
    public void sendMessage(final Object commandSender, final Component message) {
        if (!(commandSender instanceof CommandSender)) {
            this.log().warn(ChatColor.RED + "Tried sending message as a non command sender.");
            return;
        }
        final CommandSender sender = (CommandSender) commandSender;
        final BungeeAudiences adventure = CommandBlockerBungee.adventure();
        if (sender instanceof ProxiedPlayer) {
            final ProxiedPlayer player = (ProxiedPlayer) sender;
            adventure.player(player).sendMessage(message);
        } else {
            adventure.sender(sender).sendMessage(message);
        }
    }

    @Override
    public TranslateVariables translateVariables() {
        return null;
    }
}
