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

import java.util.List;
import me.treyruffy.commandblocker.api.Command;
import me.treyruffy.commandblocker.api.CommandBlocker;
import me.treyruffy.commandblocker.api.CommandBlockerTypes;
import me.treyruffy.commandblocker.common.players.CommandBlockerPlayers;
import org.jetbrains.annotations.NotNull;

/**
 * Checks if tabbed commands should be blocked.
 */
public class TabBlocking {

    private final List<Command> commandList;
    private final CommandBlockerPlayers player;
    private final String[] commandArguments;

    TabBlocking(final @NotNull CommandBlockerPlayers player, final String[] commandArguments) {
        this.player = player;
        this.commandArguments = commandArguments;
        if (player.isOp()) {
            this.commandList = CommandBlocker.commandsBlocked(CommandBlockerTypes.OPERATORS);
        } else {
            this.commandList = CommandBlocker.commandsBlocked(CommandBlockerTypes.REGULAR_PLAYERS);
        }
    }

    // TODO check if it works
    /**
     * Checks if the command should be blocked.
     *
     * @return true if the command should be blocked, false otherwise
     */
    public boolean isTabBlocked() {
        if (this.commandList.size() == 0) {
            return false;
        }

        for (final Command command : this.commandList) {
            if (!command.command()[0].toLowerCase().trim().startsWith(this.commandArguments[0].toLowerCase().trim())) {
                continue;
            }
            if (command.disableTabComplete()) {
                final StringBuilder commandStringBuilder = new StringBuilder();
                for (int i = 0; i < command.command().length; i++) {
                    commandStringBuilder.append(command.command()[i]);
                }
                final StringBuilder incompleteStringBuilder = new StringBuilder();
                for (final String commandArgument : this.commandArguments) {
                    incompleteStringBuilder.append(commandArgument);
                }
                if (startsWithIgnoreCase(commandStringBuilder.toString(), incompleteStringBuilder.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean startsWithIgnoreCase(final String string, final String incompleteString) {
        if (string.length() < incompleteString.length()) {
            return false;
        }
        return string.regionMatches(true, 0, incompleteString, 0, incompleteString.length());
    }
}
