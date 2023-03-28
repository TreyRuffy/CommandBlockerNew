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
package me.treyruffy.commandblocker.spongelegacy;

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
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * The Sponge Legacy command handler.
 */
public class SpongeLegacyCommandHandler extends CommandHandler<CommandSource> implements CommandCallable {

  private final CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();

  /**
   * The Sponge Legacy command handler.
   */
  public SpongeLegacyCommandHandler() {
    this.dispatcher.register(this.commandBlockerNode);
  }

  /**
   * Gets the {@link CommandDispatcher}.
   *
   * @return the {@link CommandDispatcher}
   */
  public CommandDispatcher<CommandSource> dispatcher() {
    return this.dispatcher;
  }

  @Override
  public @NotNull CommandResult process(final @NotNull CommandSource source, final @NotNull String arguments) {
    final String argsString = "commandblocker " + String.join(" ", arguments);

    try {
      this.dispatcher.execute(argsString, source);
    } catch (final CommandSyntaxException e) {
      this.showHelp(source);
    }
    return CommandResult.success();
  }

  @Override
  public @NotNull List<String> getSuggestions(final @NotNull CommandSource source, final @NotNull String arguments,
                                              final @Nullable Location<World> targetPosition) {
    final String argsString = "commandblocker " + String.join(" ", arguments);

    final ParseResults<CommandSource> parseResults = this.dispatcher.parse(argsString, source);
    final CompletableFuture<Suggestions> suggestionsFuture =
      this.dispatcher.getCompletionSuggestions(parseResults).toCompletableFuture();
    final List<String> suggestions = new ArrayList<>();
    try {
      for (final Suggestion suggestion : suggestionsFuture.get().getList()) {
        suggestions.add(suggestion.getText());
      }
      return suggestions;
    } catch (InterruptedException | ExecutionException e) {
      ExceptionHandler.handleException(e);
    }
    return suggestions;
  }

  @Override
  public boolean testPermission(final @NotNull CommandSource source) {
    return true;
  }

  @Override
  public @NotNull Optional<Text> getShortDescription(final @NotNull CommandSource source) {
    return Optional.empty();
  }

  @Override
  public @NotNull Optional<Text> getHelp(final @NotNull CommandSource source) {
    return Optional.empty();
  }

  @Override
  public @NotNull Text getUsage(final @NotNull CommandSource source) {
    return Text.of("/commandblocker");
  }

  @Override
  public void sendMessage(final @NotNull CommandSource sender, final @NotNull Component component) {
    CommandBlockerSpongeLegacy.adventure().receiver(sender).sendMessage(component);
  }

  @Override
  public boolean hasPermission(final @NotNull CommandSource sender, final @NotNull String permission) {
    return sender.hasPermission(permission);
  }
}
