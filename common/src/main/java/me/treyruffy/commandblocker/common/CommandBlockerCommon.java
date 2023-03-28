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
package me.treyruffy.commandblocker.common;

import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.command.BasicCommand;
import me.treyruffy.commandblocker.api.command.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.utils.CommandMapper;
import me.treyruffy.commandblocker.common.config.Configuration;
import me.treyruffy.commandblocker.common.config.ConfigurationFiles;
import me.treyruffy.commandblocker.common.config.PopulateBlockedCommands;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.ExceptionHandler;
import me.treyruffy.commandblocker.common.util.Universal;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

/**
 * The main CommandBlocker common class.
 *
 * @since 3.0.0
 */
public class CommandBlockerCommon {

  final private boolean debug = true;

  /**
   * If debug is enabled, this will execute this method.
   *
   * @since 3.0.0
   */
  public void test() {
    if (this.debug) {
      final CommandBlockerValues<?> commandBlockerValues = Universal.get().universalMethods();
      final Configuration configuration = new Configuration(ConfigurationFiles.CONFIGURATION);
      final ConfigurationNode configRoot = configuration.rootNode();

      if (configRoot.node("Testing", "Debug").virtual()) {
        try {
          configRoot.node("Testing", "Debug").set("This is a test in order to make sure the configuration works properly");
        } catch (final SerializationException exception) {
          ExceptionHandler.handleException(exception);
        }
        configuration.save();
      }

      commandBlockerValues.log().info("Testing Debug: " + configRoot.node("Testing", "Debug").getString());

      final String[] command = new String[] {"testing123", "test2"};
      final BasicCommand basicCommand = new BasicCommand(command, null,
        null, null, null, null, true, null,
        null, null);
      CommandBlockerAPI.get().modifySets().addCommand(CommandBlockerTypes.REGULAR_PLAYERS, basicCommand);
      CommandMapper.commandToConfiguration(basicCommand);
    }
    try {
      PopulateBlockedCommands.populateSet();
    } catch (final SerializationException e) {
      ExceptionHandler.handleException(e);
    }
  }

  /**
   * Checks if debug is enabled.
   *
   * @return debug is enabled
   * @since 3.0.0
   */
  public boolean debug() {
    return this.debug;
  }
}
