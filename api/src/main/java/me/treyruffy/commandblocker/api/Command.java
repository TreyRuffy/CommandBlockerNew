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

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import me.treyruffy.commandblocker.api.config.Configuration;
import me.treyruffy.commandblocker.api.config.ConfigurationFiles;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The class of a blocked command in Command Blocker.
 */
public class Command {

    final Configuration.ConfigurationOptions configuration =
        Configuration.getConfiguration(ConfigurationFiles.CONFIGURATION);
    private @NotNull String[] command;
    private @NotNull String permission;
    private @NotNull List<String> messages;
    private @NotNull List<String> worlds;
    private @Nullable List<String> playerCommands;
    private @Nullable List<String> consoleCommands;
    private @Nullable List<UUID> whitelistedPlayers;
    private @Nullable String title;
    private @Nullable String subtitle;
    private @Nullable Title.Times titleTime;
    private @Nullable String actionBar;
    private boolean disableTabComplete;

    /**
     * Instantiates a new block command.
     *
     * @param command the command arguments to block
     * @param permission the permission to unblock the command
     * @param messages the messages send when the command is executed
     * @param worlds the worlds / servers the command is blocked in
     * @param playerCommands the player commands executed when the blocked command is executed
     * @param consoleCommands the console commands executed when the blocked command is executed
     * @param disableTabComplete if the command should be able to be tab completed
     * @param whitelistedPlayers the whitelisted players for the command
     * @param title the title to send the player who executed the command
     * @param subtitle the subtitle to send the player who executed the command
     * @param titleTime the time the title should show for
     * @param actionBar the action bar to send the player who executed the command
     */
    public Command(@NotNull final String[] command, @Nullable final String permission,
                   @Nullable final List<String> messages, @Nullable final List<String> worlds,
                   @Nullable final List<String> playerCommands, @Nullable final List<String> consoleCommands,
                   final boolean disableTabComplete, @Nullable final List<UUID> whitelistedPlayers,
                   @Nullable final String title, @Nullable final String subtitle,
                   @Nullable final Title.Times titleTime, @Nullable final String actionBar) {
        this.command = command;

        if (permission == null) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (final String commandArg : this.command) {
                stringBuilder.append(commandArg).append(" ");
            }
            this.permission = this.configuration.getString("Default.Permission")
                .replace("%command%", stringBuilder.toString().trim())
                .replace("%c", stringBuilder.toString().trim());
        } else {
            this.permission = permission;
        }

        if (messages == null) {
            this.messages = Collections.singletonList(this.configuration.getString("Default" +
                ".Message"));
        } else {
            this.messages = messages;
        }

        if (worlds == null || worlds.isEmpty()) {
            this.worlds = Collections.singletonList("all");
        } else {
            this.worlds = worlds;
        }

