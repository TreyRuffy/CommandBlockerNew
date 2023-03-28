/*
 * This file is part of Command Blocker for Minecraft.
 * Copyright (C) 2023 TreyRuffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.treyruffy.commandblocker.common.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import me.treyruffy.commandblocker.common.util.Universal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

/**
 * The configuration accessor and mutator.
 *
 * @since 3.0.0
 */
public class Configuration {

  @NotNull
  private static Path directoryPath = Paths.get("plugins", "CommandBlocker");

  private static GsonConfigurationLoader configurationLoader;
  private static GsonConfigurationLoader messagesLoader;
  private static GsonConfigurationLoader blockedCommandsLoader;

  @Nullable
  private ConfigurationNode rootNode = null;
  @NotNull
  private final GsonConfigurationLoader currentLoader;
  @NotNull
  private final Path filePath;

  /**
   * Accesses a specific configuration file.
   *
   * @param configurationFiles the configuration file to access
   * @since 3.0.0
   */
  public Configuration(final @NotNull ConfigurationFiles configurationFiles) {
    switch (configurationFiles) {
      case MESSAGES:
        this.currentLoader = messagesLoader;
        this.filePath = Paths.get(directoryPath + File.separator + ConfigurationFiles.MESSAGES.fileName());
        break;
      case BLOCKED_COMMANDS:
        this.currentLoader = blockedCommandsLoader;
        this.filePath = Paths.get(directoryPath + File.separator + ConfigurationFiles.BLOCKED_COMMANDS.fileName());
        break;
      default:
        this.currentLoader = configurationLoader;
        this.filePath = Paths.get(directoryPath + File.separator + ConfigurationFiles.CONFIGURATION.fileName());
        break;
    }
    try {
      this.rootNode = this.currentLoader.load();
    } catch (final IOException e) {
      Universal.get().universalMethods().log().severe("Error loading configuration file: " + this.filePath, e);
    }
  }

  private static void createFiles() throws IOException {
    if (!new File(directoryPath.toString()).exists()) {
      if (!new File(directoryPath.toString()).mkdirs()) {
        Universal.get().universalMethods().log().severe("Error creating configuration directory!");
        throw new IOException("Error creating configuration directory!");
      }
    }

    for (final @NotNull ConfigurationFiles configurationFiles : ConfigurationFiles.values()) {
      final File currentFile = new File(directoryPath + File.separator + configurationFiles.fileName());
      if (!currentFile.exists()) {
        Files.copy(Objects.requireNonNull(Configuration.class.getResourceAsStream("/" + configurationFiles.fileName())), Paths.get(directoryPath + File.separator + configurationFiles.fileName()));
      }
    }
  }

  /**
   * Reloads all configuration files.
   *
   * @since 3.0.0
   */
  public static void reload() {
    try {
      createFiles();
    } catch (final IOException exception) {
      exception.printStackTrace();
    }
    if (configurationLoader == null)
      configurationLoader =
      GsonConfigurationLoader.builder().path(Paths.get(directoryPath + File.separator + ConfigurationFiles.CONFIGURATION.fileName())).build();
    if (messagesLoader == null)
      messagesLoader =
      GsonConfigurationLoader.builder().path(Paths.get(directoryPath + File.separator + ConfigurationFiles.MESSAGES.fileName())).build();
    if (blockedCommandsLoader == null)
      blockedCommandsLoader =
      GsonConfigurationLoader.builder().path(Paths.get(directoryPath + File.separator + ConfigurationFiles.BLOCKED_COMMANDS.fileName())).build();
  }

  /**
   * Sets a new path for the configuration folder.
   *
   * @param newPath the new configuration folder path
   * @since 3.0.0
   */
  public static void directoryPath(final Path newPath) {
    directoryPath = newPath;
  }

  /**
   * Gets the path for the configuration folder.
   *
   * @return the path for the configuration folder
   * @since 3.0.0
   */
  public static Path directoryPath() {
    return directoryPath;
  }

  /**
   * Gets the path for the configuration file.
   *
   * @return the path for the configuration file
   * @since 3.0.0
   */
  public Path filePath() {
    return this.filePath;
  }

  /**
   * Gets the root node.
   *
   * @return the root node
   * @since 3.0.0
   */
  public ConfigurationNode rootNode() {
    return this.rootNode;
  }

  /**
   * Saves the configuration.
   *
   * @since 3.0.0
   */
  public void save() {
    try {
      this.currentLoader.save(this.rootNode);
    } catch (final ConfigurateException e) {
      Universal.get().universalMethods().log().severe("Error saving configuration file: " + this.filePath, e);
    }
  }
}
