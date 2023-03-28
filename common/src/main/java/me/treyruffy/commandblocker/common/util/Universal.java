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

import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.common.config.Configuration;
import net.kyori.event.EventBus;
import org.jetbrains.annotations.NotNull;

/**
 * The Universal classes that can be called from anywhere.
 *
 * @since 3.0.0
 */
public class Universal {

  private static Universal instance = null;
  private CommandBlockerValues<?> commandBlockerValues;
  private final EventBus<CommandBlockerEvent> eventBus = EventBus.create(CommandBlockerEvent.class);

  /**
   * Gets the Universal class.
   *
   * @return the Universal class
   * @since 3.0.0
   */
  public static @NotNull Universal get() {
    return instance == null ? instance = new Universal() : instance;
  }

  /**
   * Gets the Universal class.
   *
   * @param force forces a new instance
   * @return the Universal class.
   * @since 3.0.0
   */
  public static @NotNull Universal get(final boolean force) {
    return force ? instance = new Universal() : get();
  }

  /**
   * Sets up the Universal class.
   *
   * @param commandBlockerValues sets the command blocker main methods
   * @since 3.0.0
   */
  public void setup(final @NotNull CommandBlockerValues<?> commandBlockerValues) {
    this.commandBlockerValues = commandBlockerValues;
    this.commandBlockerValues.setupMetrics();

    Configuration.reload();
    CommandBlockerAPI.get().setup(new Sets(), commandBlockerValues.translateVariables(), this.eventBus());
    UpdateChecker.consoleMessage();
  }

  /**
   * Gets the universal methods.
   *
   * @return the universal methods
   * @since 3.0.0
   */
  public @NotNull CommandBlockerValues<?> universalMethods() {
    return this.commandBlockerValues;
  }

  /**
   * Gets the event bus.
   *
   * @return the event bus
   * @since 3.0.0
   */
  public @NotNull EventBus<CommandBlockerEvent> eventBus() {
    return this.eventBus;
  }

}
