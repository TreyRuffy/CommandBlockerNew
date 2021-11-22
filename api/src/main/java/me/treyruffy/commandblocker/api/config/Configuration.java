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
package me.treyruffy.commandblocker.api.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import me.treyruffy.commandblocker.api.CommandBlocker;
import org.simpleyaml.configuration.ConfigurationSection;
import org.simpleyaml.configuration.comments.CommentType;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

/**
 * The configuration for CommandBlocker.
 */
@SuppressWarnings("checkstyle:MethodName")
public final class Configuration {

    private Configuration() {
        // Do nothing
    }

    static YamlFile currentConfiguration;
    static File currentConfigurationLoader;

    static YamlFile rootConfig;
    static YamlFile rootBlocked;
    static YamlFile rootOpBlocked;
    static YamlFile rootMessages;

    static File configLoader;
    static File blockedLoader;
    static File opBlockedLoader;
    static File messagesLoader;

    /**
     * Gets the configuration.
     *
     * @param configurationFile the configuration file
     * @return the configuration
     */
    public static ConfigurationOptions getConfiguration(final ConfigurationFiles configurationFile) {
        if (rootConfig == null || rootBlocked == null || rootOpBlocked == null || rootMessages == null) {
            try {
                new ConfigurationOptions().reload();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        switch (configurationFile) {
            case CONFIGURATION:
                currentConfiguration = rootConfig;
                currentConfigurationLoader = configLoader;
                break;
            case BLOCKED:
                currentConfiguration = rootBlocked;
                currentConfigurationLoader = blockedLoader;
                break;
            case OPBLOCKED:
                currentConfiguration = rootOpBlocked;
                currentConfigurationLoader = opBlockedLoader;
                break;
            case MESSAGES:
            default:
                currentConfiguration = rootMessages;
                currentConfigurationLoader = messagesLoader;
                break;
        }
        return new ConfigurationOptions();
    }

    /**
     * Options for a configuration set.
     */
    public static final class ConfigurationOptions {
        final File commandBlockerFolder = new File("plugins" + File.separator + "CommandBlocker");

        /**
         * Gets the folder where the configuration files are.
         *
         * @return the folder which holds the configuration files
         */
        public File commandBlockerFolder() {
            return this.commandBlockerFolder;
        }

        /**
         * Reloads the configuration file.
         */
        public void reload() throws IOException {
            if (!this.commandBlockerFolder.exists())
                if (!this.commandBlockerFolder.mkdirs()) {
                    throw new IOException("Directory not created");
                }
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

            Configuration.rootConfig = new YamlFile("plugins/CommandBlocker/config.yml");

            try {
                if (!Configuration.rootConfig.exists()) {
                    Configuration.rootConfig.createNewFile(true);
                }
                Configuration.rootConfig.loadWithComments();
                Configuration.rootConfig.options().copyDefaults(true);
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            Configuration.rootBlocked = new YamlFile("plugins/CommandBlocker/disabled.yml");

            try {
                if (!Configuration.rootBlocked.exists()) {
                    Configuration.rootBlocked.createNewFile(true);
                    Configuration.rootBlocked.options().copyDefaults(true);
                }
                Configuration.rootBlocked.loadWithComments();
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            Configuration.rootOpBlocked = new YamlFile("plugins/CommandBlocker/opblock.yml");

            try {
                if (!Configuration.rootOpBlocked.exists()) {
                    Configuration.rootOpBlocked.createNewFile(true);
                    Configuration.rootOpBlocked.options().copyDefaults(true);
                }
                Configuration.rootOpBlocked.loadWithComments();
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            Configuration.rootMessages = new YamlFile("plugins/CommandBlocker/messages.yml");

            try {
                if (!Configuration.rootMessages.exists()) {
                    Configuration.rootMessages.createNewFile(true);
                    Configuration.rootMessages.options().copyDefaults(true);
                }
                Configuration.rootMessages.loadWithComments();
            } catch (final IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }

            CommandBlocker.resetCachedLists();
        }

        /**
         * Gets a string from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the string from the configuration file
         */
        public String getString(final String path) {
            return Configuration.currentConfiguration.getString(path);
        }

        /**
         * Gets an integer from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the integer from the configuration file
         */
        public int getInt(final String path) {
            return Configuration.currentConfiguration.getInt(path);
        }

        /**
         * Gets a boolean from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the boolean from the configuration file
         */
        public boolean getBoolean(final String path) {
            return Configuration.currentConfiguration.getBoolean(path);
        }

        /**
         * Gets a string list from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the string list from the configuration file
         */
        public List<String> getStringList(final String path) {
            return Configuration.currentConfiguration.getStringList(path);
        }

        /**
         * Gets the configuration section of a path.
         *
         * @param path the path in the configuration file
         * @return the configuration section from the configuration file
         */
        public ConfigurationSection getConfigurationSection(final String path) {
            return Configuration.currentConfiguration.getConfigurationSection(path);
        }

        /**
         * Gets an object from the configuration file.
         *
         * @param path the path in the configuration file
         * @return the object from the configuration file
         */
        public Object get(final String path) {
            return Configuration.currentConfiguration.get(path);
        }

        /**
         * Sets a value in the configuration file.
         *
         * @param path the path in the configuration file
         * @param object the object to set
         */
        public void set(final String path, final Object object) {
            Configuration.currentConfiguration.set(path, object);
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
            Configuration.currentConfiguration.setComment(path, comment, CommentType.BLOCK);
        }

        /**
         * Saves the configuration file.
         */
        public void save() {
            try {
                Configuration.currentConfiguration.save();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

    }

}
