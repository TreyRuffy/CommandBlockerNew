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
package me.treyruffy.commandblocker.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

/**
 * The main CommandBlocker Velocity Plugin class.
 */
@Plugin(id = "commandblocker", name = "CommandBlocker", version = "@project-version@", authors = {"TreyRuffy"})
public class CommandBlockerVelocity {

    private final ProxyServer server;
    private final Logger logger;

    /**
     * Initializes the Velocity plugin.
     *
     * @param server ProxyServer from the injection
     * @param logger Logger from the injection
     */
    @Inject
    public CommandBlockerVelocity(final ProxyServer server, final Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    /**
     * Gets the Command Blocker Velocity logger.
     *
     * @return the logger
     */
    public Logger logger() {
        return this.logger;
    }

    /**
     * Gets the Command Blocker Velocity server.
     *
     * @return the proxy server
     */
    public ProxyServer server() {
        return this.server;
    }

}
