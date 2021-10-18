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
import net.kyori.adventure.text.Component;

/**
 * The type Command.
 */
public class Command {

    private String command;
    private String permission;
    private Component message;
    private List<String> worlds;
    private List<String> playerCommands;
    private List<String> consoleCommands;

    /**
     * Instantiates a new Command.
     *
     * @param command         the command
     * @param permission      the permission
     * @param message         the message
     * @param worlds          the worlds
     * @param playerCommands  the player commands
     * @param consoleCommands the console commands
     */
    public Command(final String command, final String permission, final Component message, final List<String> worlds,
                   final List<String> playerCommands, final List<String> consoleCommands) {
        this.command = command;
        this.permission = permission;
        this.message = message;
        this.worlds = worlds;
        this.playerCommands = playerCommands;
        this.consoleCommands = consoleCommands;
    }

    /**
     * Instantiates a new Command.
     *
     * @param command    the command
     * @param permission the permission
     * @param message    the message
     */
    public Command(final String command, final String permission, final Component message) {
        this.command = command;
        this.permission = permission;
        this.message = message;
    }

    /**
     * Instantiates a new Command.
     *
     * @param command    the command
     * @param permission the permission
     */
    @Deprecated
    public Command(final String command, final String permission) {
        this.command = command;
        this.permission = permission;
    }

    /**
     * Instantiates a new Command.
     *
     * @param command the command
     */
    @Deprecated
    public Command(final String command) {
        this.command = command;
    }

    /**
     * Instantiates a new Command.
     */
    @Deprecated
    public Command() {
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String command() {
        return this.command;
    }

    /**
     * Sets command.
     *
     * @param command the command
     */
    public void command(final String command) {
        this.command = command;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    public String permission() {
        return this.permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     */
    public void permission(final String permission) {
        this.permission = permission;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public Component message() {
        return this.message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void message(final Component message) {
        this.message = message;
    }

    /**
     * Gets worlds.
     *
     * @return the worlds
     */
    public List<String> worlds() {
        return this.worlds;
    }

    /**
     * Sets worlds.
     *
     * @param worlds the worlds
     */
    public void worlds(final List<String> worlds) {
        this.worlds = worlds;
    }

    /**
     * Gets player commands.
     *
     * @return the player commands
     */
    public List<String> playerCommands() {
        return this.playerCommands;
    }

    /**
     * Sets player commands.
     *
     * @param playerCommands the player commands
     */
    public void playerCommands(final List<String> playerCommands) {
        this.playerCommands = playerCommands;
    }

    /**
     * Gets console commands.
     *
     * @return the console commands
     */
    public List<String> consoleCommands() {
        return this.consoleCommands;
    }

    /**
     * Sets console commands.
     *
     * @param consoleCommands the console commands
     */
    public void consoleCommands(final List<String> consoleCommands) {
        this.consoleCommands = consoleCommands;
    }

}
