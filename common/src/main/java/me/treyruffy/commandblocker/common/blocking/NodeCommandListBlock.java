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

import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Set;
import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.InvalidCommandException;
import me.treyruffy.commandblocker.common.util.ExceptionHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Checks which commands should be removed from the list of node commands.
 *
 * @since 3.0.0
 */
public class NodeCommandListBlock {

  private final @NotNull Set<AbstractCommand> blockedCommandList;
  private final boolean opCommand;
  private final @NotNull CommandBlockerPlayers<?> player;
  private final @NotNull RootCommandNode<?> rootCommandNode;

  /**
   * Creates an object to check which commands should be blocked from the list of executable commands.
   *
   * @param player      the player who is listing the executable commands
   * @param rootCommandNode the root command node
   * @since 3.0.0
   */
  public NodeCommandListBlock(final @NotNull CommandBlockerPlayers<?> player,
                          final @NotNull RootCommandNode<?> rootCommandNode) {
    this.player = player;
    this.rootCommandNode = rootCommandNode;
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
   * @since 3.0.0
   */
  public void removeBlockedCommands() {
    if (!this.rootCommandNode.getChildren().isEmpty()) {
      this.rootCommandNode.getChildren().removeIf(commandNode -> {
        for (final AbstractCommand blockedCommand : this.blockedCommandList) {
          try {
            if (!blockedCommand.disableTabComplete() || !blockedCommand.commandListBlock(this.player,
              commandNode.getName(), this.opCommand)) {
              continue;
            }
          } catch (final InvalidCommandException e) {
            ExceptionHandler.handleException(e);
            continue;
          }
          if (CheckBlock.checkPlayerBlock(this.player, blockedCommand, this.opCommand)) {
            return true;
          }
        }
        return false;
      });
    }
  }
}
