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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The universal logger.
 *
 * @since 3.0.0
 */
public interface Logger {

  /**
   * Logs a message as info.
   *
   * @param message the message
   * @since 3.0.0
   */
  void info(@NotNull String message);

  /**
   * Logs a message as warn.
   *
   * @param message the message
   * @since 3.0.0
   */
  void warn(@NotNull String message);

  /**
   * Logs a message as warn.
   *
   * @param message   the message
   * @param throwable the throwable
   * @since 3.0.0
   */
  void warn(@NotNull String message, @Nullable Throwable throwable);

  /**
   * Logs a message as severe.
   *
   * @param message the message
   * @since 3.0.0
   */
  void severe(@NotNull String message);

  /**
   * Logs a message as severe.
   *
   * @param message   the message
   * @param throwable the throwable
   * @since 3.0.0
   */
  void severe(@NotNull String message, @Nullable Throwable throwable);

}
