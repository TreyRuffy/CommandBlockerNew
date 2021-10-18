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
package me.treyruffy.commandblocker.api;

import java.util.List;

/**
 * Gets the CommandBlocker API.
 */
public class CommandBlocker {

    /**
     * Gets a list of the blocked commands.
     *
     * @param commandBlockerType the file to check commands from
     * @return the list of blocked commands
     */
    public List<Command> commandsBlocked(final CommandBlockerTypes commandBlockerType) {
        return null;
    }

    /**
     * Adds a command to the block list.
     *
     * @param commandBlockerType the file to add the command to
     * @param command the command to add
     * @return true if command was successfully added to the block list
     */
    public boolean addBlockedCommand(final CommandBlockerTypes commandBlockerType, final Command command) {
        return false;
    }
}
