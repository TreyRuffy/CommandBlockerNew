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
package me.treyruffy.commandblocker.spongelegacy.player;

import java.util.UUID;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.spongelegacy.CommandBlockerSpongeLegacy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

/**
 * The player object for Command Blocker Sponge.
 */
public class CommandBlockerSpongePlayer implements CommandBlockerPlayers<Player> {

  private final Player player;

  /**
   * Sets up the player for Command Blocker.
   *
   * @param player the player to set up
   */
  public CommandBlockerSpongePlayer(final Player player) {
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
    return this.player.getDisplayNameData().displayName().copy().toString();
  }

  @Override
  public boolean isOp() {
    return false;
  }

  @Override
  public @NotNull UUID uuid() {
    return this.player.getUniqueId();
  }

  @Override
  public void sendMessage(final @NotNull Component message) {
    CommandBlockerSpongeLegacy.adventure().player(this.player).sendMessage(message);
  }

  @Override
  public void performCommand(final @NotNull String command) {
    this.player.getCommandSource().ifPresent(source -> Sponge.getCommandManager().process(source, command));
  }

  @Override
  public void sendActionBar(final @NotNull Component message) {
    CommandBlockerSpongeLegacy.adventure().player(this.player).sendActionBar(message);
  }

  @Override
  public void showTitle(final @NotNull Title title) {
    CommandBlockerSpongeLegacy.adventure().player(this.player).showTitle(title);
  }

  @Override
  public boolean hasPermission(final @NotNull String permission) {
    return this.player.hasPermission(permission);
  }

  @Override
  public @NotNull String world() {
    return this.player.getWorld().getName();
  }
}
