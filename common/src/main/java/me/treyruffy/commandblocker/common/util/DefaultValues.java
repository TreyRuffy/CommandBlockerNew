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
package me.treyruffy.commandblocker.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.treyruffy.commandblocker.common.config.Configuration;
import me.treyruffy.commandblocker.common.config.ConfigurationFiles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

/**
 * Gets the default values from the configuration.
 *
 * @since 3.0.0
 */
public final class DefaultValues {

  private DefaultValues() {
    // Do nothing
  }

  /**
   * Gets the default permission for the blocked command.
   *
   * @param command the command
   * @return the default permission
   * @since 3.0.0
   */
  @NotNull
  public static String defaultPermission(final @NotNull String command) {
    final Configuration configuration = new Configuration(ConfigurationFiles.CONFIGURATION);
    final ConfigurationNode configRoot = configuration.rootNode();
    final String permission = configRoot.node("default", "permission").getString();

    if (permission == null) {
      Universal.get().universalMethods().log().warn("A default permission is not set in the configuration");
      return "commandblocker." + command;
    }
    return permission.replace("%command%", command.trim()).replace("%c", command.trim());
  }

  /**
   * Gets the default messages for a blocked command.
   *
   * @param command the command
   * @return the default messages
   * @since 3.0.0
   */
  @NotNull
  public static List<Component> defaultMessage(final @Nullable String command) {
    List<String> message = null;
    final Configuration configuration = new Configuration(ConfigurationFiles.CONFIGURATION);
    final ConfigurationNode configRoot = configuration.rootNode();

    if (!configRoot.node("default", "messages").virtual()) {
      if (configRoot.node("default", "messages").isList()) {
        try {
          message = configRoot.node("default", "messages").getList(String.class);
        } catch (final SerializationException exception) {
          ExceptionHandler.handleException(exception);
        }
      } else {
        message = new ArrayList<>();
        message.add(configRoot.node("default", "messages").getString());
      }
    }

    if (message == null || message.isEmpty()) {
      Universal.get().universalMethods().log().warn("A default message is not set in the configuration");
      return Collections.singletonList(PlainTextComponentSerializer.plainText().deserialize("<red>You do not have " + "permission to use this command!"));
    }

    if (command != null) {
      message.replaceAll(s -> s.replace("%command%", command.trim()).replace("%c", command.trim()));
    }

    final List<Component> componentsList = new ArrayList<>();

    for (int i = 0; i < message.size(); i++) {
      componentsList.add(i, PlainTextComponentSerializer.plainText().deserialize(message.get(i)));
    }

    return componentsList;
  }

}
