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
package me.treyruffy.commandblocker.spongelegacy.util;

import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Logger;
import me.treyruffy.commandblocker.spongelegacy.CommandBlockerSpongeLegacy;
import me.treyruffy.commandblocker.spongelegacy.logger.Log;
import me.treyruffy.commandblocker.spongelegacy.player.CommandBlockerSpongePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

/**
 * Sets up the universal methods for Sponge.
 */
public class SetupValues implements CommandBlockerValues<Player> {
  @Override
  public @NotNull String commandBlockerVersion() {
    return CommandBlockerSpongeLegacy.get().plugin().getVersion().orElse("");
  }

  @Override
  public @NotNull String serverVersion() {
    return Sponge.getGame().getPlatform().getMinecraftVersion().getName();
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
    Sponge.getServer().getConsole().getCommandSource().ifPresent(source -> Sponge.getCommandManager().process(source,
      command));
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
    if (sender instanceof Player) {
      final CommandBlockerPlayers<Player> cbPlayer = new CommandBlockerSpongePlayer((Player) sender);
      return this.translateVariables().stringToComponent(key, cbPlayer);
    }
    return this.translateVariables().stringToComponent(key);
  }
}
