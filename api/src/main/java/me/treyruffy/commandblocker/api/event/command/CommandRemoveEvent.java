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
package me.treyruffy.commandblocker.api.event.command;

import me.treyruffy.commandblocker.api.command.AbstractCommand;
import net.kyori.event.Cancellable;

/**
 * Called when a {@link AbstractCommand} is removed from the set.
 *
 * @since 3.0.0
 */
public class CommandRemoveEvent extends CommandEvent implements Cancellable {

  private boolean cancelled = false;

  /**
   * Creates a remove command event.
   *
   * @param command the command
   */
  public CommandRemoveEvent(final AbstractCommand command) {
    super(command);
  }

  @Override
  public boolean cancelled() {
    return this.cancelled;
  }

  @Override
  public void cancelled(final boolean cancelled) {
    this.cancelled = cancelled;
  }
}
