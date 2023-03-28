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
package me.treyruffy.commandblocker.spongelegacy;

import com.google.inject.Inject;
import java.io.File;
import java.nio.file.Paths;
import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.config.Configuration;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Universal;
import me.treyruffy.commandblocker.spongelegacy.listeners.CommandListener;
import me.treyruffy.commandblocker.spongelegacy.listeners.CommandSendListener;
import me.treyruffy.commandblocker.spongelegacy.listeners.TabCompleteListener;
import me.treyruffy.commandblocker.spongelegacy.util.SetupValues;
import me.treyruffy.commandblocker.spongelegacy.util.SpongeEventManager;
import net.kyori.adventure.platform.spongeapi.SpongeAudiences;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

/**
 * The main CommandBlocker Sponge Plugin class.
 */
@Plugin(
  id = "commandblocker",
  name = "CommandBlocker",
  version = "@project-version@",
  description = "Command Blocker for Sponge 7",
  authors = {"TreyRuffy"},
  url = "https://github.com/TreyRuffy/CommandBlocker"
)
public class CommandBlockerSpongeLegacy {

  private static CommandBlockerSpongeLegacy instance;
  private static SpongeAudiences adventure;

  @Inject
  private Logger logger;
  @Inject
  private PluginContainer plugin;

  /**
   * Gets the Command Blocker Sponge plugin class.
   *
   * @return the Command Blocker Sponge class
   */
  public static CommandBlockerSpongeLegacy get() {
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
  public void onGameInitialization(final GameInitializationEvent event) {
    instance = this;
    adventure = SpongeAudiences.create(this.plugin, Sponge.getGame());
    Configuration.directoryPath(Paths.get("config" + File.separator + "commandblocker"));
    Universal.get().setup(new SetupValues());
    final CommandBlockerValues<?> commandBlockerValues = Universal.get().universalMethods();

    Sponge.getCommandManager().register(this.plugin, new SpongeLegacyCommandHandler(), "commandblocker", "cb", "commandblockersponge", "cbsponge");
    Sponge.getEventManager().registerListeners(this.plugin, new CommandListener());
    Sponge.getEventManager().registerListeners(this.plugin, new CommandSendListener());
    Sponge.getEventManager().registerListeners(this.plugin, new TabCompleteListener());
    Universal.get().eventBus().subscribe(CommandBlockerEvent.class, new SpongeEventManager<>());

    commandBlockerValues.log().info("Loaded correctly");
    new CommandBlockerCommon().test();
  }

  /**
   * Gets the Sponge Adventure API.
   *
   * @return the Sponge Adventure API
   */
  public static @NotNull SpongeAudiences adventure() {
    if (adventure == null)
      throw new IllegalStateException("Tried to access Adventure while the plugin was disabled.");
    return adventure;
  }

  /**
   * Gets the Command Blocker logger.
   *
   * @return the logger
   */
  public Logger logger() {
    return this.logger;
  }

}
