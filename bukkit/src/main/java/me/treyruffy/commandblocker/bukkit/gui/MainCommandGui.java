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
package me.treyruffy.commandblocker.bukkit.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import me.treyruffy.commandblocker.api.Command;
import me.treyruffy.commandblocker.api.CommandBlocker;
import me.treyruffy.commandblocker.api.CommandBlockerTypes;
import me.treyruffy.commandblocker.api.config.Configuration;
import me.treyruffy.commandblocker.api.config.ConfigurationFiles;
import me.treyruffy.commandblocker.common.Universal;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.ConfigurationSection;

/**
 * The class to create in-game GUIs.
 */
public class MainCommandGui {

    /**
     * The main page to the edit command gui.
     *
     * @param command the command to edit
     * @param op {@code true} if the command is on the OP list
     * @param player the player who can see the gui
     */
    public void firstPageGui(final Command command, final boolean op, final @NotNull Player player) {

        if (op ? !player.hasPermission("cb.editop") : !player.hasPermission("cb.edit")) {
            return;
        }

        final String configurationSectionName = op ? "EditOpGui" : "EditGui";
        final ConfigurationSection config =
            Configuration.getConfiguration(ConfigurationFiles.MESSAGES).getConfigurationSection(configurationSectionName);

        final String permissionItemTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTY3MTY1MjI4ZWNlN2FhYzg3NmExMTA4ZjczYzJiNzYwNDQ0MGJlMWRmZjQzZWJkMmZhODQxOTU0ODkxZDhjIn19fQ==";
        final String messageItemTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjAyYWYzY2EyZDVhMTYwY2ExMTE0MDQ4Yjc5NDc1OTQyNjlhZmUyYjFiNWVjMjU1ZWU3MmI2ODNiNjBiOTliOSJ9fX0=";
        final String worldItemTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDJmMDMyMTk5NjRlOTY2N2ZhMWY0ZDdhNWRlYTA1ODQwZTg5YTRkODgyNDAzNjQxYTMwMTE2MjNkOTUifX19";
        final String consoleCommandItemTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdkOTc3ZTI4ODlhMWY1MGJiYjIzZDQyMjI3NWFiOWRhYTNjMTUwYzg5ODMxZTc5ODRiNDliNDA5ZGIwNjcifX19";
        final String titleItemTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAxYWZlOTczYzU0ODJmZGM3MWU2YWExMDY5ODgzM2M3OWM0MzdmMjEzMDhlYTlhMWEwOTU3NDZlYzI3NGEwZiJ9fX0=";
        final String whitelistItemTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU1NzY5NTM1YjIyMzgxNDExY2VjNDA4ZDBiY2I4NTQyMmMzMTQ4Zjk5MDI1ZjZhMWIyZjJhYzYwMmUzOGVmNiJ9fX0=";

        final Gui gui = Gui.gui()
            .title(this.translateCommandVariable(config.getString("EditCommandInventoryName"), command))
            .type(GuiType.CHEST)
            .rows(4)
            .create();

        final GuiItem pinkOutline =
            ItemBuilder.from(Material.PINK_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem();
        final GuiItem purpleOutline =
            ItemBuilder.from(Material.PURPLE_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem();

        final GuiItem permissionGuiItem = ItemBuilder
            .skull()
            .texture(permissionItemTexture)
            .name(this.translateCommandVariable(config.getString("PermissionItemName"), command))
            .lore(this.translateCommandVariable(config.getStringList("PermissionItemLore"), command))
            .asGuiItem();
        final GuiItem messageGuiItem = ItemBuilder
            .skull()
            .texture(messageItemTexture)
            .name(this.translateCommandVariable(config.getString("MessageItemName"), command))
            .lore(this.translateCommandVariable(config.getStringList("MessageItemLore"), command))
            .asGuiItem(event -> this.messageGui(command, op, player));
        final GuiItem worldsGuiItem = ItemBuilder
            .skull()
            .texture(worldItemTexture)
            .name(this.translateCommandVariable(config.getString("WorldsItemName"), command))
            .asGuiItem(event -> this.worldGui(command, op, player));
        final GuiItem playerCommandsGuiItem = ItemBuilder
            .skull()
            .name(this.translateCommandVariable(config.getString("PlayerCommandsItemName"), command))
            .asGuiItem();
        final GuiItem consoleCommandsGuiItem = ItemBuilder
            .skull()
            .texture(consoleCommandItemTexture)
            .name(this.translateCommandVariable(config.getString("ConsoleCommandsItemName"), command))
            .asGuiItem();
        final GuiItem tabCompletionGuiItem = ItemBuilder
            .from(command.disableTabComplete() ? Material.GREEN_WOOL : Material.RED_WOOL)
            .name(this.translateCommandVariable(config.getString("NoTabCompletion"), command))
            .asGuiItem(event -> {
                command.disableTabComplete(!command.disableTabComplete());
                CommandBlocker.addBlockedCommandToConfig(op ? CommandBlockerTypes.OPERATORS :
                    CommandBlockerTypes.REGULAR_PLAYERS, command, false);
                this.firstPageGui(command, op, player);
            });
        final GuiItem titleGuiItem = ItemBuilder
            .skull()
            .texture(titleItemTexture)
            .name(this.translateCommandVariable(config.getString("TitleItemName"), command))
            .asGuiItem();
        final GuiItem whitelistGuiItem = ItemBuilder
            .skull()
            .texture(whitelistItemTexture)
            .name(this.translateCommandVariable(config.getString("WhitelistItemName"), command))
            .asGuiItem();

        final GuiItem commandGuiItem = ItemBuilder
            .from(Material.BOOK)
            .name(this.translateCommandVariable(config.getString("InventoryCommandName"), command))
            .asGuiItem();

        int offset = 0;
        if (!op) {
            offset = 1;
            gui.setItem(2, 2, permissionGuiItem);
        }

        gui.setItem(2, 3, messageGuiItem);
        gui.setItem(2, 4, worldsGuiItem);
        gui.setItem(2, 5 + offset, playerCommandsGuiItem);
        gui.setItem(2, 6 + offset, consoleCommandsGuiItem);
        gui.setItem(2, 7 + offset, tabCompletionGuiItem);

        gui.setItem(3, 4, titleGuiItem);
        gui.setItem(3, 6, whitelistGuiItem);

        gui.setItem(4, 5, commandGuiItem);

        gui.getFiller().fill(Arrays.asList(pinkOutline, purpleOutline));
        gui.setDefaultClickAction(event -> event.setCancelled(true));

        gui.open(player);
    }

    /**
     * The message page to the edit command gui.
     *
     * @param command the command to edit
     * @param op {@code true} if the command is on the OP list
     * @param player the player who can see the gui
     */
    public void messageGui(final Command command, final boolean op, final @NotNull Player player) {
        if (op ? !player.hasPermission("cb.editop") : !player.hasPermission("cb.edit")) {
            return;
        }
        final PaginatedGui gui = this.createPaginatedGui(command, op, player);

        final String addMessageTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzIzMzJiNzcwYTQ4NzQ2OTg4NjI4NTVkYTViM2ZlNDdmMTlhYjI5MWRmNzY2YjYwODNiNWY5YTBjM2M2ODQ3ZSJ9fX0=";

        final String configurationSectionName = op ? "EditOpGui" : "EditGui";
        final ConfigurationSection config =
            Configuration.getConfiguration(ConfigurationFiles.MESSAGES).getConfigurationSection(configurationSectionName);

        final GuiItem addMessageGuiItem = ItemBuilder
            .skull()
            .texture(addMessageTexture)
            .name(this.translateCommandVariable(config.getString("AddMessageItem"), command))
            .asGuiItem();

        gui.setItem(5, 8, addMessageGuiItem);

        final String messageTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjAyYWYzY2EyZDVhMTYwY2ExMTE0MDQ4Yjc5NDc1OTQyNjlhZmUyYjFiNWVjMjU1ZWU3MmI2ODNiNjBiOTliOSJ9fX0=";

        command.messages().iterator().forEachRemaining(s -> {
            final GuiItem messageGuiItem = ItemBuilder
                .skull()
                .texture(messageTexture)
                .name(this.translateCommandVariable(s, command).color(TextColor.color(255, 255, 255)))
                .asGuiItem(event -> {
                    final List<String> messageList = command.messages();
                    if (!messageList.equals(CommandBlocker.defaultMessage())) {
                        messageList.remove(s);
                        if (messageList.isEmpty()) {
                            messageList.addAll(CommandBlocker.defaultMessage());
                        }
                        command.messages(messageList);
                        CommandBlocker.addBlockedCommandToConfig(op ? CommandBlockerTypes.OPERATORS :
                            CommandBlockerTypes.REGULAR_PLAYERS, command, false);
                        this.messageGui(command, op, player);
                    }
                });
            gui.addItem(messageGuiItem);
        });

        gui.open(player);
    }

    /**
     * The world page to the edit command gui.
     *
     * @param command the command to edit
     * @param op {@code true} if the command is on the OP list
     * @param player the player who can see the gui
     */
    public void worldGui(final Command command, final boolean op, final @NotNull Player player) {
        if (op ? !player.hasPermission("cb.editop") : !player.hasPermission("cb.edit")) {
            return;
        }
        final PaginatedGui gui = this.createPaginatedGui(command, op, player);

        final String configurationSectionName = op ? "EditOpGui" : "EditGui";
        final ConfigurationSection config =
            Configuration.getConfiguration(ConfigurationFiles.MESSAGES).getConfigurationSection(configurationSectionName);
        final String addWorldTexture =
        "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzIzMzJiNzcwYTQ4NzQ2OTg4NjI4NTVkYTViM2ZlNDdmMTlhYjI5MWRmNzY2YjYwODNiNWY5YTBjM2M2ODQ3ZSJ9fX0=";

        final GuiItem addWorldGuiItem = ItemBuilder
            .skull()
            .texture(addWorldTexture)
            .name(this.translateCommandVariable(config.getString("AddWorldItem"), command))
            .asGuiItem();

        gui.setItem(5, 8, addWorldGuiItem);

        final List<String> worldList = command.worlds();
        final boolean allEnabled = worldList.contains("all");
        final List<String> enabledWorlds = new ArrayList<>();
        final List<String> disabledWorlds = new ArrayList<>();
        final List<String> bukkitWorldList = new ArrayList<>();

        for (final World bukkitWorld : Bukkit.getWorlds()) {
            bukkitWorldList.add(bukkitWorld.getName().toLowerCase());
        }

        final String enabledWorldTexture =
        "eyJ0aW1lc3RhbXAiOjE1MTQyMTY2ODIwMDAsInByb2ZpbGVJZCI6ImFkMWM2Yjk1YTA5ODRmNTE4MWJhOTgyMzY0OTllM2JkIiwicHJvZmlsZU5hbWUiOiJGdXJrYW5iejAwIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82Zjc0ZjU4ZjU0MTM0MjM5M2IzYjE2Nzg3ZGQwNTFkZmFjZWM4Y2I1Y2QzMjI5YzYxZTVmNzNkNjM5NDdhZCJ9fX0=";
        final String disabledWorldTexture =
        "ewogICJ0aW1lc3RhbXAiIDogMTYzODYwNzMwMzk2NywKICAicHJvZmlsZUlkIiA6ICI0ZjU2ZTg2ODk2OGU0ZWEwYmNjM2M2NzRlNzQ3ODdjOCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE1IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzkxZWRkYWE2Njk3OWQyZTJhMjYzZDVlNzliMDA4OTA1ZjFkMGY5YWFlMjY3ZGE0N2YxYTRiYTkzMzk2ZmUwYzYiCiAgICB9CiAgfQp9";

        command.worlds().iterator().forEachRemaining(s -> {
            if (!enabledWorlds.contains(s.toLowerCase()))
                enabledWorlds.add(s.toLowerCase());
        });
        if (allEnabled) {
            bukkitWorldList.iterator().forEachRemaining(world -> {
                if (!enabledWorlds.contains(world))
                    enabledWorlds.add(world);
            });
        } else {
            bukkitWorldList.iterator().forEachRemaining(world -> {
                if (!enabledWorlds.contains(world)) {
                    disabledWorlds.add(world);
                }
            });
        }

        enabledWorlds.iterator().forEachRemaining(s -> {
            final GuiItem enabledWorldGuiItem = ItemBuilder
                .skull()
                .texture(enabledWorldTexture)
                .name(Component.text(s).color(TextColor.color(50, 205, 50)))
                .asGuiItem(event -> {
                    if (allEnabled) {
                        enabledWorlds.remove("all");
                    }
                    enabledWorlds.remove(s);
                    command.worlds(enabledWorlds);
                    CommandBlocker.addBlockedCommandToConfig(op ? CommandBlockerTypes.OPERATORS :
                        CommandBlockerTypes.REGULAR_PLAYERS, command, false);
                    this.worldGui(command, op, player);
                });
            gui.addItem(enabledWorldGuiItem);
        });
        disabledWorlds.iterator().forEachRemaining(s -> {
            final GuiItem disabledWorldGuiItem = ItemBuilder
            .skull()
            .texture(disabledWorldTexture)
            .name(Component.text(s).color(TextColor.color(178, 34, 34)))
            .asGuiItem(event -> {
                enabledWorlds.add(s);
                if (enabledWorlds.containsAll(bukkitWorldList)) {
                    enabledWorlds.add("all");
                    enabledWorlds.removeAll(bukkitWorldList);
                }
                command.worlds(enabledWorlds);
                CommandBlocker.addBlockedCommandToConfig(op ? CommandBlockerTypes.OPERATORS :
                    CommandBlockerTypes.REGULAR_PLAYERS, command, false);
                this.worldGui(command, op, player);
            });
            gui.addItem(disabledWorldGuiItem);
        });

        gui.open(player);
    }

    private @NotNull PaginatedGui createPaginatedGui(final Command command, final boolean op, final Player player) {
        final String configurationSectionName = op ? "EditOpGui" : "EditGui";
        final ConfigurationSection config =
            Configuration.getConfiguration(ConfigurationFiles.MESSAGES).getConfigurationSection(configurationSectionName);

        final PaginatedGui gui = Gui.paginated()
            .title(this.translateCommandVariable(config.getString("EditWorldsInventoryName"), command))
            .rows(6)
            .create();

        final GuiItem pinkOutline =
            ItemBuilder.from(Material.PINK_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem();
        final GuiItem purpleOutline =
            ItemBuilder.from(Material.PURPLE_STAINED_GLASS_PANE).name(Component.text(" ")).asGuiItem();

        final GuiItem previousPageItem = ItemBuilder
            .from(Material.PAPER)
            .name(this.translateCommandVariable(config.getString("PreviousPageItemName"), command))
            .asGuiItem(event -> gui.previous());
        final GuiItem nextPageItem = ItemBuilder
            .from(Material.PAPER)
            .name(this.translateCommandVariable(config.getString("NextPageItemName"), command))
            .asGuiItem(event -> gui.next());

        final GuiItem commandGuiItem = ItemBuilder
            .from(Material.BOOK)
            .name(this.translateCommandVariable(config.getString("InventoryCommandName"), command))
            .asGuiItem();
        final GuiItem backGuiItem = ItemBuilder
            .from(Material.ARROW)
            .name(this.translateCommandVariable(config.getString("GoBackItemName"), command))
            .asGuiItem(event -> this.firstPageGui(command, op, player));

        gui.setItem(6, 1, backGuiItem);
        gui.setItem(6, 3, previousPageItem);
        gui.setItem(6, 5, commandGuiItem);
        gui.setItem(6, 7, nextPageItem);

        final List<Integer> pinkOutlineSlots = new ArrayList<>();
        Collections.addAll(pinkOutlineSlots, 0, 2, 4, 6, 8, 18, 26, 36, 44, 46, 48, 50, 52);

        final List<Integer> purpleOutlineSlots = new ArrayList<>();
        Collections.addAll(purpleOutlineSlots, 1, 3, 5, 7, 9, 17, 27, 35, 53);

        gui.setItem(pinkOutlineSlots, pinkOutline);
        gui.setItem(purpleOutlineSlots, purpleOutline);

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        return gui;
    }

    private Component translateCommandVariable(final String originalString, final @NotNull Command command,
                                               final int messageLine) {
        final HashMap<String, String> commandVariables = new HashMap<>();
        final HashMap<String, Component> componentCommandVariables = new HashMap<>();
        final StringBuilder commandString = new StringBuilder();
        for (final String commandPart : command.command()) {
            commandString.append(commandPart).append(" ");
        }
        commandVariables.put("%c", commandString.toString().trim());
        commandVariables.put("%p", command.permission());
        if (messageLine == -1) {
            final StringBuilder messageVariable = new StringBuilder();
            final Iterator<String> iterator = command.messages().iterator();
            iterator.forEachRemaining(s -> {
                messageVariable.append(s);
                if (iterator.hasNext()) {
                    messageVariable.append(", ");
                }
            });
            componentCommandVariables.put("%m",
                Universal.get().universalMethods().translateVariables().translateVariables(messageVariable.toString()));
        } else {
            componentCommandVariables.put("%m",
                Universal.get().universalMethods().translateVariables().translateVariables(command.messages().get(messageLine), command));
        }

        return Universal.get().universalMethods().translateVariables().translateVariables(originalString, null,
            commandVariables, componentCommandVariables);
    }

    private Component translateCommandVariable(final String originalString, final Command command) {
        return this.translateCommandVariable(originalString, command, -1);
    }

    @Contract(pure = true)
    private @NotNull List<Component> translateCommandVariable(final @NotNull List<String> originalString, final Command command) {
        final List<Component> componentList = new ArrayList<>();
        for (final String stringPart : originalString) {
            if (stringPart.contains("%m")) {
                for (int i = 0; i < command.messages().size(); i++) {
                    componentList.add(this.translateCommandVariable(stringPart, command, i));
                }
            } else {
                componentList.add(this.translateCommandVariable(stringPart, command));
            }
        }
        return componentList;
    }

}
