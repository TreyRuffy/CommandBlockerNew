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
package me.treyruffy.commandblocker.sponge;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import me.treyruffy.commandblocker.common.commands.CommandHandler;
import me.treyruffy.commandblocker.common.util.ExceptionHandler;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandCause;
import org.spongepowered.api.command.CommandCompletion;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.ArgumentReader;

/**
 * The Sponge command handler.
 */
public class SpongeCommandHandler extends CommandHandler<CommandCause> implements Command.Raw {

  private final CommandDispatcher<CommandCause> dispatcher = new CommandDispatcher<>();

  /**
   * The Sponge command handler.
   */
  public SpongeCommandHandler() {
    this.dispatcher.register(this.commandBlockerNode);
  }

  /**
   * Gets the {@link CommandDispatcher}.
   *
   * @return the {@link CommandDispatcher}
   */
  public CommandDispatcher<CommandCause> dispatcher() {
    return this.dispatcher;
  }

  @Override
  public CommandResult process(final CommandCause cause, final ArgumentReader.@NotNull Mutable arguments) {
    final String argsString = "commandblocker " + String.join(" ", arguments.input());

    try {
      this.dispatcher.execute(argsString, cause);
    } catch (final CommandSyntaxException e) {
      this.showHelp(cause);
    }
    return CommandResult.success();
  }

  @Override
  public List<CommandCompletion> complete(final CommandCause cause, final ArgumentReader.@NotNull Mutable arguments) {

    final String argsString = "commandblocker " + String.join(" ", arguments.input());

    final ParseResults<CommandCause> parseResults = this.dispatcher.parse(argsString, cause);
    final CompletableFuture<Suggestions> suggestionsFuture =
    this.dispatcher.getCompletionSuggestions(parseResults).toCompletableFuture();
    final List<CommandCompletion> suggestions = new ArrayList<>();
    try {
      for (final Suggestion suggestion : suggestionsFuture.get().getList()) {
        suggestions.add(CommandCompletion.of(suggestion.getText()));
      }
      return suggestions;
    } catch (InterruptedException | ExecutionException e) {
      ExceptionHandler.handleException(e);
    }
    return suggestions;
  }

  @Override
  public boolean canExecute(final CommandCause cause) {
    return true;
  }

  @Override
  public Optional<Component> shortDescription(final CommandCause cause) {
    return Optional.empty();
  }

  @Override
  public Optional<Component> extendedDescription(final CommandCause cause) {
    return Optional.empty();
  }

  @Override
  public Component usage(final CommandCause cause) {
    return Component.text("/commandblocker");
  }

  @Override
  public void sendMessage(final @NotNull CommandCause sender, final @NotNull Component component) {
    sender.audience().sendMessage(component);
  }

  @Override
  public boolean hasPermission(final @NotNull CommandCause sender, final @NotNull String permission) {
    return sender.subject().hasPermission(permission);
  }
}
