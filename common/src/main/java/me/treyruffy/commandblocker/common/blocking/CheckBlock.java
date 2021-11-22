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
package me.treyruffy.commandblocker.common.blocking;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.treyruffy.commandblocker.api.Command;
import me.treyruffy.commandblocker.api.CommandBlocker;
import me.treyruffy.commandblocker.api.CommandBlockerTypes;
import me.treyruffy.commandblocker.common.players.CommandBlockerPlayers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the command should be blocked.
 */
public class CheckBlock {

    private final List<Command> commandList;
    private final CommandBlockerPlayers player;
    private final String[] commandArguments;
    private Command blockedCommand;

    /**
     * Creates a new object to check commands.
     *
     * @param player the player who executed the command
     * @param commandArguments the command arguments
     */
    public CheckBlock(final @NotNull CommandBlockerPlayers player, final String[] commandArguments) {
        this.player = player;
        this.commandArguments = commandArguments;
        if (player.isOp()) {
            this.commandList = CommandBlocker.commandsBlocked(CommandBlockerTypes.OPERATORS);
        } else {
            this.commandList = CommandBlocker.commandsBlocked(CommandBlockerTypes.REGULAR_PLAYERS);
        }
    }

    /**
     * Checks if the executed command should be blocked.
     *
     * @return {@code true} if the command should be blocked
     */
    private boolean isBlocked() {
        // Checks if the list of blocked commands has any entries
        if (this.commandList.size() == 0) {
            return false;
        }

        // Check through the list of blocked commands
        for (final Command command : this.commandList) {
            // Variable to hold the name of a blocked command
            final String[] testingCommandArguments = command.command();

            // Check if the first String in the blocked command and the executed command are the same
            if (testingCommandArguments[0].equalsIgnoreCase(this.commandArguments[0])) {

                // Check if the arguments in both the executed command and the blocked command are the same
                boolean argumentMatches = true;
                if (testingCommandArguments.length > this.commandArguments.length) {
                    argumentMatches = false;
                } else {
                    for (int i = 0; i < testingCommandArguments.length; i++) {
                        if (!testingCommandArguments[i].equalsIgnoreCase(this.commandArguments[i])) {
                            argumentMatches = false;
                            break;
                        }
                    }
                }

                // If the commands match, block the executed command
                if (argumentMatches) {
                    this.blockedCommand = command;
                    break;
                }
            }
        }

        // If the list did not come up with a command, do not block the executed command
        if (this.blockedCommand == null) {
            return false;
        }

        // If the player has the permission, do not block the executed command
        final String commandPermission = this.blockedCommand.permission();
        if (!this.player.isOp() && this.player.hasPermission(commandPermission)) {
            return false;
        }

        // Checks if the player is in a world where the command is blocked
        boolean blockedWorld = false;
        final String currentWorld = this.player.world();
        for (final String world : this.blockedCommand.worlds()) {
            if (currentWorld.equalsIgnoreCase(world) || world.equalsIgnoreCase("all")) {
                blockedWorld = true;
                break;
            }
        }
        // If the player was not in a blocked world, return false
        if (!blockedWorld) {
            return false;
        }

        // Check if the player is whitelisted to execute the command
        final List<UUID> whitelistedPlayers = this.blockedCommand.whitelistedPlayers();
        if (whitelistedPlayers != null) {
            return !whitelistedPlayers.contains(this.player.uuid());
        }
        return true;
    }

    /**
     * Checks if the executed command should be blocked.
     *
     * @param placeholders any placeholders for the message, title, actionbar, etc
     * @return null if the command is not blocked or the command block output
     */
    public @Nullable BlockedCommandOutput blockedOutput(@Nullable final HashMap<String, String> placeholders) {
        if (this.isBlocked()) {
            return new BlockedCommandOutput(this.player, this.blockedCommand, placeholders);
        } else {
            return null;
        }
    }

    /**
     * Checks if the executed command should be blocked.
     *
     * @return null if the command is not blocked or the command block output
     */
    public @Nullable BlockedCommandOutput blockedOutput() {
        return this.blockedOutput(null);
    }

}
