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
package me.treyruffy.commandblocker.common.blocking;

import java.util.HashMap;
import java.util.List;
import me.treyruffy.commandblocker.api.Command;
import me.treyruffy.commandblocker.common.CommandBlockerValues;
import me.treyruffy.commandblocker.common.Universal;
import me.treyruffy.commandblocker.common.players.CommandBlockerPlayers;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.Nullable;

/**
 * Methods to send out the outputs from the blocked command.
 */
public class BlockedCommandOutput {
    private final CommandBlockerPlayers player;
    private final Command command;
    private @Nullable final HashMap<String, String> placeholders;

    BlockedCommandOutput(final CommandBlockerPlayers player, final Command command,
                         @Nullable final HashMap<String, String> placeholders) {
        this.player = player;
        this.command = command;
        this.placeholders = placeholders;
    }

    /**
     * Gets the blocked command.
     *
     * @return the blocked command
     */
    public Command command() {
        return this.command;
    }

    /**
     * Sends the messages set for the blocked command.
     */
    public void sendMessages() {
        for (final String message : this.command.messages()) {
            this.player.sendMessage(Universal.get().universalMethods().translateVariables().translateVariables(message,
                this.player.player(), this.placeholders, null));
        }
    }

    /**
     * Sends the player commands set for the blocked command.
     */
    public void sendPlayerCommands() {
        final List<String> commands = this.command().playerCommands();
        if (commands != null) {
            for (final String command : commands) {
                this.player.performCommand(command);
            }
        }
    }

    /**
     * Sends the console commands set for the blocked command.
     */
    public void sendConsoleCommands() {
        final CommandBlockerValues methods = Universal.get().universalMethods();
        final List<String> commands = this.command().consoleCommands();
        if (commands != null) {
            for (final String command : commands) {
                methods.executeConsoleCommand(command);
            }
        }
    }

    /**
     * Sends a title to the player who executed the command.
     */
    public void sendTitle() {
        if (this.command().title() != null && this.command().subtitle() != null) {
            final Component title = Universal.get().universalMethods().translateVariables().translateVariables(this.command.title(),
                this.player.player(), this.placeholders, null);
            final Component subtitle =
                Universal.get().universalMethods().translateVariables().translateVariables(this.command.subtitle(),
                this.player.player(), this.placeholders, null);
            final Title.Times titleTime = this.command.titleTime();
            final Title fullTitle;
            if (titleTime == null || titleTime.fadeIn().getSeconds() == 0 && titleTime.stay().getSeconds() == 0
                && titleTime.fadeOut().getSeconds() == 0) {
                fullTitle = Title.title(title, subtitle);
            } else {
                fullTitle = Title.title(title, subtitle, titleTime);
            }
            this.player.sendTitle(fullTitle);
        }
    }

    /**
     * Sends an action bar to the player who executed the command.
     */
    public void sendActionBar() {
        final String actionBar = this.command.actionBar();
        if (actionBar != null) {
            this.player.sendActionBar(Universal.get().universalMethods().translateVariables().translateVariables(actionBar,
                this.player.player(), this.placeholders, null));
        }
    }

    /**
     * Sends all the different outputs.
     */
    public void sendAll() {
        this.sendMessages();
        this.sendPlayerCommands();
        this.sendConsoleCommands();
        this.sendTitle();
        this.sendActionBar();
    }

}
