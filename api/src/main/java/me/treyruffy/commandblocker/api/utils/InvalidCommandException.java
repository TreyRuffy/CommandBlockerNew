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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A {@link Throwable} for invalid commands.
 *
 * @since 3.0.0
 */
public class InvalidCommandException extends Throwable {

  private static final long serialVersionUID = -1L;

  final @NotNull String message;
  @Nullable
  Throwable throwable;

  /**
   * Creates a new exception.
   *
   * @param message the message
   * @param throwable the throwable
   */
  public InvalidCommandException(final @NotNull String message, final @NotNull Throwable throwable) {
    this(message);
    this.throwable = throwable;
  }

  /**
   * Creates a new exception.
   *
   * @param message the message
   */
  public InvalidCommandException(final @NotNull String message) {
    super(message);
    this.message = message;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public @NotNull String message() {
    return this.message;
  }

  /**
   * Gets the throwable.
   *
   * @return the throwable
   */
  public @Nullable Throwable throwable() {
    return this.throwable;
  }
}
