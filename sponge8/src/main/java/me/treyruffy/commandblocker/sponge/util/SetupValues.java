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
package me.treyruffy.commandblocker.sponge.util;

import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Logger;
import me.treyruffy.commandblocker.sponge.CommandBlockerSponge;
import me.treyruffy.commandblocker.sponge.logger.Log;
import me.treyruffy.commandblocker.sponge.player.CommandBlockerSpongePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.exception.CommandException;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

/**
 * Sets up the universal methods for Sponge.
 */
public class SetupValues implements CommandBlockerValues<ServerPlayer> {
  @Override
  public @NotNull String commandBlockerVersion() {
    return CommandBlockerSponge.get().plugin().metadata().version().toString();
  }

  @Override
  public @NotNull String serverVersion() {
    return Sponge.game().platform().minecraftVersion().name();
  }

  @Override
  public @NotNull Logger log() {
    return new Log();
  }

  @Override
  public void setupMetrics() {

  }

  @Override
  public void executeConsoleCommand(final @NotNull String command) {
    try {
      Sponge.server().commandManager().process(command);
    } catch (final CommandException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void executePlayerCommand(final @NotNull CommandBlockerPlayers<ServerPlayer> player, final @NotNull String command) {
    player.performCommand(command);
  }

  @Override
  public void sendMessage(final @NotNull CommandBlockerPlayers<ServerPlayer> commandSender, final @NotNull Component message) {
    if (LegacyComponentSerializer.legacySection().serialize(message).equalsIgnoreCase(""))
      return;
    commandSender.sendMessage(message);
  }

  @Override
  public @NotNull ComponentTranslator translateVariables() {
    return new Variables();
  }

  @Override
  public @NotNull Component i18n(final @NotNull String key, final Object sender, final Object... formatters) {
    if (sender instanceof ServerPlayer) {
      final CommandBlockerPlayers<ServerPlayer> cbPlayer = new CommandBlockerSpongePlayer((ServerPlayer) sender);
      return this.translateVariables().stringToComponent(key, cbPlayer);
    }
    return this.translateVariables().stringToComponent(key);
  }
}
