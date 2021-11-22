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
package me.treyruffy.commandblocker.common.players;

import java.util.ArrayList;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;

/**
 * Common elements for players in the Command Blocker plugins.
 */
public interface CommandBlockerPlayers {

    /**
     * Gets the player back as the original object.
     * Will convert back to whatever platform the plugin is from.
     *
     * @return the original player object
     */
    Object player();

    /**
     * Gets the name of the player.
     *
     * @return the name
     */
    String name();

    /**
     * Gets the display name of the player.
     *
     * @return the display name
     */
    String displayName();

    /**
     * Checks if the player is an operator.
     *
     * @return {@code true} if the player is an operator
     */
    boolean isOp();

    /**
     * Gets the UUID of the player.
     *
     * @return the UUID
     */
    UUID uuid();

    /**
     * Gets the UUID of the player as a String.
     *
     * @return the String of the UUID
     */
    String uuidAsString();

    /**
     * Sends the player a message.
     *
     * @param message the message component to send
     */
    void sendMessage(Component message);

    /**
     * Performs a command as the player.
     *
     * @param command the command to perform
     */
    void performCommand(String command);

    /**
     * Performs multiple commands as the player.
     *
     * @param commands the commands to perform
     */
    void performCommand(ArrayList<String> commands);

    /**
     * Sends an action bar message.
     *
     * @param message the message component to send
     */
    void sendActionBar(Component message);

    /**
     * Sends a title message.
     *
     * @param title the title to send
     */
    void sendTitle(Title title);

    /**
     * Checks if the player has the permission.
     *
     * @param permission the permission to check
     * @return {@code true} if the player has the permission
     */
    boolean hasPermission(String permission);

    /**
     * Gets the player's world or server.
     * Checks the world on most server types.
     * Checks the server on BungeeCord or Velocity.
     *
     * @return the world / server the player is currently in
     */
    String world();
}
