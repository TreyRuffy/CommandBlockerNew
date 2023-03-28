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
package me.treyruffy.commandblocker.velocity.event;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * The base event that is called when any other CommandBlocker Velocity event occurs.
 * All other CommandBlocker Velocity events implement this.
 */
public class VelocityCommandBlockerEvent {

  private final CommandBlockerEvent apiEvent;

  /**
   * Creates a new Velocity Command Blocker event.
   *
   * @param apiEvent the {@link CommandBlockerEvent}
   */
  public VelocityCommandBlockerEvent(final CommandBlockerEvent apiEvent) {
    this.apiEvent = apiEvent;
  }

  /**
   * Gets the {@link CommandBlockerEvent}.
   *
   * @return the event
   */
  public @NotNull CommandBlockerEvent apiEvent() {
    return this.apiEvent;
  }
}