        this.playerCommands = playerCommands;
        this.consoleCommands = consoleCommands;
        this.disableTabComplete = disableTabComplete;
        this.whitelistedPlayers = whitelistedPlayers;
        this.title = title;
        this.subtitle = subtitle;
        this.titleTime = titleTime;
        this.actionBar = actionBar;
    }

    /**
     * Instantiates a new block command.
     *
     * @param command the command arguments to block
     * @param permission the permission to unblock the command
     * @param messages the messages send when the command is executed
     * @param worlds the worlds / servers the command is blocked in
     * @param playerCommands the player commands executed when the blocked command is executed
     * @param consoleCommands the console commands executed when the blocked command is executed
     * @param disableTabComplete if the command should be able to be tab completed
     */
    public Command(@NotNull final String[] command, @Nullable final String permission,
                   @Nullable final List<String> messages, @Nullable final List<String> worlds,
                   @Nullable final List<String> playerCommands, @Nullable final List<String> consoleCommands,
                   final boolean disableTabComplete) {
        this(command, permission, messages, worlds, playerCommands, consoleCommands, disableTabComplete, null, null, null, null, null);
    }

    /**
     * Instantiates a new block command.
     *
     * @param command the command arguments to block
     * @param permission the permission to unblock the command
     * @param messages the messages send when the command is executed
     */
    public Command(@NotNull final String[] command, @Nullable final String permission,
                   @Nullable final List<String> messages) {
        this(command, permission, messages, null, null, null, true);
    }

    /**
     * Instantiates a new block command.
     *
     * @param command the command name to block
     * @param permission the permission to unblock the command
     */
    @Deprecated
    public Command(@NotNull final String[] command, final @Nullable String permission) {
        this(command, permission, null);
    }

    /**
     * Instantiates a new block command.
     *
     * @param command the command name to block
     */
    @Deprecated
    public Command(@NotNull final String[] command) {
        this(command, null);
    }

    /**
     * Gets the command arguments of the blocked command.
     *
     * @return the command arguments
     */
    public @NotNull String[] command() {
        return this.command;
    }

    /**
     * Sets the command arguments of the blocked command.
     *
     * @param command the command arguments
     */
    public void command(@NotNull final String[] command) {
        this.command = command;
    }

    /**
     * Gets the permission of the blocked command.
     *
     * @return the permission
     */
    public @NotNull String permission() {
        return this.permission.replace(":", "").replace(" ", "");
    }

    /**
     * Sets the permission of the blocked command.
     *
     * @param permission the permission
     */
    public void permission(@Nullable final String permission) {
        if (permission == null) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (final String commandArg : this.command) {
                stringBuilder.append(commandArg).append(" ");
            }
            this.permission = this.configuration.getString("Default.Permission")
                .replace("%command%", stringBuilder.toString().trim())
                .replace("%c", stringBuilder.toString().trim());
        } else {
            this.permission = permission;
        }
    }

    /**
     * Gets the messages of the blocked command.
     *
     * @return the messages
     */
    public @NotNull List<String> messages() {
        return this.messages;
    }

    /**
     * Sets the messages of the blocked command.
     *
     * @param messages the messages
     */
    public void messages(@Nullable final List<String> messages) {
        if (messages == null) {
            if (this.configuration.getStringList("Default.Message").isEmpty()) {
                this.messages = Collections.singletonList(this.configuration.getString("Default.Message"));
            } else {
                this.messages = this.configuration.getStringList("Default.Message");
            }
        } else {
            this.messages = messages;
        }
    }

    /**
     * Gets the worlds / servers of the blocked command.
     * Servers for BungeeCord and Velocity.
     * Worlds for the rest.
     *
     * @return the worlds / servers
     */
    public @NotNull List<String> worlds() {
        return this.worlds;
    }

    /**
     * Sets the worlds / servers of the blocked command.
     *
     * @param worlds the worlds / servers
     */
    public void worlds(@Nullable final List<String> worlds) {
        if (worlds == null || worlds.isEmpty()) {
            this.worlds = Collections.singletonList("all");
        } else {
            this.worlds = worlds;
        }
    }

    /**
     * Gets the player commands of the blocked command.
     *
     * @return the player commands
     */
    public @Nullable List<String> playerCommands() {
        return this.playerCommands;
    }

    /**
     * Sets the player commands of the blocked command.
     *
     * @param playerCommands the player commands
     */
    public void playerCommands(@Nullable final List<String> playerCommands) {
        this.playerCommands = playerCommands;
    }

    /**
     * Gets the console commands of the blocked command.
     *
     * @return the console commands
     */
    public @Nullable List<String> consoleCommands() {
        return this.consoleCommands;
    }

    /**
     * Sets the console commands of the blocked command.
     *
     * @param consoleCommands the console commands
     */
    public void consoleCommands(@Nullable final List<String> consoleCommands) {
        this.consoleCommands = consoleCommands;
    }

    /**
     * Gets if the command can be tab autocompleted.
     *
     * @return {@code true} if the command cannot be tab autocompleted
     */
    public boolean disableTabComplete() {
        return this.disableTabComplete;
    }

    /**
     * Sets if the command can be tab autocompleted.
     *
     * @param disableTabComplete {@code true} if the command cannot be tab autocompleted
     */
    public void disableTabComplete(final boolean disableTabComplete) {
        this.disableTabComplete = disableTabComplete;
    }

    /**
     * Gets the whitelisted players of the blocked command.
     *
     * @return the UUIDs of the whitelisted players
     */
    public @Nullable List<UUID> whitelistedPlayers() {
        return this.whitelistedPlayers;
    }

    /**
     * Sets the whitelisted players of the blocked command.
     *
     * @param whitelistedPlayers the UUIDs of the whitelisted players
     */
    public void whitelistedPlayers(@Nullable final List<UUID> whitelistedPlayers) {
        this.whitelistedPlayers = whitelistedPlayers;
    }

    /**
     * Gets the title of the blocked command.
     *
     * @return the title
     */
    public @Nullable String title() {
        return this.title;
    }

    /**
     * Sets the title of the blocked command.
     *
     * @param title the title
     */
    public void title(@Nullable final String title) {
        this.title = title;
    }

    /**
     * Gets the title of the blocked command.
     *
     * @return the title
     */
    public @Nullable String subtitle() {
        return this.subtitle;
    }

    /**
     * Sets the subtitle of the blocked command.
     *
     * @param subtitle the subtitle
     */
    public void subtitle(@Nullable final String subtitle) {
        this.subtitle = subtitle;
    }

    /**
     * Gets the title time of the blocked command.
     *
     * @return the title time
     */
    public @Nullable Title.Times titleTime() {
        return this.titleTime;
    }

    /**
     * Sets the title time of the blocked command.
     *
     * @param titleTime the title time
     */
    public void titleTime(@Nullable final Title.Times titleTime) {
        this.titleTime = titleTime;
    }

    /**
     * Gets the action bar of the blocked command.
     *
     * @return the action bar
     */
    public @Nullable String actionBar() {
        return this.actionBar;
    }

    /**
     * Sets the action bar of the blocked command.
     *
     * @param actionBar the action bar
     */
    public void actionBar(@Nullable final String actionBar) {
        this.actionBar = actionBar;
    }
}
