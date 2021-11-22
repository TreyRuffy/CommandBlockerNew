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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import me.treyruffy.commandblocker.api.config.Configuration;
import me.treyruffy.commandblocker.api.config.ConfigurationFiles;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.simpleyaml.configuration.ConfigurationSection;

/**
 * The main Command Blocker API class.
 */
public final class CommandBlocker {

    private static List<Command> normalCommandsBlocked;
    private static List<Command> operatorCommandsBlocked;

    private CommandBlocker() {
        // Disable initialization
    }

    /**
     * Resets the cached lists back to the configuration list.
     */
    public static void resetCachedLists() {
        normalCommandsBlocked = new ArrayList<>();
        operatorCommandsBlocked = new ArrayList<>();
        configToCache(CommandBlockerTypes.REGULAR_PLAYERS);
        configToCache(CommandBlockerTypes.OPERATORS);
    }

    private static void configToCache(final CommandBlockerTypes commandBlockerType) {
        final String sectionName;
        final Configuration.ConfigurationOptions configuration;
        if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
            sectionName = "DisabledCommands";
            configuration = Configuration.getConfiguration(ConfigurationFiles.BLOCKED);
        } else if (commandBlockerType == CommandBlockerTypes.OPERATORS) {
            sectionName = "DisabledOpCommands";
            configuration = Configuration.getConfiguration(ConfigurationFiles.OPBLOCKED);
        } else {
            return;
        }

        final ConfigurationSection configSection = configuration.getConfigurationSection(sectionName);
        if (configSection == null) {
            return;
        }

