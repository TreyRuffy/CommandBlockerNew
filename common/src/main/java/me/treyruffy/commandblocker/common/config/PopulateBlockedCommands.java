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
package me.treyruffy.commandblocker.common.config;

import java.util.List;
import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.command.AbstractCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.utils.CommandMapper;
import me.treyruffy.commandblocker.api.utils.ModifySets;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

/**
 * Populates the blocked sets from the configuration.
 *
 * @since 3.0.0
 */
public final class PopulateBlockedCommands {

  private PopulateBlockedCommands() {
    // Utility class
  }

  /**
   * Populates the sets of {@link AbstractCommand}s from the configuration.
   *
   * @since 3.0.0
   */
  public static void populateSet() throws SerializationException {
    final Configuration blockedCommandsConfig = new Configuration(ConfigurationFiles.BLOCKED_COMMANDS);
    final ConfigurationNode rootNode = blockedCommandsConfig.rootNode();
    if (rootNode.virtual()) {
      return;
    }

    for (final CommandBlockerTypes commandBlockerTypes : CommandBlockerTypes.values()) {
      final ConfigurationNode blockedCommandsNode = rootNode.node(commandBlockerTypes.configurationSection());
      if (blockedCommandsNode.virtual()) {
        continue;
      }
      final List<CommandMapper.ConfigurationCommand> blockedCommandsList;
      blockedCommandsList = blockedCommandsNode.getList(CommandMapper.ConfigurationCommand.class);
      if (blockedCommandsList == null) {
        continue;
      }
      for (final CommandMapper.ConfigurationCommand blockedCommand : blockedCommandsList) {
        final ModifySets modifySets = CommandBlockerAPI.get().modifySets();
        final AbstractCommand command = CommandMapper.commandFromConfig(blockedCommand);
        if (command != null) {
          modifySets.addCommand(commandBlockerTypes, command);
        }
      }
    }
  }
}
