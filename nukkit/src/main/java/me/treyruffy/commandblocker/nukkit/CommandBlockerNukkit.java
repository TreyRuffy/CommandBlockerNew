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
package me.treyruffy.commandblocker.nukkit;

import cn.nukkit.plugin.PluginBase;
import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Universal;
import me.treyruffy.commandblocker.nukkit.listeners.CommandListener;
import me.treyruffy.commandblocker.nukkit.util.NukkitEventManager;
import me.treyruffy.commandblocker.nukkit.util.SetupValues;

/**
 * The main CommandBlocker Nukkit Plugin class.
 */
public class CommandBlockerNukkit extends PluginBase {

  private static CommandBlockerNukkit instance;
  private static CommandBlockerValues<?> commandBlockerValues;

  /**
   * Gets the Nukkit plugin class.
   *
   * @return the CommandBlocker Nukkit plugin class
   */
  public static CommandBlockerNukkit get() {
    return instance;
  }

  @Override
  public void onEnable() {
    instance = this;
    Universal.get().setup(new SetupValues());
    commandBlockerValues = Universal.get().universalMethods();

    final NukkitCBCommand command = new NukkitCBCommand("commandblocker");
    getServer().getCommandMap().register("commandblocker", command);

    getServer().getPluginManager().registerEvents(new CommandListener(), this);
    Universal.get().eventBus().subscribe(CommandBlockerEvent.class, new NukkitEventManager<>());
    commandBlockerValues.log().info("Loaded correctly");
    new CommandBlockerCommon().test();
  }

  @Override
  public void onDisable() {
    commandBlockerValues.log().info("Unloaded correctly");
  }
}
