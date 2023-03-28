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

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * The Bungee '/commandblocker' command.
 */
public class BungeeCBCommand extends Command {

  final BungeeCommandHandler commandHandler = new BungeeCommandHandler();

  /**
   * The default command blocker command constructor.
   */
  public BungeeCBCommand() {
    super("commandblocker", null, "cb", "commandblockerbungee", "cbbungee");
  }

  @Override
  public void execute(final CommandSender sender, final String[] args) {
    final String argsString = "commandblocker " + String.join(" ", args);

    this.commandHandler.onCommand(sender, argsString.split(" "));
  }
}
