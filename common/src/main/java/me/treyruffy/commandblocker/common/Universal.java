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
package me.treyruffy.commandblocker.common;

import me.treyruffy.commandblocker.api.CommandBlocker;
import me.treyruffy.commandblocker.api.config.Configuration;
import me.treyruffy.commandblocker.api.config.ConfigurationFiles;
import me.treyruffy.commandblocker.common.config.UpdateConfigurations;

/**
 * The Universal class which can be called from any other class.
 */
public class Universal {

    private static Universal instance = null;
    private CommandBlockerValues commandBlockerValues;
    private CommandBlocker commandBlocker;

    /**
     * Gets the Universal class.
     *
     * @return the Universal class
     */
    public static Universal get() {
        return instance == null ? instance = new Universal() : instance;
    }

    /**
     * Gets the Universal class.
     *
     * @param force forces a new instance
     * @return the Universal class.
     */
    public static Universal get(final boolean force) {
        return force ? instance = new Universal() : get();
    }

    /**
     * Sets up the Universal class.
     *
     * @param commandBlockerValues sets the command blocker main methods
     * @return the Universal class
     */
    public Universal setup(final CommandBlockerValues commandBlockerValues) {
        this.commandBlockerValues = commandBlockerValues;
        this.commandBlockerValues.setupMetrics();

        Configuration.getConfiguration(ConfigurationFiles.CONFIGURATION); // Sets up the configuration files.
        new UpdateConfigurations().updateAll();

        return this;
    }

    /**
     * Gets the universal methods.
     *
     * @return the universal methods
     */
    public CommandBlockerValues universalMethods() {
        return this.commandBlockerValues;
    }

    /**
     * Gets the Command Blocker API.
     *
     * @return the Command Blocker API class
     */
    public CommandBlocker commandBlocker() {
        return this.commandBlocker;
    }

}
