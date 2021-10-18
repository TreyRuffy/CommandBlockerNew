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
package me.treyruffy.commandblocker.bungee;

import java.util.logging.Level;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.Universal;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The main CommandBlocker Bungee Plugin class.
 */
public class CommandBlockerBungee extends Plugin {

    private final boolean debug = new CommandBlockerCommon().debug();
    private static CommandBlockerBungee instance;
    private static BungeeAudiences adventure;

    /**
     * Gets the Bungee plugin class.
     *
     * @return the CommandBlocker Bungee plugin class
     */
    public static CommandBlockerBungee get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        adventure = BungeeAudiences.create(this);
        Universal.get().setup(new SetupValues());
        Universal.get().universalMethods().log().info("Loaded correctly");
        new CommandBlockerCommon().test();
    }

    /**
     * Gets the Bungee Adventure API.
     *
     * @return the Bungee Adventure API
     */
    public static @NotNull BungeeAudiences adventure() {
        if (adventure == null)
            throw new IllegalStateException("Tried to access Adventure while the plugin was disabled.");
        return adventure;
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Unloaded correctly");
    }

}
