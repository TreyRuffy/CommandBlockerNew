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

import java.util.List;
import me.treyruffy.commandblocker.api.Command;
import me.treyruffy.commandblocker.api.CommandBlocker;
import me.treyruffy.commandblocker.api.CommandBlockerTypes;
import me.treyruffy.commandblocker.bukkit.gui.MainCommandGui;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.CommandBlockerValues;
import me.treyruffy.commandblocker.common.Universal;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The main CommandBlocker Bukkit Plugin class.
 */
public class CommandBlockerBukkit extends JavaPlugin {

    private final boolean debug = new CommandBlockerCommon().debug();
    private static CommandBlockerBukkit instance;
    private static CommandBlockerValues commandBlockerValues;
    private static BukkitAudiences adventure;
    private boolean placeholderApiEnabled = false;

    /**
     * Gets the Bukkit plugin class.
     *
     * @return the CommandBlocker Bukkit plugin class
     */
    public static CommandBlockerBukkit get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        adventure = BukkitAudiences.create(this);
        Universal.get().setup(new SetupValues());
        commandBlockerValues = Universal.get().universalMethods();
        commandBlockerValues.log().info("Loaded correctly");
        new CommandBlockerCommon().test();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.placeholderApiEnabled = true;
        }

        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);

        Bukkit.getOnlinePlayers().iterator().forEachRemaining(player -> {
            final List<Command> commands = CommandBlocker.commandsBlocked(CommandBlockerTypes.REGULAR_PLAYERS);
            if (commands != null) {
                new MainCommandGui().firstPageGui(commands.get(0), false, player);
            }
        });
    }

    /**
     * Forcefully refreshes the universal command blocker methods.
     */
    public static void refreshCommandBlockerValues() {
        Universal.get(true).setup(new SetupValues()).universalMethods();
    }

    /**
     * Gets the Bukkit Adventure API.
     *
     * @return the Bukkit Adventure API
     */
    public static @NotNull BukkitAudiences adventure() {
        if (adventure == null)
            throw new IllegalStateException("Tried to access Adventure while the plugin was disabled.");
        return adventure;
    }

    /**
     * Checks if PlaceholderAPI is enabled.
     *
     * @return true if PlaceholderAPI is enabled
     */
    public boolean placeholderApiEnabled() {
        return this.placeholderApiEnabled;
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        commandBlockerValues.log().info("Unloaded correctly");
    }

}
