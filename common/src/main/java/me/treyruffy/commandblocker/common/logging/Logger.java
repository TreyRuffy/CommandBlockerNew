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
package me.treyruffy.commandblocker.common.logging;

/**
 * The interface for logging.
 */
public interface Logger {

    /**
     * Logs a message as info.
     *
     * @param message the message to log
     */
    void info(String message);

    /**
     * Logs a message as warn.
     *
     * @param message the message to log
     */
    void warn(String message);

    /**
     * Logs a message as warn.
     *
     * @param message the message to log
     * @param throwable the throwable to log
     */
    void warn(String message, Throwable throwable);

    /**
     * Logs a message as severe.
     *
     * @param message the message to log
     */
    void severe(String message);

    /**
     * Logs a message as severe.
     *
     * @param message the message to log
     * @param throwable the throwable to log
     */
    void severe(String message, Throwable throwable);

}
