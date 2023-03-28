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
package me.treyruffy.commandblocker.api.command;

/**
 * The blocking types.
 *
 * @since 3.0.0
 */
public enum CommandBlockerTypes {

  /**
   * Regular players.
   * Blocks commands for regular players.
   *
   * @since 3.0.0
   */
  REGULAR_PLAYERS("blockedCommands"),

  /**
   * Operators.
   * Blocks commands for operators.
   *
   * @since 3.0.0
   */
  OPERATORS("blockedOperatorCommands");

  private final String configurationSection;

  CommandBlockerTypes(final String configurationSection) {
    this.configurationSection = configurationSection;
  }

  /**
   * Gets the configuration section.
   *
   * @return the configuration section
   * @since 3.0.0
   */
  public String configurationSection() {
    return this.configurationSection;
  }
}
