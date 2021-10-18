/*
 * Copyright (C) 2015-2021 TreyRuffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.treyruffy.commandblocker.bungee.logger;

import java.util.logging.Level;
import me.treyruffy.commandblocker.bungee.CommandBlockerBungee;
import me.treyruffy.commandblocker.common.logging.Logger;

/**
 * The Bungee logger.
 */
public class Log implements Logger {

    @Override
    public void info(final String message) {
        CommandBlockerBungee.get().getLogger().log(Level.INFO, message);
    }

    @Override
    public void warn(final String message) {
        CommandBlockerBungee.get().getLogger().log(Level.WARNING, message);
    }

    @Override
    public void warn(final String message, final Throwable throwable) {
        CommandBlockerBungee.get().getLogger().log(Level.WARNING, message, throwable);
    }

    @Override
    public void severe(final String message) {
        CommandBlockerBungee.get().getLogger().log(Level.SEVERE, message);
    }

    @Override
    public void severe(final String message, final Throwable throwable) {
        CommandBlockerBungee.get().getLogger().log(Level.SEVERE, message, throwable);
    }
}
