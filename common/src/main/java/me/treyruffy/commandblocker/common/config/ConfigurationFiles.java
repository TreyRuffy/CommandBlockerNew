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

/**
 * The different configuration files.
 *
 * @since 3.0.0
 */
public enum ConfigurationFiles {

  /**
   * The config.json file.
   *
   * @since 3.0.0
   */
  CONFIGURATION("config.json"),
  /**
   * The blocked-commands.json file.
   *
   * @since 3.0.0
   */
  BLOCKED_COMMANDS("blocked-commands.json"),
  /**
   * The messages.json file.
   */
  MESSAGES("messages.json");

  private final String fileName;

  ConfigurationFiles(final String fileName) {
    this.fileName = fileName;
  }

  /**
   * Gets the file name.
   *
   * @return the file name
   */
  public String fileName() {
    return this.fileName;
  }
}
