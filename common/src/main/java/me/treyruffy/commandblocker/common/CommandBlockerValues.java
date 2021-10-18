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
package me.treyruffy.commandblocker.common;

import me.treyruffy.commandblocker.common.logging.Logger;
import net.kyori.adventure.text.Component;

/**
 * The interface for each plugin's main methods.
 */
@SuppressWarnings("checkstyle:MethodName")
public interface CommandBlockerValues {

    /**
     * Gets the command blocker version.
     *
     * @return the command blocker version as a string.
     */
    String getCommandBlockerVersion();

    /**
     * Gets the server version.
     *
     * @return the server version as a string
     */
    String getServerVersion();

    /**
     * Gets the server type.
     *
     * @return the server type
     */
    ServerTypes getServerType();

    /**
     * The universal logger.
     *
     * @return the logger
     */
    Logger log();

    /**
     * Sets up the metrics for each server type.
     */
    void setupMetrics();

    /**
     * Executes a console command.
     *
     * @param command the command to execute
     */
    void executeConsoleCommand(String command);

    /**
     * Executes a player command.
     *
     * @param player the player to execute the command
     * @param command the command to execute
     */
    void executePlayerCommand(Object player, String command);

    /**
     * Sends a message.
     *
     * @param commandSender the sender of the message
     * @param message the message to send
     */
    void sendMessage(Object commandSender, Component message);

    /**
     * The universal variable translator.
     *
     * @return the translator
     */
    TranslateVariables translateVariables();

}
