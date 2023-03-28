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
package me.treyruffy.commandblocker.api.event.player;

import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import net.kyori.event.Cancellable;

/**
 * Called when a player's command is blocked.
 */
public class BlockedCommandEvent extends CommandBlockerEvent implements Cancellable {

  private final CommandBlockerPlayers<?> player;
  private final AbstractCommand command;
  private final String executedCommand;
  private boolean cancelled = false;

  /**
   * A new blocked command event.
   *
   * @param player the player who executed the blocked command
   * @param command the blocked command
   * @param executedCommand the actual typed command
   */
  public BlockedCommandEvent(final CommandBlockerPlayers<?> player, final AbstractCommand command, final String executedCommand) {
    this.player = player;
    this.command = command;
    this.executedCommand = executedCommand;
  }

  /**
   * The player who executed the blocked command.
   *
   * @return a {@link CommandBlockerPlayers} object containing the player data
   */
  public CommandBlockerPlayers<?> player() {
    return this.player;
  }

  /**
   * The blocked command.
   *
   * @return the CommandBlocker {@link AbstractCommand} that blocked the command from being executed
   */
  public AbstractCommand command() {
    return this.command;
  }

  /**
   * The actual command the {@link #player()} typed.
   *
   * @return the entire command the player executed
   */
  public String executedCommand() {
    return this.executedCommand;
  }

  @Override
  public boolean cancelled() {
    return this.cancelled;
  }

  @Override
  public void cancelled(final boolean cancelled) {
    this.cancelled = cancelled;
  }
}
