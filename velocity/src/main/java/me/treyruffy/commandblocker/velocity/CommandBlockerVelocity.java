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
package me.treyruffy.commandblocker.velocity;

import com.google.inject.Inject;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Universal;
import me.treyruffy.commandblocker.velocity.listeners.AvailableCommandsListener;
import me.treyruffy.commandblocker.velocity.listeners.CommandListener;
import me.treyruffy.commandblocker.velocity.util.SetupValues;
import me.treyruffy.commandblocker.velocity.util.VelocityEventManager;
import org.slf4j.Logger;

/**
 * The main CommandBlocker Velocity Plugin class.
 */
@Plugin(id = "commandblocker", name = "CommandBlocker", version = "@project-version@", authors = {"TreyRuffy"})
public class CommandBlockerVelocity {

  private static CommandBlockerVelocity instance;
  private final ProxyServer server;
  private final Logger logger;

  /**
   * Gets the Velocity plugin class.
   *
   * @return the CommandBlocker Velocity plugin class
   */
  public static CommandBlockerVelocity get() {
    return instance;
  }

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
   * Sets up the command blocker plugin.
   *
   * @param event the proxy initialize event
   */
  @Subscribe
  public void onProxyInitialization(final ProxyInitializeEvent event) {
    instance = this;
    Universal.get().setup(new SetupValues());
    final CommandBlockerValues<?> commandBlockerValues = Universal.get().universalMethods();

    this.server.getEventManager().register(this, new CommandListener());
    this.server.getEventManager().register(this, new AvailableCommandsListener());
    Universal.get().eventBus().subscribe(CommandBlockerEvent.class, new VelocityEventManager<>());

    final CommandManager commandManager = this.server.getCommandManager();
    final CommandMeta commandMeta = commandManager.metaBuilder("commandblocker")
      .aliases("cb", "cbvelocity", "commandblockervelocity")
      .plugin(this)
      .build();

    final LiteralCommandNode<CommandSource> commandNode = new VelocityCommandHandler().commandBlockerNode().build();
    final BrigadierCommand brigadierCommand = new BrigadierCommand(commandNode);
    commandManager.register(commandMeta, brigadierCommand);

    commandBlockerValues.log().info("Loaded correctly");
    new CommandBlockerCommon().test();
  }

  /**
   * Gets the Command Blocker Velocity server.
   *
   * @return the proxy server
   */
  public ProxyServer server() {
    return this.server;
  }

  /**
   * Gets the plugin logger.
   *
   * @return the logger
   */
  public Logger logger() {
    return this.logger;
  }

  /**
   * Gets the plugin's version.
   *
   * @return the plugin version number
   */
  public String pluginVersion() {
    return "@project-version@";
  }

}
