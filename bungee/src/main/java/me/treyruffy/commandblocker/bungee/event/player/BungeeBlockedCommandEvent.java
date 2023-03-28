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
package me.treyruffy.commandblocker.bungee.event.player;

import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.bungee.event.BungeeCommandBlockerEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player's command is blocked.
 */
public class BungeeBlockedCommandEvent extends BungeeCommandBlockerEvent implements Cancellable {

  /**
   * Creates a new blocked command event.
   *
   * @param apiEvent the {@link me.treyruffy.commandblocker.api.event.CommandBlockerEvent}
   */
  public BungeeBlockedCommandEvent(final BlockedCommandEvent apiEvent) {
    super(apiEvent);
  }

  @Override
  public boolean isCancelled() {
    return this.apiEvent().cancelled();
  }

  @Override
  public void setCancelled(final boolean cancel) {
    this.apiEvent().cancelled(cancel);
  }

  @Override
  public @NotNull BlockedCommandEvent apiEvent() {
    return (BlockedCommandEvent) super.apiEvent();
  }

  /**
   * The player who executed the blocked command.
   *
   * @return a {@link ProxiedPlayer} object of the player who executed the blocked command
   */
  public ProxiedPlayer player() {
    return (ProxiedPlayer) this.apiEvent().player().player();
  }
}
