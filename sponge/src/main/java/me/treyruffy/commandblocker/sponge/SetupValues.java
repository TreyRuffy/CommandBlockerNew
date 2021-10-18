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
package me.treyruffy.commandblocker.sponge;

import me.treyruffy.commandblocker.common.CommandBlockerValues;
import me.treyruffy.commandblocker.common.ServerTypes;
import me.treyruffy.commandblocker.common.TranslateVariables;
import me.treyruffy.commandblocker.common.logging.Logger;
import me.treyruffy.commandblocker.sponge.logger.Log;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.command.manager.CommandManager;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Sets up the universal methods for Sponge.
 */
public class SetupValues implements CommandBlockerValues {
    @Override
    public String getCommandBlockerVersion() {
        return CommandBlockerSponge.get().plugin.metadata().version().toString();
    }

    @Override
    public String getServerVersion() {
        return Sponge.game().platform().minecraftVersion().name();
    }

    @Override
    public ServerTypes getServerType() {
        return ServerTypes.SPONGE;
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
        try {
            Sponge.server().commandManager().process(command);
        } catch (final CommandException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executePlayerCommand(final Object player, final String command) {
        ((Player) player).sendMessage(Component.text("/" + command));
    }

    @Override
    public void sendMessage(final Object commandSender, final Component message) {
        if (!(commandSender instanceof CommandManager)) {
            this.log().warn("Tried sending message as a non command sender.");
            return;
        }
        final CommandManager sender = (CommandManager) commandSender;
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            player.sendMessage(message);
        }
    }

    @Override
    public TranslateVariables translateVariables() {
        return null;
    }
}
