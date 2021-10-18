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
package me.treyruffy.commandblocker.sponge;

import com.google.inject.Inject;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.CommandBlockerValues;
import me.treyruffy.commandblocker.common.Universal;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/**
 * The main CommandBlocker Sponge Plugin class.
 */
@Plugin("commandblocker")
public class CommandBlockerSponge {

    private final boolean debug = new CommandBlockerCommon().debug();
    private static CommandBlockerSponge instance;
    private static CommandBlockerValues commandBlockerValues;
    private static Logger logger;
    final PluginContainer plugin;

    /**
     * Gets the plugin container for Command Blocker sponge.
     *
     * @param plugin the plugin container
     */
    @Inject
    public CommandBlockerSponge(final PluginContainer plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the Command Blocker Sponge plugin class.
     *
     * @return the Command Blocker Sponge class.
     */
    public static CommandBlockerSponge get() {
        return instance;
    }

    /**
     * Runs on server start.
     *
     * @param event ConstructPluginEvent
     */
    @Listener
    public void onServerStart(final ConstructPluginEvent event) {
        instance = this;
        Universal.get().setup(new SetupValues());
        commandBlockerValues = Universal.get().universalMethods();
        commandBlockerValues.log().info("Loaded correctly");
        new CommandBlockerCommon().test();
    }

    /**
     * Gets the Command Blocker logger.
     *
     * @return the logger
     */
    public Logger logger() {
        return this.plugin.logger();
    }

    /**
     * Runs on server end.
     *
     * @param event StoppingEngineEvent
     */
    @Listener
    public void onServerStop(final StoppingEngineEvent<Server> event) {
        // Any tear down per-game instance. This can run multiple times when
        // using the integrated (singleplayer) server.
        commandBlockerValues.log().info("Unloaded correctly");
    }

}
