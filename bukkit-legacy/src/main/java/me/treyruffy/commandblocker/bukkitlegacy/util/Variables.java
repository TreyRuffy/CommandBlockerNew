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
package me.treyruffy.commandblocker.bukkitlegacy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.clip.placeholderapi.PlaceholderAPI;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Bukkit Legacy variable translator.
 */
public class Variables implements ComponentTranslator {

  @Override
  public @NotNull Component stringToComponent(final @NotNull String message) {
    return this.stringToComponent(message, null);
  }

  @Override
  public @NotNull Component stringToComponent(final @NotNull String message,
                                              final @Nullable CommandBlockerPlayers<?> player) {
    return this.stringToComponent(message, player, null);
  }

  @Override
  public @NotNull Component stringToComponent(final @NotNull String message,
                                              final @Nullable CommandBlockerPlayers<?> player,
                                              final @Nullable HashMap<String, Component> additionalComponentVariables) {
    final List<TagResolver> tagResolvers = new ArrayList<>();
    tagResolvers.add(TagResolver.standard());

    if (player != null && player.player() instanceof Player) {
      tagResolvers.add(this.playerTags((Player) player.player()));

      if (me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().placeholderApiEnabled()) {
        tagResolvers.add(this.papiTag((Player) player.player()));
      }

      tagResolvers.add(
          TagResolver.resolver(
          TagResolver.resolver("checkmark", Tag.selfClosingInserting(Component.text("✔ "))),
          TagResolver.resolver("crossmark", Tag.selfClosingInserting(Component.text("✘ ")))
        )
      );
    } else {
      tagResolvers.add(
        TagResolver.resolver(
          TagResolver.resolver("checkmark", Tag.selfClosingInserting(Component.text(""))),
          TagResolver.resolver("crossmark", Tag.selfClosingInserting(Component.text("")))
        )
      );
    }

    tagResolvers.add(this.serverTags());

    if (additionalComponentVariables != null) {
      tagResolvers.add(this.additionalPlaceholderTags(additionalComponentVariables));
    }
    return MiniMessage.miniMessage().deserialize(message, TagResolver.resolver(tagResolvers));
  }

  @Override
  public @NotNull Component componentVariableTranslate(final @NotNull Component component,
                                                       final @Nullable CommandBlockerPlayers<?> player,
                                                       final @Nullable HashMap<String, Component> additionalComponentVariables) {
    return this.stringToComponent(PlainTextComponentSerializer.plainText().serialize(component), player,
      additionalComponentVariables);
  }

  @NotNull TagResolver playerTags(final @NotNull Player player) {
    return TagResolver.resolver(
      TagResolver.resolver("player_name", Tag.selfClosingInserting(Component.text(player.getName()))),
      TagResolver.resolver("player_uuid", Tag.selfClosingInserting(Component.text(player.getUniqueId().toString()))),
      TagResolver.resolver("player_isop", Tag.selfClosingInserting(Component.text(String.valueOf(player.isOp())))),
      TagResolver.resolver("player_displayname", Tag.selfClosingInserting(Component.text(player.getDisplayName()))),
      TagResolver.resolver("player_server_name", Tag.selfClosingInserting(Component.text(player.getServer().getName()))),
      TagResolver.resolver("player_server_ip", Tag.selfClosingInserting(Component.text(player.getServer().getIp()))),
      TagResolver.resolver("player_server_port", Tag.selfClosingInserting(Component.text(player.getServer().getPort()))),
      TagResolver.resolver("player_world", Tag.selfClosingInserting(Component.text(player.getWorld().getName()))),
      TagResolver.resolver("player_x", Tag.selfClosingInserting(Component.text(player.getLocation().getBlockX()))),
      TagResolver.resolver("player_y", Tag.selfClosingInserting(Component.text(player.getLocation().getBlockY()))),
      TagResolver.resolver("player_z", Tag.selfClosingInserting(Component.text(player.getLocation().getBlockZ()))),
      TagResolver.resolver("player_gamemode", Tag.selfClosingInserting(Component.text(player.getGameMode().name()))),
      TagResolver.resolver("player_health", Tag.selfClosingInserting(Component.text(player.getHealth()))),
      TagResolver.resolver("player_food", Tag.selfClosingInserting(Component.text(player.getFoodLevel()))),
      TagResolver.resolver("player_exp_level", Tag.selfClosingInserting(Component.text(player.getLevel()))),
      TagResolver.resolver("player_exp_points", Tag.selfClosingInserting(Component.text(player.getExp()))),
      TagResolver.resolver("player_time", Tag.selfClosingInserting(Component.text(player.getPlayerTime()))),
      TagResolver.resolver("player_time_offset", Tag.selfClosingInserting(Component.text(player.getPlayerTimeOffset()))),
      TagResolver.resolver("player_firstplayed", Tag.selfClosingInserting(Component.text(player.getFirstPlayed())))
    );
  }

  @NotNull TagResolver serverTags() {
    return TagResolver.resolver(
      TagResolver.resolver("server_name", Tag.selfClosingInserting(Component.text(me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getServer().getName()))),
      TagResolver.resolver("server_ip", Tag.selfClosingInserting(Component.text(me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getServer().getIp()))),
      TagResolver.resolver("server_port", Tag.selfClosingInserting(Component.text(me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getServer().getPort()))),
      TagResolver.resolver("server_motd", Tag.selfClosingInserting(Component.text(me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getServer().getMotd()))),
      TagResolver.resolver("server_max_players", Tag.selfClosingInserting(Component.text(me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getServer().getMaxPlayers()))),
      TagResolver.resolver("server_online_players", Tag.selfClosingInserting(Component.text(me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getServer().getOnlinePlayers().size()))),
      TagResolver.resolver("server_version", Tag.selfClosingInserting(Component.text(me.treyruffy.commandblocker.bukkitlegacy.CommandBlockerBukkitLegacy.get().getServer().getVersion()))),
      TagResolver.resolver("server_bukkit_version", Tag.selfClosingInserting(Component.text(CommandBlockerBukkitLegacy.get().getServer().getBukkitVersion())))
    );
  }

  @NotNull TagResolver additionalPlaceholderTags(final @NotNull HashMap<String, Component> additionalComponentVariables) {
    final TagResolver.Builder builder = TagResolver.builder();
    for (final Map.Entry<String, Component> entry : additionalComponentVariables.entrySet()) {
      builder.resolver(TagResolver.resolver(entry.getKey(), Tag.selfClosingInserting(entry.getValue())));
    }
    return builder.build();
  }

  // PlaceholderAPI Tag Resolver
  // Based on https://gist.github.com/kezz/ff1bcb8c8db4e113e0119c210026d5ad
  /**
   * Creates a tag resolver capable of resolving PlaceholderAPI tags for a given player.
   *
   * @param player the player
   * @return the tag resolver
   */
  @NotNull TagResolver papiTag(final @NotNull Player player) {
    return TagResolver.resolver("papi", (argumentQueue, context) -> {
      // Get the string placeholder that they want to use.
      final String papiPlaceholder = argumentQueue.popOr("papi tag requires an argument").value();

      // Then get PAPI to parse the placeholder for the given player.
      final String parsedPlaceholder = PlaceholderAPI.setPlaceholders(player, '%' + papiPlaceholder + '%');

      // We need to turn this ugly legacy string into a nice component.
      final Component componentPlaceholder = LegacyComponentSerializer.legacySection().deserialize(parsedPlaceholder);

      // Finally, return the tag instance to insert the placeholder!
      return Tag.selfClosingInserting(componentPlaceholder);
    });
  }
}