        for (final String command : configSection.getKeys(false)) {
            final Command fullCommand;
            if (configSection.isConfigurationSection(command)) {
                final ConfigurationSection commandConfigSection = configSection.getConfigurationSection(command);
                final String permission = commandConfigSection.getString("Permission");
                final List<String> message;
                if (!commandConfigSection.getStringList("Message").isEmpty()) {
                    message = commandConfigSection.getStringList("Message");
                } else if (commandConfigSection.getString("Message") != null) {
                    message = Collections.singletonList(commandConfigSection.getString("Message"));
                } else {
                    message = null;
                }
                final List<String> worlds = commandConfigSection.getStringList("Worlds");
                final List<String> playerCommands = commandConfigSection.getStringList("PlayerCommands");
                final List<String> consoleCommands = commandConfigSection.getStringList("ConsoleCommands");
                final List<UUID> whitelistedPlayers = new ArrayList<>();
                for (final String whitelistedPlayer : commandConfigSection.getStringList("WhitelistedPlayers")) {
                    whitelistedPlayers.add(UUID.fromString(whitelistedPlayer));
                }

                final String title;
                final String subtitle;
                final Title.Times titleTime;
                if (commandConfigSection.isConfigurationSection("Title")) {
                    final ConfigurationSection titleSection = commandConfigSection.getConfigurationSection("Title");
                    title = titleSection.getString("Title");
                    subtitle = titleSection.getString("Subtitle");
                    final Duration fadeIn = Duration.ofSeconds(titleSection.getLong("FadeIn"));
                    final Duration stay = Duration.ofSeconds(titleSection.getLong("Stay"));
                    final Duration fadeOut = Duration.ofSeconds(titleSection.getLong("FadeOut"));
                    titleTime = Title.Times.of(fadeIn, stay, fadeOut);
                } else {
                    title = null;
                    subtitle = null;
                    titleTime = null;
                }

                final String actionBar = commandConfigSection.getString("ActionBar");

                fullCommand = new Command(command.split(" "), permission, message, worlds, playerCommands,
                    consoleCommands, whitelistedPlayers, title, subtitle, titleTime, actionBar);
            } else {
                fullCommand = new Command(command.split(" "), null, null);
            }

            if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
                normalCommandsBlocked.add(fullCommand);
            } else {
                operatorCommandsBlocked.add(fullCommand);
            }
        }
    }

    /**
     * Gets a list of the cached blocked commands.
     *
     * @param commandBlockerType the file to check commands from
     * @return the list of blocked commands
     */
    @Contract(pure = true)
    public static @Nullable List<Command> commandsBlocked(final CommandBlockerTypes commandBlockerType) {
        if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
            return normalCommandsBlocked;
        } else if (commandBlockerType == CommandBlockerTypes.OPERATORS) {
            return operatorCommandsBlocked;
        } else {
            return null;
        }
    }

    /**
     * <b>The recommended way to add a command!</b>
     * Adds a new command config entry.
     * It will be added to the cached list on immediately.
     * It will also be added to the config file immediately.
     *
     * @param commandBlockerType the file to add the command to
     * @param command the command to add
     */
    public static void addBlockedCommand(final CommandBlockerTypes commandBlockerType, final Command command) {
        addBlockedCommandToConfig(commandBlockerType, command, true);
        addBlockedCommandToList(commandBlockerType, command, true);
    }

    /**
     * <b>The recommended way to remove a command!</b>
     * Removes a command config entry.
     * It will be removed from the cached list on immediately.
     * It will also be removed to the config file immediately.
     *
     * @param commandBlockerType the file to remove the command from
     * @param command the command to remove
     */
    public static void removeBlockedCommand(final CommandBlockerTypes commandBlockerType, final Command command) {
        removeBlockedCommandFromList(commandBlockerType, command);
        removeBlockedCommandFromConfig(commandBlockerType, command);
    }

    /**
     * Adds a command to the cached list.
     * <b>Will not add the command to the config list!</b>
     *
     * @param commandBlockerType the list to add the command to
     * @param command the command to add
     * @param combine {@code true} if the command should be combined with an existing command, if there is one
     */
    public static void addBlockedCommandToList(final CommandBlockerTypes commandBlockerType, final Command command,
                                               final boolean combine) {
        Command overriddenCommand = null;
        for (final Command listedCommand : commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS ?
            normalCommandsBlocked : operatorCommandsBlocked) {
            if (Arrays.equals(listedCommand.command(), command.command())) {
                overriddenCommand = listedCommand;
                break;
            }
        }

        if (overriddenCommand != null && combine) {

            combineCommands(command, overriddenCommand);

            if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
                normalCommandsBlocked.remove(overriddenCommand);
            } else if (commandBlockerType == CommandBlockerTypes.OPERATORS) {
                operatorCommandsBlocked.remove(overriddenCommand);
            }
        }
        if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
            normalCommandsBlocked.add(command);
        } else if (commandBlockerType == CommandBlockerTypes.OPERATORS) {
            operatorCommandsBlocked.add(command);
        }
    }

    private static void combineCommands(final @NotNull Command command, final Command overriddenCommand) {
        if (command.permission().equals(defaultPermission(command.command()))) {
            command.permission(overriddenCommand.permission());
        }
        if (command.messages() == defaultMessage()) {
            command.messages(overriddenCommand.messages());
        }
        if (command.worlds().equals(Collections.singletonList("all"))) {
            command.worlds(overriddenCommand.worlds());
        }
        if (command.playerCommands() == null) {
            command.playerCommands(overriddenCommand.playerCommands());
        }
        if (command.consoleCommands() == null) {
            command.consoleCommands(overriddenCommand.consoleCommands());
        }
        if (command.whitelistedPlayers() == null) {
            command.whitelistedPlayers(overriddenCommand.whitelistedPlayers());
        }
        if (command.title() == null) {
            command.title(overriddenCommand.title());
        }
        if (command.subtitle() == null) {
            command.subtitle(overriddenCommand.subtitle());
        }
        if (command.titleTime() == null) {
            command.titleTime(overriddenCommand.titleTime());
        }
        if (command.actionBar() == null) {
            command.actionBar(overriddenCommand.actionBar());
        }
    }

    private static @NotNull String defaultPermission(final String @NotNull [] commandArgs) {
        final Configuration.ConfigurationOptions configuration = Configuration.getConfiguration(ConfigurationFiles.CONFIGURATION);
        final StringBuilder stringBuilder = new StringBuilder();
        for (final String commandArg : commandArgs) {
            stringBuilder.append(commandArg).append(" ");
        }
        return configuration.getString("Default.Permission")
            .replace("%command%", stringBuilder.toString().trim())
            .replace("%c", stringBuilder.toString().trim());
    }

    private static @NotNull List<String> defaultMessage() {
        final Configuration.ConfigurationOptions configuration = Configuration.getConfiguration(ConfigurationFiles.CONFIGURATION);
        final List<String> defaultMessage;
        if (configuration.getStringList("Default.Message").isEmpty()) {
            defaultMessage = Collections.singletonList(configuration.getString("Default.Message"));
        } else {
            defaultMessage = configuration.getStringList("Default.Message");
        }
        return defaultMessage;
    }

    /**
     * Removes a command from the cached list.
     * <b>Will not remove the command from the config list!</b>
     *
     * @param commandBlockerType the list to remove the command from
     * @param command the command to remove
     */
    public static void removeBlockedCommandFromList(final CommandBlockerTypes commandBlockerType,
                                                   final Command command) {
        if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
            normalCommandsBlocked.remove(command);
        } else if (commandBlockerType == CommandBlockerTypes.OPERATORS) {
            operatorCommandsBlocked.remove(command);
        }
    }

    /**
     * Adds a new command config entry.
     * <b>It will only be added to the cached list on reload!</b>
     *
     * @param commandBlockerType the file to add the command to
     * @param command the command to add
     * @param combine {@code true} if the command should be combined with an existing command, if there is one
     */
    public static void addBlockedCommandToConfig(final CommandBlockerTypes commandBlockerType, final Command command,
                                                 final boolean combine) {
        Command overriddenCommand = null;
        for (final Command listedCommand : commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS ?
            normalCommandsBlocked : operatorCommandsBlocked) {
            if (Arrays.equals(listedCommand.command(), command.command())) {
                overriddenCommand = listedCommand;
                break;
            }
        }
        if (overriddenCommand != null && combine) {
            combineCommands(command, overriddenCommand);
        }

        final String sectionName;
        final Configuration.ConfigurationOptions configuration;
        if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
            sectionName = "DisabledCommands";
            configuration = Configuration.getConfiguration(ConfigurationFiles.BLOCKED);
        } else {
            sectionName = "DisabledOpCommands";
            configuration = Configuration.getConfiguration(ConfigurationFiles.OPBLOCKED);
        }

        final ConfigurationSection configSection = configuration.getConfigurationSection(sectionName);
        final StringBuilder commandConfigName = new StringBuilder();
        for (final String commandPart : command.command()) {
            commandConfigName.append(commandPart).append(" ");
        }
        final ConfigurationSection commandConfigSection =
            configSection.createSection(commandConfigName.toString().trim());

        commandConfigSection.set("Message", command.messages());
        commandConfigSection.set("Permission", command.permission());
        commandConfigSection.set("Worlds", command.worlds());
        commandConfigSection.set("PlayerCommands", command.playerCommands());
        commandConfigSection.set("ConsoleCommands", command.consoleCommands());
        final List<UUID> oldWhitelistedPlayers = command.whitelistedPlayers();
        final List<String> whitelistedPlayers;
        if (oldWhitelistedPlayers != null) {
            whitelistedPlayers = new ArrayList<>();
            for (final UUID whitelistedPlayer : oldWhitelistedPlayers) {
                whitelistedPlayers.add(whitelistedPlayer.toString());
            }
            commandConfigSection.set("WhitelistedPlayers", whitelistedPlayers);
        }
        final ConfigurationSection titleSection = commandConfigSection.createSection("Title");
        titleSection.set("Title", command.title());
        titleSection.set("Subtitle", command.subtitle());
        if (command.titleTime() != null) {
            titleSection.set("FadeIn", command.titleTime().fadeIn().getSeconds());
            titleSection.set("Stay", command.titleTime().stay().getSeconds());
            titleSection.set("FadeOut", command.titleTime().fadeOut().getSeconds());
        }

        commandConfigSection.set("ActionBar", command.actionBar());
        configuration.save();
    }

    /**
     * Removes a command config entry.
     * <b>It will only be removed from the cached list on reload!</b>
     *
     * @param commandBlockerType the file to remove the command from
     * @param command the command to remove
     */
    public static void removeBlockedCommandFromConfig(final CommandBlockerTypes commandBlockerType,
                                                      final Command command) {
        final Configuration.ConfigurationOptions configuration;
        final String sectionName;
        if (commandBlockerType == CommandBlockerTypes.REGULAR_PLAYERS) {
            sectionName = "DisabledCommands";
            configuration = Configuration.getConfiguration(ConfigurationFiles.BLOCKED);
        } else {
            sectionName = "DisabledOpCommands";
            configuration = Configuration.getConfiguration(ConfigurationFiles.OPBLOCKED);
        }
        final StringBuilder commandConfigName = new StringBuilder();
        for (final String commandPart : command.command()) {
            commandConfigName.append(commandPart).append(" ");
        }
        final ConfigurationSection configSection = configuration.getConfigurationSection(sectionName);
        configSection.set(commandConfigName.toString(), null);
        configuration.save();
    }

}
