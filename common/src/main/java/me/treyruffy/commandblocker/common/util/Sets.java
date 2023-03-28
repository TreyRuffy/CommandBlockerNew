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
package me.treyruffy.commandblocker.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.event.command.CommandCreateEvent;
import me.treyruffy.commandblocker.api.event.command.CommandRemoveEvent;
import me.treyruffy.commandblocker.api.utils.ModifySets;
import org.jetbrains.annotations.NotNull;

/**
 * The set accessor and mutator.
 *
 * @since 3.0.0
 */
public class Sets implements ModifySets {

  @NotNull
  private final List<Set<AbstractCommand>> blockedSet = new ArrayList<>();

  /**
   * Creates the sets.
   *
   * @since 3.0.0
   */
  public Sets() {
    for (int i = 0; i < CommandBlockerTypes.values().length; i++) {
      this.blockedSet.add(new HashSet<>());
    }
  }

  @Override
  public void addCommand(final @NotNull CommandBlockerTypes commandBlockerTypes, final @NotNull AbstractCommand command) {
    final CommandCreateEvent event = new CommandCreateEvent(command);
    Universal.get().eventBus().post(event);
    if (!event.cancelled())
      this.blockedSet.get(commandBlockerTypes.ordinal()).add(command);
  }

  @Override
  public void removeCommand(final @NotNull CommandBlockerTypes commandBlockerTypes, final @NotNull AbstractCommand command) {
    final CommandRemoveEvent event = new CommandRemoveEvent(command);
    Universal.get().eventBus().post(event);
    if (!event.cancelled())
      this.blockedSet.get(commandBlockerTypes.ordinal()).remove(command);
  }

  @Override
  public void removeAllCommands(final @NotNull CommandBlockerTypes commandBlockerTypes) {
    for (final AbstractCommand command : this.blockedSet.get(commandBlockerTypes.ordinal())) {
      this.removeCommand(commandBlockerTypes, command);
    }
  }

  @Override
  public @NotNull Set<AbstractCommand> commands(final @NotNull CommandBlockerTypes commandBlockerTypes) {
    return this.blockedSet.get(commandBlockerTypes.ordinal());
  }
}
