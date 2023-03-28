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
package me.treyruffy.commandblocker.nukkit.player;

import cn.nukkit.Player;
import java.util.UUID;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

/**
 * The player object for Command Blocker Nukkit.
 */
public class CommandBlockerNukkitPlayer implements CommandBlockerPlayers<Player> {

  private final Player player;

  /**
   * Sets up the player for Command Blocker.
   *
   * @param player the player to set up
   */
  public CommandBlockerNukkitPlayer(final Player player) {
    this.player = player;
  }

  @Override
  public @NotNull Player player() {
    return this.player;
  }

  @Override
  public @NotNull String name() {
    return this.player.getName();
  }

  @Override
  public @NotNull String displayName() {
    return this.player.getDisplayName();
  }

  @Override
  public boolean isOp() {
    return this.player.isOp();
  }

  @Override
  public @NotNull UUID uuid() {
    return this.player.getUniqueId();
  }

  @Override
  public void sendMessage(final @NotNull Component message) {
    this.player.sendChat(LegacyComponentSerializer.legacySection().serialize(message));
  }

  @Override
  public void performCommand(final @NotNull String command) {
    this.player.chat("/" + command);
  }

  @Override
  public void sendActionBar(final @NotNull Component message) {
    this.player.sendActionBar(LegacyComponentSerializer.legacySection().serialize(message));
  }

  @Override
  public void showTitle(final @NotNull Title title) {
    final String legacyTitle = LegacyComponentSerializer.legacySection().serialize(title.title());
    final String legacySubtitle = LegacyComponentSerializer.legacySection().serialize(title.subtitle());
    final Title.Times times = title.times();
    if (times != null) {
      final int fadeIn = (int) times.fadeIn().getSeconds();
      final int stay = (int) times.stay().getSeconds();
      final int fadeOut = (int) times.fadeOut().getSeconds();

      this.player.sendTitle(legacyTitle, legacySubtitle, fadeIn, stay, fadeOut);
    } else {
      this.player.sendTitle(legacyTitle, legacySubtitle);
    }
  }

  @Override
  public boolean hasPermission(final @NotNull String permission) {
    return this.player.hasPermission(permission);
  }

  @Override
  public @NotNull String world() {
    return this.player.getLevel().getName();
  }
}
