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
package me.treyruffy.commandblocker.bungee;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.bungee.listeners.CommandListener;
import me.treyruffy.commandblocker.bungee.listeners.CommandSendListener;
import me.treyruffy.commandblocker.bungee.listeners.TabCompleteListener;
import me.treyruffy.commandblocker.bungee.util.BungeeEventManager;
import me.treyruffy.commandblocker.bungee.util.SetupValues;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Universal;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The main CommandBlocker Bungee Plugin class.
 */
public class CommandBlockerBungee extends Plugin {

  private static CommandBlockerBungee instance;
  private static CommandBlockerValues<?> commandBlockerValues;
  private static BungeeAudiences adventure;

  /**
   * Gets the Bungee plugin class.
   *
   * @return the CommandBlocker Bungee plugin class
   */
  public static CommandBlockerBungee get() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    adventure = BungeeAudiences.create(this);
    Universal.get().setup(new SetupValues());
    commandBlockerValues = Universal.get().universalMethods();

    getProxy().getPluginManager().registerCommand(this, new BungeeCBCommand());
    getProxy().getPluginManager().registerListener(this, new CommandListener());
    getProxy().getPluginManager().registerListener(this, new CommandSendListener());
    getProxy().getPluginManager().registerListener(this, new TabCompleteListener());
    Universal.get().eventBus().subscribe(CommandBlockerEvent.class, new BungeeEventManager<>());

    commandBlockerValues.log().info("Loaded correctly");
    new CommandBlockerCommon().test();
  }

  /**
   * Gets the Bungee Adventure API.
   *
   * @return the Bungee Adventure API
   */
  public static @NotNull BungeeAudiences adventure() {
    if (adventure == null)
      throw new IllegalStateException("Tried to access Adventure while the plugin was disabled.");
    return adventure;
  }

  @Override
  public void onDisable() {
    if (adventure != null) {
      adventure.close();
      adventure = null;
    }
    commandBlockerValues.log().info("Unloaded correctly");
  }

}
