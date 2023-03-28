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
package me.treyruffy.commandblocker.velocity.listeners;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.proxy.Player;
import me.treyruffy.commandblocker.common.blocking.BlockedCommandOutput;
import me.treyruffy.commandblocker.common.blocking.CheckBlock;
import me.treyruffy.commandblocker.common.config.Configuration;
import me.treyruffy.commandblocker.common.config.ConfigurationFiles;
import me.treyruffy.commandblocker.velocity.player.CommandBlockerVelocityPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

/**
 * Checks if the player runs a blocked command.
 */
public class CommandListener {

  /**
   * Checks if the command is blocked.
   *
   * @param event the player command preprocess event
   */
  @Subscribe(order = PostOrder.FIRST)
  public void onCommand(final @NotNull CommandExecuteEvent event) {
    final CommandSource commandSource = event.getCommandSource();
    if (!(commandSource instanceof Player)) {
      return;
    }
    final Player player = (Player) commandSource;
    final CommandBlockerVelocityPlayer cbPlayer = new CommandBlockerVelocityPlayer(player);
    final @Nullable BlockedCommandOutput checkBlock = new CheckBlock(cbPlayer, event.getCommand().split(" ")).blockedOutput();

    if (checkBlock != null) {
      // TODO: Fix signed blocking
      final Configuration configuration = new Configuration(ConfigurationFiles.CONFIGURATION);
      final ConfigurationNode configRoot = configuration.rootNode();

      if (configRoot.node("commands", "forward").getBoolean()) {
        event.setResult(CommandExecuteEvent.CommandResult.forwardToServer());
      } else {
        event.setResult(CommandExecuteEvent.CommandResult.denied());
      }

      checkBlock.sendAll();
    }
  }
}
