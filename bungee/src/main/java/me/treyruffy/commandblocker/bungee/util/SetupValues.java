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
package me.treyruffy.commandblocker.bungee.util;

import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import me.treyruffy.commandblocker.bungee.CommandBlockerBungee;
import me.treyruffy.commandblocker.bungee.logger.Log;
import me.treyruffy.commandblocker.bungee.player.CommandBlockerBungeePlayer;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Sets up the universal methods for Bungee.
 */
public class SetupValues implements CommandBlockerValues<ProxiedPlayer> {

  @Override
  public @NotNull String commandBlockerVersion() {
    return CommandBlockerBungee.get().getDescription().getVersion();
  }

  @Override
  public @NotNull String serverVersion() {
    return CommandBlockerBungee.get().getProxy().getVersion();
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
    CommandBlockerBungee.get().getProxy().getPluginManager().dispatchCommand(CommandBlockerBungee.get().getProxy().getConsole(), command);
  }

  @Override
  public void executePlayerCommand(final @NotNull CommandBlockerPlayers<ProxiedPlayer> player, final @NotNull String command) {
    player.performCommand(command);
  }

  @Override
  public void sendMessage(final @NotNull CommandBlockerPlayers<ProxiedPlayer> commandSender, final @NotNull Component message) {
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
    if (sender instanceof ProxiedPlayer) {
      final CommandBlockerPlayers<ProxiedPlayer> cbPlayer = new CommandBlockerBungeePlayer((ProxiedPlayer) sender);
      return this.translateVariables().stringToComponent(key, cbPlayer);
    }
    return this.translateVariables().stringToComponent(key);
  }
}
