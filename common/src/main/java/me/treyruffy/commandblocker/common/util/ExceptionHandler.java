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
import org.spongepowered.configurate.serialize.SerializationException;

/**
 * The Exception handler.
 *
 * @since 3.0.0
 */
public final class ExceptionHandler {

  private ExceptionHandler() {
    // Do nothing
  }

  /**
   * Handles an exception that occurs.
   *
   * @param exception the exception to handle
   * @since 3.0.0
   */
  public static void handleException(final @NotNull Throwable exception) {
    Universal.get().universalMethods().log().warn("An error occurred in CommandBlocker.");
    if (exception instanceof SerializationException) {
      CBSerializationException((SerializationException) exception);
      return;
    }
    Universal.get().universalMethods().log().warn("Error: " + exception.getMessage(), exception);
  }

  /**
   * Handles a serialization exception.
   *
   * @param serializationException the serialization exception
   * @since 3.0.0
   */
  private static void CBSerializationException(final @NotNull SerializationException serializationException) {
    Universal.get().universalMethods().log().warn("An error occurred in CommandBlocker. This is most likely due to an" +
      " invalid config. Please check your config and try again.");
    Universal.get().universalMethods().log().warn("Serialization Exception: " + serializationException.getMessage(),
      serializationException);
  }

}
