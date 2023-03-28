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
package me.treyruffy.commandblocker.bukkit;

import java.util.Objects;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import me.treyruffy.commandblocker.api.event.CommandBlockerEvent;
import me.treyruffy.commandblocker.bukkit.listeners.CommandListener;
import me.treyruffy.commandblocker.bukkit.listeners.CommandSendListener;
import me.treyruffy.commandblocker.bukkit.listeners.TabCompleteListener;
import me.treyruffy.commandblocker.bukkit.util.BukkitEventManager;
import me.treyruffy.commandblocker.bukkit.util.SetupValues;
import me.treyruffy.commandblocker.common.CommandBlockerCommon;
import me.treyruffy.commandblocker.common.util.CommandBlockerValues;
import me.treyruffy.commandblocker.common.util.Universal;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The main CommandBlocker Bukkit Plugin class.
 */
public class CommandBlockerBukkit extends JavaPlugin {

  private static CommandBlockerBukkit instance;
  private static CommandBlockerValues<?> commandBlockerValues;
  private static BukkitAudiences adventure;
  private boolean placeholderApiEnabled = false;
  //private ConfigurationNode languageRoot;

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
    //this.language("en_us");
    Universal.get().setup(new SetupValues());
    commandBlockerValues = Universal.get().universalMethods();

    final PluginCommand command = getCommand("commandblocker");
    Objects.requireNonNull(command).setExecutor(new BukkitCommandHandler());

    if (CommodoreProvider.isSupported()) {
      final Commodore commodore = CommodoreProvider.getCommodore(this);
      commodore.register(command, new BukkitCommandHandler().commandBlockerNode());
    }
    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
      this.placeholderApiEnabled = true;
    }

    Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
    Bukkit.getPluginManager().registerEvents(new CommandSendListener(), this);
    Bukkit.getPluginManager().registerEvents(new TabCompleteListener(), this);
    Universal.get().eventBus().subscribe(CommandBlockerEvent.class, new BukkitEventManager<>());
    commandBlockerValues.log().info("Loaded correctly");
    new CommandBlockerCommon().test();
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

  //  /**
  //   * Sets the configuration language.
  //   * Example: "en_us"
  //   *
  //   * @param lang the new language code
  //   */
  //  public void language(final @NotNull String lang) {
  //    if (!this.getDataFolder().exists()) {
  //      if (!this.getDataFolder().mkdirs()) {
  //        ExceptionHandler.handleException(new IOException("Error creating configuration directory!"));
  //      }
  //    }
  //
  //    final File langFile = new File(this.getDataFolder() + File.separator + lang.toLowerCase() + ".json");
  //    if (!langFile.exists()) {
  //      try {
  //        Files.copy(Objects.requireNonNull(Configuration.class.getResourceAsStream("assets/commandblocker/lang" + lang.toLowerCase() + ".json")),
  //        Paths.get(this.getDataFolder() + File.separator + lang.toLowerCase() + ".json"));
  //      } catch (final IOException e) {
  //        ExceptionHandler.handleException(new IOException("Error creating language file!"));
  //      }
  //    }
  //
  //    final GsonConfigurationLoader languageLoader = GsonConfigurationLoader.builder().file(langFile).build();
  //    try {
  //      this.languageRoot = languageLoader.load();
  //    } catch (final ConfigurateException e) {
  //      ExceptionHandler.handleException(e);
  //    }
  //  }
  //
  //  /**
  //   * Gets the language configuration root.
  //   *
  //   * @return the language configuration
  //   */
  //  public @NotNull ConfigurationNode language() {
  //    return this.languageRoot;
  //  }
  //
  @Override
  public void onDisable() {
    if (adventure != null) {
      adventure.close();
      adventure = null;
    }
    commandBlockerValues.log().info("Unloaded correctly");
  }
}
