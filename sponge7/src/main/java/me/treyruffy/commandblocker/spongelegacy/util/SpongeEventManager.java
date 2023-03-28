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
package me.treyruffy.commandblocker.spongelegacy.util;

import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.api.event.command.CommandCreateEvent;
import me.treyruffy.commandblocker.api.event.command.CommandEvent;
import me.treyruffy.commandblocker.api.event.command.CommandRemoveEvent;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.spongelegacy.event.SpongeLegacyCommandBlockerEvent;
import me.treyruffy.commandblocker.spongelegacy.event.command.SpongeLegacyCommandCreateEvent;
import me.treyruffy.commandblocker.spongelegacy.event.command.SpongeLegacyCommandEvent;
import me.treyruffy.commandblocker.spongelegacy.event.command.SpongeLegacyCommandRemoveEvent;
import me.treyruffy.commandblocker.spongelegacy.event.player.SpongeLegacyBlockedCommandEvent;
import net.kyori.event.EventSubscriber;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;

/**
 * The Sponge event manager.
 *
 * @param <T> The event type.
 */
public final class SpongeEventManager<T extends CommandBlockerEvent> implements EventSubscriber<T> {

  @Override
  public void on(final @NotNull T event) {
    if (event instanceof CommandCreateEvent) {
      final SpongeLegacyCommandCreateEvent commandCreateEvent = new SpongeLegacyCommandCreateEvent((CommandCreateEvent) event);
      Sponge.getEventManager().post(commandCreateEvent);
    } else if (event instanceof CommandRemoveEvent) {
      final SpongeLegacyCommandRemoveEvent commandRemoveEvent = new SpongeLegacyCommandRemoveEvent((CommandRemoveEvent) event);
      Sponge.getEventManager().post(commandRemoveEvent);
    } else if (event instanceof BlockedCommandEvent) {
      final SpongeLegacyBlockedCommandEvent blockedCommandEvent = new SpongeLegacyBlockedCommandEvent((BlockedCommandEvent) event);
      Sponge.getEventManager().post(blockedCommandEvent);
    } else if (event instanceof CommandEvent) {
      final SpongeLegacyCommandEvent commandEvent = new SpongeLegacyCommandEvent((CommandEvent) event);
      Sponge.getEventManager().post(commandEvent);
    } else {
      final SpongeLegacyCommandBlockerEvent commandEvent = new SpongeLegacyCommandBlockerEvent(event);
      Sponge.getEventManager().post(commandEvent);
    }
  }
}
