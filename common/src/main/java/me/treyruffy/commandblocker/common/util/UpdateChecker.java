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
package me.treyruffy.commandblocker.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import me.treyruffy.commandblocker.common.config.Configuration;
import me.treyruffy.commandblocker.common.config.ConfigurationFiles;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurationNode;

/**
 * The update checker.
 *
 * @since 3.0.0
 */
public final class UpdateChecker {

  private UpdateChecker() {
    // Do nothing
  }

  private static final String USER_AGENT = "CommandBlocker";
  private static final String REQUEST_URL = "https://api.github.com/repos/TreyRuffy/CommandBlocker/releases";
  @Nullable
  private static LatestUpdate latestUpdate;

  /**
   * Sends the messages to console about an update.
   *
   * @since 3.0.0
   */
  public static void consoleMessage() {
    if (!updateCheck() || latestUpdate == null) return;
    Universal.get().universalMethods().log().info(String.format("Update available: %s\nChangelog: %s",
      latestUpdate.updateVersion.version, latestUpdate.updateChangelog));
  }

  /**
   * Checks for a new program update.
   *
   * @return {@code true} if there is a new program update
   * @since 3.0.0
   */
  public static boolean updateCheck() {
    final Configuration configuration = new Configuration(ConfigurationFiles.CONFIGURATION);
    final ConfigurationNode configRoot = configuration.rootNode();
    if (!configRoot.node("updates", "check").getBoolean(true)) {
      return false;
    }

    // Only check once an hour
    if (latestUpdate == null || latestUpdate.lastUpdateTime - 3600000 < new Date().getTime()) {
      latestVersion();
    }

    final Version currentVersion = new Version(Universal.get().universalMethods().commandBlockerVersion());

    return currentVersion.compareTo(latestUpdate.updateVersion) < 0;
  }

  /**
   * Gets the newest program update.
   *
   * @since 3.0.0
   */
  private static void latestVersion() {
    try {
      final URL url = new URL(REQUEST_URL);
      final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.addRequestProperty("User-Agent", USER_AGENT);

      final InputStream inputStream = connection.getInputStream();
      final InputStreamReader reader = new InputStreamReader(inputStream);

      final JsonElement element = new JsonParser().parse(reader);
      reader.close();

      if (!element.isJsonArray()) {
        return;
      }

      for (final JsonElement jsonElement : element.getAsJsonArray()) {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final JsonElement preRelease = jsonObject.get("prerelease");
        final JsonElement draft = jsonObject.get("draft");

        // Only show full releases
        if (!(preRelease instanceof JsonNull) && !preRelease.getAsBoolean() && !(draft instanceof JsonNull) && !draft.getAsBoolean()) {
          final String updateVersion = jsonObject.get("tag_name").getAsString();
          final String updateChangelog = jsonObject.get("html_url").getAsString();
          latestUpdate = new LatestUpdate(updateVersion, updateChangelog, new Date().getTime());
          return;
        }
      }

    } catch (final IOException exception) {
      Universal.get().universalMethods().log().warn("Failed to check for an update.", exception);
    }
  }

  /**
   * Represents the latest update to the program.
   *
   * @since 3.0.0
   */
  protected static class LatestUpdate {
    private final Version updateVersion;
    private final String updateChangelog;
    private final long lastUpdateTime;

    /**
     * Initializes the latest update to the program.
     *
     * @param updateVersion the latest update version
     * @param updateChangelog the latest update changelog URL
     * @param lastUpdateTime the time the last update was checked at
     * @since 3.0.0
     */
    public LatestUpdate(final String updateVersion, final String updateChangelog, final long lastUpdateTime) {
      this.updateVersion = new Version(updateVersion);
      this.updateChangelog = updateChangelog;
      this.lastUpdateTime = lastUpdateTime;
    }
  }

  protected static class Version implements Comparable<Version> {

    private final String version;

    protected Version(final @NotNull String version) {
      this.version = version;
    }

    /**
     * Gets the version.
     *
     * @return the version
     * @since 3.0.0
     */
    public final String version() {
      return this.version;
    }

    @Override
    public int compareTo(final @NotNull UpdateChecker.Version other) {
      final String[] thisParts = this.version.split("\\.");
      final String[] otherParts = other.version.split("\\.");

      for (int i = 0; i < 3; i++) {
        final int thisPart = Integer.parseInt(thisParts[i]);
        final int otherPart = Integer.parseInt(otherParts[i]);
        if (thisPart < otherPart) {
          return -1;
        } else if (thisPart > otherPart) {
          return 1;
        }
      }
      return 0;
    }
  }
}

