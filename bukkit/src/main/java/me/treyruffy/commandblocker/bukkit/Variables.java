/*
 * Copyright (C) 2015-2021 TreyRuffy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package me.treyruffy.commandblocker.bukkit;

import java.util.HashMap;
import java.util.Objects;
import me.clip.placeholderapi.PlaceholderAPI;
import me.treyruffy.commandblocker.common.TranslateVariables;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The Bukkit variable translator.
 */
public class Variables implements TranslateVariables {

    @Override
    public Component translateVariables(final String message) {
        return this.translateVariables(message, null);
    }

    @Override
    public Component translateVariables(final String message, final Object player) {
        return this.translateVariables(message, player, null, null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public Component translateVariables(@NotNull final String message, final Object player,
                                        @Nullable final HashMap<String, String> additionalVariables,
                                        @Nullable final HashMap<String, Component> additionalComponentVariables) {
        final Component messageComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        String messageToString = MiniMessage.miniMessage().serialize(messageComponent);

        if (player instanceof OfflinePlayer) {
            final OfflinePlayer offlinePlayer = (OfflinePlayer) player;

            messageToString = messageToString.replace("%player%", Objects.requireNonNull(offlinePlayer.getName()))
                    .replace("%uuid%", offlinePlayer.getUniqueId().toString())
                    .replace("%isop%", String.valueOf(offlinePlayer.isOp()));
            if (player instanceof Player) {
                final Player onlinePlayer = (Player) player;

                messageToString = messageToString.replace("%username%", onlinePlayer.getDisplayName())
                        .replace("%displayname%", onlinePlayer.getDisplayName())
                        .replace("%server_name%", onlinePlayer.getServer().getName())
                        .replace("%server_ip%", onlinePlayer.getServer().getIp())
                        .replace("%server_port%", String.valueOf(onlinePlayer.getServer().getPort()))
                        .replace("%world%", onlinePlayer.getWorld().getName());
            }
            if (CommandBlockerBukkit.get().placeholderApiEnabled()) {
                messageToString = PlaceholderAPI.setPlaceholders(offlinePlayer, messageToString);
            }

        }

        messageToString = messageToString.replace("%server_name%", CommandBlockerBukkit.get().getServer().getName())
                .replace("%server_ip%", CommandBlockerBukkit.get().getServer().getIp())
                .replace("%server_port%", String.valueOf(CommandBlockerBukkit.get().getServer().getPort()));

        if (additionalVariables != null) {
            for (final String placeholder : additionalVariables.keySet()) {
                messageToString = messageToString.replace(placeholder, additionalVariables.get(placeholder));
            }
        }

        if (additionalComponentVariables != null) {
            for (final String placeholder : additionalComponentVariables.keySet()) {
                messageToString = messageToString.replace(placeholder,
                MiniMessage.miniMessage().serialize(additionalComponentVariables.get(placeholder)) + "<reset>");
            }
        }

        return MiniMessage.miniMessage().deserialize(messageToString);
    }
}
