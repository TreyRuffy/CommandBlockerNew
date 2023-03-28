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
package me.treyruffy.commandblocker.common.blocking;

import java.util.Set;
import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.InvalidCommandException;
import me.treyruffy.commandblocker.common.util.Universal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if tabbed commands should be blocked.
 *
 * @since 3.0.0
 */
public class TabBlocking {

  private final @NotNull Set<AbstractCommand> commandList;
  private final boolean opCommand;
  private final @NotNull CommandBlockerPlayers<?> player;
  private final @NotNull String[] commandArguments;
  private @Nullable AbstractCommand blockedCommand;

  /**
   * Creates an object to check if commands should be blocked from tab completion.
   *
   * @param player           the player who is getting tabbed commands
   * @param commandArguments the command arguments
   * @since 3.0.0
   */
  public TabBlocking(final @NotNull CommandBlockerPlayers<?> player, final @NotNull String[] commandArguments) {
    this.player = player;
    this.commandArguments = commandArguments;
    if (player.isOp()) {
      this.commandList = CommandBlockerAPI.get().modifySets().commands(CommandBlockerTypes.OPERATORS);
      this.opCommand = true;
    } else {
      this.commandList = CommandBlockerAPI.get().modifySets().commands(CommandBlockerTypes.REGULAR_PLAYERS);
      this.opCommand = false;
    }
  }

  /**
   * Checks if the command should be blocked from tab completion.
   *
   * @return true if the command should be blocked
   * @since 3.0.0
   */
  public boolean isTabBlocked() {
    // Checks if the list of blocked commands has any entries
    if (this.commandList.size() == 0) {
      return false;
    }

    // Check through the list of blocked commands
    for (final AbstractCommand command : this.commandList) {

      try {
        if (command.disableTabComplete() && command.isTabBlocked(this.player, this.commandArguments, this.opCommand)) {
          this.blockedCommand = command;
          break;
        }
      } catch (final InvalidCommandException e) {
        Universal.get().universalMethods().log().warn(e.message(), e.throwable());
      }
    }

    return CheckBlock.checkPlayerBlock(this.player, this.blockedCommand, this.opCommand);
  }
}
