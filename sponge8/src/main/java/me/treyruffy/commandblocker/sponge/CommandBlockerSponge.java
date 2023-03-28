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
package me.treyruffy.commandblocker.sponge;

import com.google.inject.Inject;
import java.io.File;
import java.nio.file.Paths;
import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.config.Configuration;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Universal;
import me.treyruffy.commandblocker.sponge.listeners.CommandListener;
import me.treyruffy.commandblocker.sponge.util.SetupValues;
import me.treyruffy.commandblocker.sponge.util.SpongeEventManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

/**
 * The main CommandBlocker Sponge Plugin class.
 */
@Plugin("commandblocker")
public class CommandBlockerSponge {

  private static CommandBlockerSponge instance;

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
   * @return the Command Blocker Sponge class
   */
  public static CommandBlockerSponge get() {
    return instance;
  }

  /**
   * Gets the Command Blocker plugin container.
   *
   * @return the Command Blocker plugin container
   */
  public PluginContainer plugin() {
    return this.plugin;
  }

  /**
   * Runs on plugin construction.
   *
   * @param event ConstructPluginEvent
   */
  @Listener
  public void onConstructPlugin(final ConstructPluginEvent event) {
    instance = this;
    Configuration.directoryPath(Paths.get("config" + File.separator + "commandblocker"));
    Universal.get().setup(new SetupValues());
    final CommandBlockerValues<?> commandBlockerValues = Universal.get().universalMethods();

    Sponge.eventManager().registerListeners(this.plugin, new CommandListener());
    Universal.get().eventBus().subscribe(CommandBlockerEvent.class, new SpongeEventManager<>());

    commandBlockerValues.log().info("Loaded correctly");
    new CommandBlockerCommon().test();
  }

  /**
   * Registers the command blocker command.
   *
   * @param event the {@link RegisterCommandEvent}
   */
  @Listener
  public void registerCommand(final @NotNull RegisterCommandEvent<Command.Raw> event) {
    event.register(this.plugin, new SpongeCommandHandler(), "commandblocker", "cb", "commandblockersponge", "cbsponge");
  }

  /**
   * Gets the Command Blocker logger.
   *
   * @return the logger
   */
  public Logger logger() {
    return this.plugin.logger();
  }

}
