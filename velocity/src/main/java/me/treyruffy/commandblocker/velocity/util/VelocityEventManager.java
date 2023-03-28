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
package me.treyruffy.commandblocker.velocity.util;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.api.event.command.CommandCreateEvent;
import me.treyruffy.commandblocker.api.event.command.CommandEvent;
import me.treyruffy.commandblocker.api.event.command.CommandRemoveEvent;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.velocity.CommandBlockerVelocity;
import me.treyruffy.commandblocker.velocity.event.VelocityCommandBlockerEvent;
import me.treyruffy.commandblocker.velocity.event.command.VelocityCommandCreateEvent;
import me.treyruffy.commandblocker.velocity.event.command.VelocityCommandEvent;
import me.treyruffy.commandblocker.velocity.event.command.VelocityCommandRemoveEvent;
import me.treyruffy.commandblocker.velocity.event.player.VelocityBlockedCommandEvent;
import net.kyori.event.EventSubscriber;
import org.jetbrains.annotations.NotNull;

/**
 * The Velocity event manager.
 *
 * @param <T> The event type.
 */
public final class VelocityEventManager<T extends CommandBlockerEvent> implements EventSubscriber<T> {

  @Override
  public void on(final @NotNull T event) {
    if (event instanceof CommandCreateEvent) {
      final VelocityCommandCreateEvent commandCreateEvent = new VelocityCommandCreateEvent((CommandCreateEvent) event);
      CommandBlockerVelocity.get().server().getEventManager().fire(commandCreateEvent);
    } else if (event instanceof CommandRemoveEvent) {
      final VelocityCommandRemoveEvent commandRemoveEvent = new VelocityCommandRemoveEvent((CommandRemoveEvent) event);
      CommandBlockerVelocity.get().server().getEventManager().fire(commandRemoveEvent);
    } else if (event instanceof BlockedCommandEvent) {
      final VelocityBlockedCommandEvent blockedCommandEvent = new VelocityBlockedCommandEvent((BlockedCommandEvent) event);
      CommandBlockerVelocity.get().server().getEventManager().fire(blockedCommandEvent);
    } else if (event instanceof CommandEvent) {
      final VelocityCommandEvent commandEvent = new VelocityCommandEvent((CommandEvent) event);
      CommandBlockerVelocity.get().server().getEventManager().fire(commandEvent);
    } else {
      final VelocityCommandBlockerEvent commandEvent = new VelocityCommandBlockerEvent(event);
      CommandBlockerVelocity.get().server().getEventManager().fire(commandEvent);
    }
  }
}
