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
package me.treyruffy.commandblocker.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.nukkit.event.NukkitCommandBlockerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player's command is blocked.
 */
public class NukkitBlockedCommandEvent extends NukkitCommandBlockerEvent implements Cancellable {

  /**
   * Creates a new blocked command event.
   *
   * @param apiEvent the {@link me.treyruffy.commandblocker.api.event.CommandBlockerEvent}
   */
  public NukkitBlockedCommandEvent(final BlockedCommandEvent apiEvent) {
    super(apiEvent);
  }

  @Override
  public boolean isCancelled() {
    return this.apiEvent().cancelled();
  }

  @Override
  public void setCancelled() {
    this.apiEvent().cancelled(true);
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
   * @return a {@link Player} object of the player who executed the blocked command
   */
  public Player player() {
    return (Player) this.apiEvent().player().player();
  }
}
