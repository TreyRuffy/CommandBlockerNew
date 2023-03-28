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
package me.treyruffy.commandblocker.common.commands.subcommands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.treyruffy.commandblocker.api.CommandBlockerAPI;
import me.treyruffy.commandblocker.api.utils.ModifySets;
import me.treyruffy.commandblocker.common.commands.CBCommand;
import me.treyruffy.commandblocker.common.commands.CommandHandler;
import me.treyruffy.commandblocker.common.config.Configuration;
import me.treyruffy.commandblocker.common.config.PopulateBlockedCommands;
import me.treyruffy.commandblocker.common.util.ExceptionHandler;
import me.treyruffy.commandblocker.common.util.Sets;
import me.treyruffy.commandblocker.common.util.Universal;
import org.spongepowered.configurate.serialize.SerializationException;

/**
 * The reload command — '/commandblocker reload'.
 *
 * @param <T> the sender type
 */
public final class ReloadCommand<T> extends CBCommand<T> {

  /**
   * Sets up a new command blocker command.
   *
   * @param commandHandler the {@link CommandHandler}
   */
  public ReloadCommand(final CommandHandler<T> commandHandler) {
    super(commandHandler);
  }

  /**
   * Registers the command.
   *
   * @return the Command Node
   */
  public LiteralArgumentBuilder<T> register() {
    return LiteralArgumentBuilder
      .<T>literal("reload")
      .requires(source -> this.commandHandler.hasPermission(source, "commandblocker.reload"))
      .executes(context -> {
        this.commandHandler.sendMessage(
          context.getSource(),
          Universal.get().universalMethods().i18n("commands.reload.start", context.getSource())
        );
        final ModifySets oldSet = CommandBlockerAPI.get().modifySets();
        Configuration.reload();
        CommandBlockerAPI.get().modifySets(new Sets());
        try {
          PopulateBlockedCommands.populateSet();
          this.commandHandler.sendMessage(
            context.getSource(),
            Universal.get().universalMethods().i18n("commands.reload.success", context.getSource())
          );
        } catch (final SerializationException e) {
          ExceptionHandler.handleException(e);
          CommandBlockerAPI.get().modifySets(oldSet);
          this.commandHandler.sendMessage(
            context.getSource(),
            Universal.get().universalMethods().i18n("commands.reload.failed", context.getSource())
          );
        }
        return Command.SINGLE_SUCCESS;
      });
  }
}
