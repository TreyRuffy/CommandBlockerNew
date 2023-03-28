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

import java.util.HashMap;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The interface to help you translate strings to {@link Component}s.
 *
 * @since 3.0.0
 */
public interface ComponentTranslator {

  /**
   * Translates variables from a component.
   *
   * @param message the component to translate
   * @return the translated component
   * @since 3.0.0
   */
  @NotNull Component stringToComponent(final @NotNull String message);

  /**
   * Translates player variables from a component.
   *
   * @param message the component to translate
   * @param player  the player to translate for
   * @return the translated component
   * @since 3.0.0
   */
  @NotNull Component stringToComponent(final @NotNull String message, final @Nullable CommandBlockerPlayers<?> player);

  /**
   * Translates player variables from a component with additional variables.
   *
   * @param message                      the component to translate
   * @param player                       the player to translate for
   * @param additionalComponentVariables additional component placeholders to translate in the text
   * @return the translated component
   * @since 3.0.0
   */
  @NotNull Component stringToComponent(final @NotNull String message, final @Nullable CommandBlockerPlayers<?> player,
                                       final @Nullable HashMap<String, Component> additionalComponentVariables);

  /**
   * Translates variables from a component.
   *
   * @param component                    the component to translate
   * @param player                       the player to translate for
   * @param additionalComponentVariables additional component placeholders to translate in the text
   * @return the translated component
   * @since 3.0.0
   */
  @NotNull Component componentVariableTranslate(final @NotNull Component component,
                                                final @Nullable CommandBlockerPlayers<?> player,
                                                final @Nullable HashMap<String, Component> additionalComponentVariables);

}
