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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.InvalidCommandException;
import me.treyruffy.commandblocker.common.util.ExceptionHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks which commands should be removed from the list of executable commands.
 *
 * @since 3.0.0
 */
public class CommandListBlock {

  private final @NotNull Set<AbstractCommand> blockedCommandList;
  private final boolean opCommand;
  private final @NotNull CommandBlockerPlayers<?> player;
  private final @Nullable String rawMessage;
  private final @NotNull Collection<String> commandList;

  /**
   * Creates an object to check which commands should be blocked from the list of executable commands.
   *
   * @param player      the player who is listing the executable commands
   * @param commandList the list of commands to check
   * @since 3.0.0
   */
  public CommandListBlock(final @NotNull CommandBlockerPlayers<?> player,
                          final @NotNull Collection<String> commandList) {
    this(player, null, commandList);
  }

  /**
   * Creates an object to check which commands should be blocked from the list of executable commands.
   *
   * @param player      the player who is listing the executable commands
   * @param rawMessage  the full message the player has in the cursor
   * @param commandList the list of commands to check
   * @since 3.0.0
   */
  public CommandListBlock(final @NotNull CommandBlockerPlayers<?> player,
                          final @Nullable String rawMessage,
                          final @NotNull Collection<String> commandList) {
    this.player = player;
    this.rawMessage = rawMessage;
    this.commandList = commandList;
    if (player.isOp()) {
      this.blockedCommandList = CommandBlockerAPI.get().modifySets().commands(CommandBlockerTypes.OPERATORS);
      this.opCommand = true;
    } else {
      this.blockedCommandList = CommandBlockerAPI.get().modifySets().commands(CommandBlockerTypes.REGULAR_PLAYERS);
      this.opCommand = false;
    }
  }

  /**
   * Removes blocked commands from the list of executable commands.
   *
   * @return the new list of executable commands
   * @since 3.0.0
   */
  public @NotNull Collection<String> removeBlockedCommands() {
    final List<String> newCommandList = new ArrayList<>(this.commandList);
    if (!this.commandList.isEmpty()) {
      for (final String command : this.commandList) {
        for (final AbstractCommand blockedCommand : this.blockedCommandList) {
          try {
            if (blockedCommand.disableTabComplete() && !blockedCommand.commandListBlock(this.player,
              this.rawMessage == null ? command : this.rawMessage, this.opCommand)) {
              continue;
            }
          } catch (final InvalidCommandException e) {
            ExceptionHandler.handleException(e);
            continue;
          }

          if (blockedCommand.disableTabComplete() && CheckBlock.checkPlayerBlock(this.player, blockedCommand,
            this.opCommand)) {
            newCommandList.remove(command);
            break;
          }
        }
      }
    }
    return newCommandList;
  }
}
