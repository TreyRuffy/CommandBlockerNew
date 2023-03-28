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
package me.treyruffy.commandblocker.api.utils;

import java.util.Set;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Add or removing commands from the blocked sets.
 *
 * @since 3.0.0
 */
public interface ModifySets {

  /**
   * Adds a command to the set.
   *
   * @param commandBlockerTypes the type of command to add
   * @param command             the command to add
   * @since 3.0.0
   */
  void addCommand(final @NotNull CommandBlockerTypes commandBlockerTypes, final @NotNull AbstractCommand command);

  /**
   * Removes a command from the set.
   *
   * @param commandBlockerTypes the type of command to remove
   * @param command             the command to remove
   * @since 3.0.0
   */
  void removeCommand(final @NotNull CommandBlockerTypes commandBlockerTypes, final @NotNull AbstractCommand command);

  /**
   * Removes all commands from the set.
   *
   * @param commandBlockerTypes the type of command to remove
   * @since 3.0.0
   */
  void removeAllCommands(final @NotNull CommandBlockerTypes commandBlockerTypes);

  /**
   * Gets all commands from the set.
   *
   * @param commandBlockerTypes the type of command to get
   * @return the commands in the set
   * @since 3.0.0
   */
  @NotNull Set<AbstractCommand> commands(final @NotNull CommandBlockerTypes commandBlockerTypes);

}
