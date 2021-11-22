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

import java.util.Collections;
import java.util.UUID;
import me.treyruffy.commandblocker.api.Command;
import me.treyruffy.commandblocker.api.CommandBlocker;
import me.treyruffy.commandblocker.api.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.config.Configuration;
import me.treyruffy.commandblocker.api.config.ConfigurationFiles;

/**
 * The main CommandBlocker common class.
 */
public class CommandBlockerCommon {

    private boolean debug = true;

    /**
     * If debug is enabled, this will execute this method.
     */
    public void test() {
        if (this.debug()) {
            final CommandBlockerValues commandBlockerValues = Universal.get().universalMethods();
            final Configuration.ConfigurationOptions configuration =
                    Configuration.getConfiguration(ConfigurationFiles.CONFIGURATION);
            if (configuration.get("Testing.Debug") == null) {
                configuration.set("Testing.Debug", true, "This is a test in order to make sure configuration works "
                        + "properly");
                configuration.save();
            }
            commandBlockerValues.log().info("Testing Debug: " + configuration.getString("Testing.Debug"));

            final String[] command = new String[] {"testing123", "test2"};
            CommandBlocker.addBlockedCommand(CommandBlockerTypes.REGULAR_PLAYERS, new Command(command, null,
                null, null, null, null, Collections.singletonList(UUID.randomUUID()),
                null, null, null, null));
        }
    }

    /**
     * Checks if debug is enabled.
     *
     * @return debug is enabled
     */
    public boolean debug() {
        return this.debug;
    }

    /**
     * Sets if debug is enabled.
     *
     * @param debug sets debug enabled
     */
    public void debug(final boolean debug) {
        this.debug = debug;
    }

}
