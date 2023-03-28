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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.treyruffy.commandblocker.api.players.CommandBlockerPlayers;
import me.treyruffy.commandblocker.api.utils.ComponentTranslator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.text.serializer.spongeapi.SpongeComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

/**
 * The Sponge variable translator.
 */
public class Variables implements ComponentTranslator {

  @Override
  public @NotNull Component stringToComponent(final @NotNull String message) {
    return this.stringToComponent(message, null);
  }

  @Override
  public @NotNull Component stringToComponent(final @NotNull String message, final CommandBlockerPlayers<?> player) {
    return this.stringToComponent(message, player, null);
  }

  @Override
  public @NotNull Component stringToComponent(final @NotNull String message, final CommandBlockerPlayers<?> player,
                                              final @Nullable HashMap<String, Component> additionalComponentVariables) {
    final List<TagResolver> tagResolvers = new ArrayList<>();
    tagResolvers.add(TagResolver.standard());

    if (player != null && player.player() instanceof Player) {
      tagResolvers.add(this.playerTags((Player) player.player()));

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
    return this.stringToComponent(PlainTextComponentSerializer.plainText().serialize(component), player, additionalComponentVariables);
  }

  @NotNull TagResolver playerTags(final @NotNull Player player) {
    return TagResolver.resolver(
      TagResolver.resolver("player_name", Tag.selfClosingInserting(Component.text(player.getName()))),
      TagResolver.resolver("player_uuid", Tag.selfClosingInserting(Component.text(player.getUniqueId().toString()))),
      TagResolver.resolver("player_displayname", Tag.selfClosingInserting(
        SpongeComponentSerializer.get().deserialize(player.getDisplayNameData().displayName().get()))
      ),
      TagResolver.resolver("player_x", Tag.selfClosingInserting(Component.text(player.getLocation().getBlockX()))),
      TagResolver.resolver("player_y", Tag.selfClosingInserting(Component.text(player.getLocation().getBlockY()))),
      TagResolver.resolver("player_z", Tag.selfClosingInserting(Component.text(player.getLocation().getBlockZ()))),
      TagResolver.resolver("player_gamemode", Tag.selfClosingInserting(
        Component.text(player.gameMode().get().getTranslation().get()))
      ),
      TagResolver.resolver("player_health", Tag.selfClosingInserting(Component.text(player.health().get()))),
      TagResolver.resolver("player_food", Tag.selfClosingInserting(Component.text(player.foodLevel().get()))),
      TagResolver.resolver("player_locale", Tag.selfClosingInserting(Component.text(player.getLocale().getLanguage())))
    );
  }

  @NotNull TagResolver serverTags() {
    final TagResolver.Builder builder = TagResolver.builder();
    final Server server = Sponge.getServer();
    if (server.getBoundAddress().isPresent()) {
      builder.resolver(TagResolver.resolver("server_ip", Tag.selfClosingInserting(Component.text(server.getBoundAddress().get().getAddress().getHostAddress()))));
      builder.resolver(TagResolver.resolver("server_port", Tag.selfClosingInserting(Component.text(server.getBoundAddress().get().getPort()))));
    }
    builder.resolvers(
      TagResolver.resolver("server_motd", Tag.selfClosingInserting(SpongeComponentSerializer.get().deserialize(server.getMotd()))),
      TagResolver.resolver("server_max_players", Tag.selfClosingInserting(Component.text(server.getMaxPlayers()))),
      TagResolver.resolver("server_online_players", Tag.selfClosingInserting(Component.text(server.getOnlinePlayers().size()))),
      TagResolver.resolver("server_sponge_version",
      Tag.selfClosingInserting(Component.text(Sponge.getPlatform().getContainer(Platform.Component.IMPLEMENTATION).getVersion().orElse("Unknown")))),
      TagResolver.resolver("server_tps", Tag.selfClosingInserting(Component.text(server.getTicksPerSecond())))
    );
    return TagResolver.resolver();
  }

  @NotNull TagResolver additionalPlaceholderTags(final @NotNull HashMap<String, Component> additionalComponentVariables) {
    final TagResolver.Builder builder = TagResolver.builder();
    for (final Map.Entry<String, Component> entry : additionalComponentVariables.entrySet()) {
      builder.resolver(TagResolver.resolver(entry.getKey(), Tag.selfClosingInserting(entry.getValue())));
    }
    return TagResolver.resolver();
  }
}
