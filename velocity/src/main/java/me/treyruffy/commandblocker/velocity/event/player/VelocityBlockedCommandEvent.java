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
package me.treyruffy.commandblocker.velocity.event.player;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.proxy.Player;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.velocity.event.VelocityCommandBlockerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player's command is blocked.
 */
public class VelocityBlockedCommandEvent extends VelocityCommandBlockerEvent implements ResultedEvent<ResultedEvent.GenericResult> {

  /**
   * Creates a new blocked command event.
   *
   * @param apiEvent the {@link me.treyruffy.commandblocker.api.event.CommandBlockerEvent}
   */
  public VelocityBlockedCommandEvent(final BlockedCommandEvent apiEvent) {
    super(apiEvent);
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

  @Override
  public GenericResult getResult() {
    return this.apiEvent().cancelled() ? GenericResult.denied() : GenericResult.allowed();
  }

  @Override
  public void setResult(final GenericResult result) {
    this.apiEvent().cancelled(result == GenericResult.denied());
  }
}
