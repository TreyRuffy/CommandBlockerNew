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
package me.treyruffy.commandblocker.bukkitlegacy.event.command;

import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.event.command.CommandEvent;
import me.treyruffy.commandblocker.bukkitlegacy.event.BukkitCommandBlockerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Base event for {@link AbstractCommand}s.
 */
public class BukkitCommandEvent extends BukkitCommandBlockerEvent {

  /**
   * A new command event.
   *
   * @param apiEvent the {@link me.treyruffy.commandblocker.api.event.CommandBlockerEvent}
   */
  public BukkitCommandEvent(final CommandEvent apiEvent) {
    super(apiEvent);
  }

  /**
   * The command.
   *
   * @return the CommandBlocker {@link AbstractCommand}
   */
  AbstractCommand command() {
    return this.apiEvent().command();
  }

  @Override
  public @NotNull CommandEvent apiEvent() {
    return (CommandEvent) super.apiEvent();
  }

}
