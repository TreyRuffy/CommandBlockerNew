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
package me.treyruffy.commandblocker.common.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import org.simpleyaml.configuration.comments.CommentType;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

/**
 * The configuration for CommandBlocker.
 */
@SuppressWarnings("checkstyle:MethodName")
public class Configuration {

    YamlFile currentConfiguration;
    File currentConfigurationLoader;

    YamlFile rootConfig;
    YamlFile rootBlocked;
    YamlFile rootOpBlocked;
    YamlFile rootMessages;

    File configLoader;
    File blockedLoader;
    File opBlockedLoader;
    File messagesLoader;

    /**
     * Gets the configuration.
     *
     * @param configurationFile the configuration file
     * @return the configuration
     */
    public ConfigurationOptions getConfiguration(final ConfigurationFiles configurationFile) {
        if (this.rootConfig == null || this.rootBlocked == null || this.rootOpBlocked == null || this.rootMessages == null) {
            new ConfigurationOptions().reload();
        }
        switch (configurationFile) {
            case CONFIGURATION:
                this.currentConfiguration = this.rootConfig;
                this.currentConfigurationLoader = this.configLoader;
                break;
            case BLOCKED:
                this.currentConfiguration = this.rootBlocked;
                this.currentConfigurationLoader = this.blockedLoader;
                break;
            case OPBLOCKED:
                this.currentConfiguration = this.rootOpBlocked;
                this.currentConfigurationLoader = this.opBlockedLoader;
                break;
            case MESSAGES:
            default:
                this.currentConfiguration = this.rootMessages;
                this.currentConfigurationLoader = this.messagesLoader;
                break;
        }
        return new ConfigurationOptions();
    }

    /**
     * Options for a configuration set.
     */
    public final class ConfigurationOptions {
        final File commandBlockerFolder = new File("plugins" + File.separator + "CommandBlocker");

        /**
         * Reloads the configuration file.
         */
        public void reload() {
            if (!this.commandBlockerFolder.exists()) this.commandBlockerFolder.mkdirs();

            if (!new File(this.commandBlockerFolder, "config.yml").exists()) {
                try {
                    Files.copy(Objects.requireNonNull(Configuration.class.getResourceAsStream("/config.yml")),
                    Paths.get(this.commandBlockerFolder + File.separator + "config.yml"));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            if (!new File(this.commandBlockerFolder, "disabled.yml").exists()) {
                try {
                    Files.copy(Objects.requireNonNull(Configuration.class.getResourceAsStream("/disabled.yml")),
                    Paths.get(this.commandBlockerFolder + File.separator + "disabled.yml"));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            if (!new File(this.commandBlockerFolder, "opblock.yml").exists()) {
                try {
                    Files.copy(Objects.requireNonNull(Configuration.class.getResourceAsStream("/opblock.yml")),
                    Paths.get(this.commandBlockerFolder + File.separator + "opblock.yml"));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            if (!new File(this.commandBlockerFolder, "messages.yml").exists()) {
                try {
                    Files.copy(Objects.requireNonNull(Configuration.class.getResourceAsStream("/messages.yml")),
                    Paths.get(this.commandBlockerFolder + File.separator + "messages.yml"));
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            Configuration.this.rootConfig = new YamlFile("plugins/CommandBlocker/config.yml");

            try {
                if (!Configuration.this.rootConfig.exists()) {
                    Configuration.this.rootConfig.createNewFile(true);
                }
                Configuration.this.rootConfig.loadWithComments();
                Configuration.this.rootConfig.options().copyDefaults(true);
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            Configuration.this.rootBlocked = new YamlFile("plugins/CommandBlocker/disabled.yml");

            try {
                if (!Configuration.this.rootBlocked.exists()) {
                    Configuration.this.rootBlocked.createNewFile(true);
                    Configuration.this.rootBlocked.options().copyDefaults(true);
                }
                Configuration.this.rootBlocked.loadWithComments();
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            Configuration.this.rootOpBlocked = new YamlFile("plugins/CommandBlocker/opblock.yml");

            try {
                if (!Configuration.this.rootOpBlocked.exists()) {
                    Configuration.this.rootOpBlocked.createNewFile(true);
                    Configuration.this.rootOpBlocked.options().copyDefaults(true);
                }
                Configuration.this.rootOpBlocked.loadWithComments();
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            Configuration.this.rootMessages = new YamlFile("plugins/CommandBlocker/messages.yml");

            try {
                if (!Configuration.this.rootMessages.exists()) {
                    Configuration.this.rootMessages.createNewFile(true);
                    Configuration.this.rootMessages.options().copyDefaults(true);
                }
                Configuration.this.rootMessages.loadWithComments();
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }

        /**
         * Gets a string from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the string from the configuration file
         */
        public String getString(final String path) {
            return Configuration.this.currentConfiguration.getString(path);
        }

        /**
         * Gets an integer from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the integer from the configuration file
         */
        public int getInt(final String path) {
            return Configuration.this.currentConfiguration.getInt(path);
        }

        /**
         * Gets a boolean from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the boolean from the configuration file
         */
        public boolean getBoolean(final String path) {
            return Configuration.this.currentConfiguration.getBoolean(path);
        }

        /**
         * Gets a string list from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the string list from the configuration file
         */
        public List<String> getStringList(final String path) {
            return Configuration.this.currentConfiguration.getStringList(path);
        }

        /**
         * Gets an object from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the object from the configuration file
         */
        public Object get(final String path) {
            return Configuration.this.currentConfiguration.get(path);
        }

        /**
         * Sets a value in the configuration file.
         *
         * @param path the path in the configuration file
         * @param object the object to set
         */
        public void set(final String path, final Object object) {
            Configuration.this.currentConfiguration.set(path, object);
        }

        /**
         * Sets a value in the configuration file with a comment.
         *
         * @param path the path in the configuration file
         * @param object the object to set
         * @param comment the comment to add
         */
        public void set(final String path, final Object object, final String comment) {
            this.set(path, object);
            Configuration.this.currentConfiguration.setComment(path, comment, CommentType.BLOCK);
        }

        /**
         * Saves the configuration file.
         */
        public void save() {
            try {
                Configuration.this.currentConfiguration.save();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

}
