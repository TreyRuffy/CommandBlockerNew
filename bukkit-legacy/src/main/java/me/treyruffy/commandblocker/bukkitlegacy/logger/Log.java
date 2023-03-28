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
package me.treyruffy.commandblocker.bukkitlegacy.logger;

import java.util.logging.Level;
import me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy;
import me.treyruffy.commandblocker.common.util.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Bukkit Legacy logger.
 */
public class Log implements Logger {

  @Override
  public void info(final @NotNull String message) {
    me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getLogger().log(Level.INFO, message);
  }

  @Override
  public void warn(final @NotNull String message) {
    me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getLogger().log(Level.WARNING, message);
  }

  @Override
  public void warn(final @NotNull String message, final @Nullable Throwable throwable) {
    if (throwable == null) {
      this.warn(message);
    }
    me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getLogger().log(Level.WARNING, message, throwable);
  }

  @Override
  public void severe(final @NotNull String message) {
    me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getLogger().log(Level.SEVERE, message);
  }

  @Override
  public void severe(final @NotNull String message, final @Nullable Throwable throwable) {
    if (throwable == null) {
      this.severe(message);
    }
    CommandBlockerBukkitLegacy.get().getLogger().log(Level.SEVERE, message, throwable);
  }
}
