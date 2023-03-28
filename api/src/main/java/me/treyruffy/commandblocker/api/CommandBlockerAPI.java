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
package me.treyruffy.commandblocker.api;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import me.treyruffy.commandblocker.api.utils.ModifySets;
import net.kyori.event.EventBus;
import org.jetbrains.annotations.NotNull;

/**
 * Command Blocker.
 *
 * @since 3.0.0
 */
public final class CommandBlockerAPI {

  private CommandBlockerAPI() {
    // API Class
  }

  private static CommandBlockerAPI instance = null;
  private ModifySets modifySets = null;
  private ComponentTranslator componentTranslator = null;
  private EventBus<CommandBlockerEvent> eventBus = null;

  /**
   * Gets the CommandBlockerAPI class.
   *
   * @return the CommandBlockerAPI class
   */
  public static @NotNull CommandBlockerAPI get() {
    return instance == null ? instance = new CommandBlockerAPI() : instance;
  }

  /**
   * Sets up the CommandBlockerAPI class.
   *
   * @param modifySets          sets up the {@link ModifySets} instance
   * @param componentTranslator sets up the {@link ComponentTranslator} instance
   * @param eventBus            sets up the {@link EventBus} instance
   */
  public void setup(final @NotNull ModifySets modifySets, final @NotNull ComponentTranslator componentTranslator,
                    final @NotNull EventBus<CommandBlockerEvent> eventBus) {
    this.modifySets = modifySets;
    this.componentTranslator = componentTranslator;
    this.eventBus = eventBus;
  }

  /**
   * Gets the {@link ModifySets}.
   * Add or remove commands from each blocked set.
   *
   * @return the {@link ModifySets}
   */
  public @NotNull ModifySets modifySets() {
    return this.modifySets;
  }

  /**
   * Change the {@link ModifySets}.
   *
   * @param sets the new {@link ModifySets}
   */
  public void modifySets(final @NotNull ModifySets sets) {
    this.modifySets = sets;
  }

  /**
   * Gets the {@link ComponentTranslator}.
   * Translate strings to components.
   *
   * @return the {@link ComponentTranslator}
   */
  public @NotNull ComponentTranslator translateVariables() {
    return this.componentTranslator;
  }

  /**
   * Gets the {@link EventBus}.
   * All generic events are posted here.
   *
   * @return the {@link EventBus}
   */
  public @NotNull EventBus<CommandBlockerEvent> eventBus() {
    return this.eventBus;
  }

  /**
   * Checks if String 1 starts with String 2.
   *
   * @param string           the string to check
   * @param incompleteString the incomplete string
   * @return {@code true} if String 1 starts with String 2
   */
  public static boolean startsWithIgnoreCase(final @NotNull String string, final @NotNull String incompleteString) {
    if (string.length() > incompleteString.length()) {
      return false;
    }
    return string.equalsIgnoreCase(incompleteString.substring(0, string.length()));
  }
}
