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
package me.treyruffy.commandblocker.bukkit.util;

import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import me.treyruffy.commandblocker.bukkit.CommandBlockerBukkit;
import me.treyruffy.commandblocker.bukkit.logger.Log;
import me.treyruffy.commandblocker.bukkit.player.CommandBlockerBukkitPlayer;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Logger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Sets up the universal methods for Bukkit.
 */
public class SetupValues implements CommandBlockerValues<Player> {

  @Override
  public @NotNull String commandBlockerVersion() {
    return CommandBlockerBukkit.get().getDescription().getVersion();
  }

  @Override
  public @NotNull String serverVersion() {
    return CommandBlockerBukkit.get().getServer().getVersion();
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
    Bukkit.dispatchCommand(CommandBlockerBukkit.get().getServer().getConsoleSender(), command);
  }

  @Override
  public void executePlayerCommand(final @NotNull CommandBlockerPlayers<Player> player, final @NotNull String command) {
    player.performCommand(command);
  }

  @Override
  public void sendMessage(final @NotNull CommandBlockerPlayers<Player> commandSender, final @NotNull Component message) {
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
    //    final ConfigurationNode languageRoot = CommandBlockerBukkit.get().language();
    //    final ConfigurationNode translationNode = languageRoot.node(key);
    //    final String translation = translationNode.virtual() ? key : translationNode.getString();
    //
    //    if (translation == null) {
    //      return Component.text(key);
    //    }

    if (sender instanceof Player) {
      final CommandBlockerPlayers<Player> cbPlayer = new CommandBlockerBukkitPlayer((Player) sender);
      return this.translateVariables().stringToComponent(key/*translation*/, cbPlayer);
    }
    return this.translateVariables().stringToComponent(key/*translation*/);
  }

}
