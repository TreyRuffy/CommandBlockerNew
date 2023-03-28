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
package me.treyruffy.commandblocker.bukkit.util;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.api.event.command.CommandCreateEvent;
import me.treyruffy.commandblocker.api.event.command.CommandEvent;
import me.treyruffy.commandblocker.api.event.command.CommandRemoveEvent;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.bukkit.event.BukkitCommandBlockerEvent;
import me.treyruffy.commandblocker.bukkit.event.command.BukkitCommandCreateEvent;
import me.treyruffy.commandblocker.bukkit.event.command.BukkitCommandEvent;
import me.treyruffy.commandblocker.bukkit.event.command.BukkitCommandRemoveEvent;
import me.treyruffy.commandblocker.bukkit.event.player.BukkitBlockedCommandEvent;
import net.kyori.event.EventSubscriber;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * The Bukkit event manager.
 *
 * @param <T> The event type.
 */
public final class BukkitEventManager<T extends CommandBlockerEvent> implements EventSubscriber<T> {

  @Override
  public void on(final @NotNull T event) {
    if (event instanceof CommandCreateEvent) {
      final BukkitCommandCreateEvent commandCreateEvent = new BukkitCommandCreateEvent((CommandCreateEvent) event);
      Bukkit.getPluginManager().callEvent(commandCreateEvent);
    } else if (event instanceof CommandRemoveEvent) {
      final BukkitCommandRemoveEvent commandRemoveEvent = new BukkitCommandRemoveEvent((CommandRemoveEvent) event);
      Bukkit.getPluginManager().callEvent(commandRemoveEvent);
    } else if (event instanceof BlockedCommandEvent) {
      final BukkitBlockedCommandEvent blockedCommandEvent = new BukkitBlockedCommandEvent((BlockedCommandEvent) event);
      Bukkit.getPluginManager().callEvent(blockedCommandEvent);
    } else if (event instanceof CommandEvent) {
      final BukkitCommandEvent commandEvent = new BukkitCommandEvent((CommandEvent) event);
      Bukkit.getPluginManager().callEvent(commandEvent);
    } else {
      final BukkitCommandBlockerEvent commandEvent = new BukkitCommandBlockerEvent(event);
      Bukkit.getPluginManager().callEvent(commandEvent);
    }
  }
}
