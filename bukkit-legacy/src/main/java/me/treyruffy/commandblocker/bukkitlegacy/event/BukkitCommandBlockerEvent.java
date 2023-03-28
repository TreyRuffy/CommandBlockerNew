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
package me.treyruffy.commandblocker.bukkitlegacy.event;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * The base event that is called when any other CommandBlocker Bukkit Legacy event occurs.
 * All other CommandBlocker Bukkit Legacy events implement this.
 */
public class BukkitCommandBlockerEvent extends Event {

  private final CommandBlockerEvent apiEvent;

  private static final HandlerList HANDLERS = new HandlerList();

  /**
   * Creates a new Bukkit Legacy Command Blocker event.
   *
   * @param apiEvent the {@link CommandBlockerEvent}
   */
  public BukkitCommandBlockerEvent(final CommandBlockerEvent apiEvent) {
    this.apiEvent = apiEvent;
  }

  /**
   * Gets the handler list.
   *
   * @return the handler list
   */
  @SuppressWarnings("checkstyle:MethodName")
  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
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
