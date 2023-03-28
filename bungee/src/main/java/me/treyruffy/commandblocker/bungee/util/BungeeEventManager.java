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
package me.treyruffy.commandblocker.bungee.util;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.api.event.command.CommandCreateEvent;
import me.treyruffy.commandblocker.api.event.command.CommandEvent;
import me.treyruffy.commandblocker.api.event.command.CommandRemoveEvent;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.bungee.CommandBlockerBungee;
import me.treyruffy.commandblocker.bungee.event.BungeeCommandBlockerEvent;
import me.treyruffy.commandblocker.bungee.event.command.BungeeCommandCreateEvent;
import me.treyruffy.commandblocker.bungee.event.command.BungeeCommandEvent;
import me.treyruffy.commandblocker.bungee.event.command.BungeeCommandRemoveEvent;
import me.treyruffy.commandblocker.bungee.event.player.BungeeBlockedCommandEvent;
import net.kyori.event.EventSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 * The Bungee event manager.
 *
 * @param <T> The event type.
 */
public final class BungeeEventManager<T extends CommandBlockerEvent> implements EventSubscriber<T> {

  @Override
  public void on(final @NotNull T event) {
    if (event instanceof CommandCreateEvent) {
      final BungeeCommandCreateEvent commandCreateEvent = new BungeeCommandCreateEvent((CommandCreateEvent) event);
      CommandBlockerBungee.get().getProxy().getPluginManager().callEvent(commandCreateEvent);
    } else if (event instanceof CommandRemoveEvent) {
      final BungeeCommandRemoveEvent commandRemoveEvent = new BungeeCommandRemoveEvent((CommandRemoveEvent) event);
      CommandBlockerBungee.get().getProxy().getPluginManager().callEvent(commandRemoveEvent);
    } else if (event instanceof BlockedCommandEvent) {
      final BungeeBlockedCommandEvent blockedCommandEvent = new BungeeBlockedCommandEvent((BlockedCommandEvent) event);
      CommandBlockerBungee.get().getProxy().getPluginManager().callEvent(blockedCommandEvent);
    } else if (event instanceof CommandEvent) {
      final BungeeCommandEvent commandEvent = new BungeeCommandEvent((CommandEvent) event);
      CommandBlockerBungee.get().getProxy().getPluginManager().callEvent(commandEvent);
    } else {
      final BungeeCommandBlockerEvent commandEvent = new BungeeCommandBlockerEvent(event);
      CommandBlockerBungee.get().getProxy().getPluginManager().callEvent(commandEvent);
    }
  }
}
