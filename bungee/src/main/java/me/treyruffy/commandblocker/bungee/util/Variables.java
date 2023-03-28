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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import me.treyruffy.commandblocker.bungee.CommandBlockerBungee;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Bungee variable translator.
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

    if (player != null && player.player() instanceof ProxiedPlayer) {
      tagResolvers.add(this.playerTags((ProxiedPlayer) player.player()));

      tagResolvers.add(
          TagResolver.resolver(
          TagResolver.resolver("checkmark", Tag.selfClosingInserting(Component.text("✔ "))),
          TagResolver.resolver("crossmark", Tag.selfClosingInserting(Component.text("✘ ")))
        )
      );
    } else {
      tagResolvers.add(TagResolver.resolver(
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

  @SuppressWarnings("deprecation")
  @NotNull TagResolver playerTags(final @NotNull ProxiedPlayer player) {
    return TagResolver.resolver(
      TagResolver.resolver("player_name", Tag.selfClosingInserting(Component.text(player.getName()))),
      TagResolver.resolver("player_uuid", Tag.selfClosingInserting(Component.text(player.getUniqueId().toString()))),
      TagResolver.resolver("player_displayname", Tag.selfClosingInserting(Component.text(player.getDisplayName()))),
      TagResolver.resolver("player_server_name", Tag.selfClosingInserting(Component.text(player.getServer().getInfo().getName()))),
      TagResolver.resolver("player_server_ip", Tag.selfClosingInserting(Component.text(player.getServer().getInfo().getAddress().getHostString()))),
      TagResolver.resolver("player_server_port", Tag.selfClosingInserting(Component.text(player.getServer().getInfo().getAddress().getPort()))),
      TagResolver.resolver("player_locale", Tag.selfClosingInserting(Component.text(player.getLocale().getDisplayLanguage()))),
      TagResolver.resolver("player_ping", Tag.selfClosingInserting(Component.text(player.getPing()))),
      TagResolver.resolver("player_forge_user", Tag.selfClosingInserting(Component.text(player.isForgeUser()))),
      TagResolver.resolver("player_view_distance", Tag.selfClosingInserting(Component.text(player.getViewDistance())))
    );
  }

  @SuppressWarnings("deprecation")
  @NotNull TagResolver serverTags() {
    return TagResolver.resolver(
      TagResolver.resolver("server_name", Tag.selfClosingInserting(Component.text(CommandBlockerBungee.get().getProxy().getName()))),
      TagResolver.resolver("server_max_players", Tag.selfClosingInserting(Component.text(CommandBlockerBungee.get().getProxy().getConfig().getPlayerLimit()))),
      TagResolver.resolver("server_online_players", Tag.selfClosingInserting(Component.text(CommandBlockerBungee.get().getProxy().getPlayers().size()))),
      TagResolver.resolver("server_version", Tag.selfClosingInserting(Component.text(CommandBlockerBungee.get().getProxy().getVersion()))),
      TagResolver.resolver("server_minecraft_version", Tag.selfClosingInserting(Component.text(CommandBlockerBungee.get().getProxy().getGameVersion()))),
      TagResolver.resolver("server_list_size", Tag.selfClosingInserting(Component.text(CommandBlockerBungee.get().getProxy().getServers().size())))
    );
  }

  @NotNull TagResolver additionalPlaceholderTags(final @NotNull HashMap<String, Component> additionalComponentVariables) {
    final TagResolver.Builder builder = TagResolver.builder();
    for (final Map.Entry<String, Component> entry : additionalComponentVariables.entrySet()) {
      builder.resolver(TagResolver.resolver(entry.getKey(), Tag.selfClosingInserting(entry.getValue())));
    }
    return builder.build();
  }
}
