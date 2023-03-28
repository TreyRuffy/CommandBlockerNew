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

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.event.player.BlockedCommandEvent;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.InvalidCommandException;
import me.treyruffy.commandblocker.common.util.DefaultValues;
import me.treyruffy.commandblocker.common.util.Universal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Checks if the command should be blocked.
 *
 * @since 3.0.0
 */
public class CheckBlock {
  private final @NotNull Set<AbstractCommand> commandList;
  private final boolean opCommand;
  private final @NotNull CommandBlockerPlayers<?> player;
  private final @NotNull String[] commandArguments;
  private @Nullable AbstractCommand blockedCommand;

  /**
   * Creates an object to check commands.
   *
   * @param player           the player who executed the command
   * @param commandArguments the command arguments
   * @since 3.0.0
   */
  public CheckBlock(final @NotNull CommandBlockerPlayers<?> player, final @NotNull String[] commandArguments) {
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
   * Checks if the executed command should be blocked.
   *
   * @return {@code true} if the command should be blocked
   * @since 3.0.0
   */
  private boolean isBlocked() {
    // Checks if the list of blocked commands has any entries
    if (this.commandList.size() == 0) {
      return false;
    }

    // Check through the list of blocked commands
    for (final AbstractCommand command : this.commandList) {

      try {
        if (command.checkBlock(this.player, this.commandArguments, this.opCommand)) {
          this.blockedCommand = command;
          break;
        }
      } catch (final InvalidCommandException e) {
        Universal.get().universalMethods().log().warn(e.message(), e.throwable());
      }
    }

    if (checkPlayerBlock(this.player, this.blockedCommand, this.opCommand)) {
      final String executedCommand = String.join(" ", this.commandArguments);

      final BlockedCommandEvent event = new BlockedCommandEvent(this.player, this.blockedCommand, executedCommand);
      Universal.get().eventBus().post(event);
      return !event.cancelled();
    }

    return false;
  }

  /**
   * Checks if the executed command should be blocked.
   *
   * @param placeholders any placeholders for the message, title, actionbar, etc
   * @return null if the command is not blocked or the command block output
   * @since 3.0.0
   */
  public @Nullable BlockedCommandOutput blockedOutput(final @Nullable HashMap<String, String> placeholders) {
    if (this.isBlocked() && this.blockedCommand != null) {
      return new BlockedCommandOutput(this.player, this.blockedCommand, placeholders);
    } else {
      return null;
    }
  }

  /**
   * Checks if the executed command should be blocked.
   *
   * @return null if the command is not blocked or the command block output
   * @since 3.0.0
   */
  public @Nullable BlockedCommandOutput blockedOutput() {
    return this.blockedOutput(null);
  }

  /**
   * Checks if the player should be blocked from executing the command.
   *
   * @param blockedCommand the command to check
   * @param player         the player to check
   * @param opCommand      if the command is an operator blocked command
   * @return {@code true} if the player should be blocked from executing the command
   * @since 3.0.0
   */
  public static boolean checkPlayerBlock(final @NotNull CommandBlockerPlayers<?> player,
                                         final @Nullable AbstractCommand blockedCommand, final boolean opCommand) {
    // If the list did not come up with a command, do not block the executed command
    if (blockedCommand == null) {
      return false;
    }

    // If the player has the permission, do not block the executed command
    final String commandPermission = blockedCommand.permission() == null ?
      DefaultValues.defaultPermission(blockedCommand.id()) : blockedCommand.permission();
    if (!opCommand && !player.isOp() && commandPermission != null && (player.hasPermission(commandPermission) || player.hasPermission("commandblocker.*"))) {
      return false;
    }

    // Checks if the player is in a world where the command is blocked
    boolean blockedWorld = false;
    final String currentWorld = player.world();
    for (final String world : Objects.requireNonNull(blockedCommand.worlds())) {
      if (currentWorld.equalsIgnoreCase(world) || world.equalsIgnoreCase("all") || world.equalsIgnoreCase("*")) {
        blockedWorld = true;
        break;
      }
    }
    // If the player was not in a blocked world, return false
    if (!blockedWorld) {
      return false;
    }

    // Check if the player is whitelisted to execute the command
    final Set<UUID> whitelistedPlayers = blockedCommand.whitelistedPlayers();
    if (whitelistedPlayers != null) {
      return !whitelistedPlayers.contains(player.uuid());
    }

    return true;
  }
}
