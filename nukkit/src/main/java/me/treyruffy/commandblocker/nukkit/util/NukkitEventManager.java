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
package me.treyruffy.commandblocker.nukkit.util;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.api.event.command.CommandCreateEvent;
import me.treyruffy.commandblocker.api.event.command.CommandEvent;
import me.treyruffy.commandblocker.api.event.command.CommandRemoveEvent;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.nukkit.CommandBlockerNukkit;
import me.treyruffy.commandblocker.nukkit.event.NukkitCommandBlockerEvent;
import me.treyruffy.commandblocker.nukkit.event.command.NukkitCommandCreateEvent;
import me.treyruffy.commandblocker.nukkit.event.command.NukkitCommandEvent;
import me.treyruffy.commandblocker.nukkit.event.command.NukkitCommandRemoveEvent;
import me.treyruffy.commandblocker.nukkit.event.player.NukkitBlockedCommandEvent;
import net.kyori.event.EventSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 * The Nukkit event manager.
 *
 * @param <T> The event type.
 */
public final class NukkitEventManager<T extends CommandBlockerEvent> implements EventSubscriber<T> {

  @Override
  public void on(final @NotNull T event) {
    if (event instanceof CommandCreateEvent) {
      final NukkitCommandCreateEvent commandCreateEvent = new NukkitCommandCreateEvent((CommandCreateEvent) event);
      CommandBlockerNukkit.get().getServer().getPluginManager().callEvent(commandCreateEvent);
    } else if (event instanceof CommandRemoveEvent) {
      final NukkitCommandRemoveEvent commandRemoveEvent = new NukkitCommandRemoveEvent((CommandRemoveEvent) event);
      CommandBlockerNukkit.get().getServer().getPluginManager().callEvent(commandRemoveEvent);
    } else if (event instanceof BlockedCommandEvent) {
      final NukkitBlockedCommandEvent blockedCommandEvent = new NukkitBlockedCommandEvent((BlockedCommandEvent) event);
      CommandBlockerNukkit.get().getServer().getPluginManager().callEvent(blockedCommandEvent);
    } else if (event instanceof CommandEvent) {
      final NukkitCommandEvent commandEvent = new NukkitCommandEvent((CommandEvent) event);
      CommandBlockerNukkit.get().getServer().getPluginManager().callEvent(commandEvent);
    } else {
      final NukkitCommandBlockerEvent commandEvent = new NukkitCommandBlockerEvent(event);
      CommandBlockerNukkit.get().getServer().getPluginManager().callEvent(commandEvent);
    }
  }
}
