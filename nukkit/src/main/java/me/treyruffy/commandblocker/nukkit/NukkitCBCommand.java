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

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

/**
 * The Nukkit '/commandblocker' command.
 */
public class NukkitCBCommand extends Command {

  final NukkitCommandHandler commandHandler = new NukkitCommandHandler();

  /**
   * Command blocker command constructor.
   *
   * @param name the command name
   */
  public NukkitCBCommand(final String name) {
    super(name, "Command Blocker", "/commandblocker", new String[]{"cb", "commandblockernukkit", "cbnukkit"});
  }

  @Override
  public boolean execute(final CommandSender sender, final String s, final String[] strings) {
    final String argsString = "commandblocker " + String.join(" ", strings);

    this.commandHandler.onCommand(sender, argsString.split(" "));
    return true;
  }
}
