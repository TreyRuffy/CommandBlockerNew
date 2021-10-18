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
package me.treyruffy.commandblocker.common.config;

import java.io.File;
import java.io.IOException;
import me.treyruffy.commandblocker.common.Universal;
import org.apache.commons.io.FileUtils;

/**
 * Class to set defaults in the configuration files after an update.
 */
public class UpdateConfigurations {

    String version;
    int intVersion;

    /**
     * Updates all configuration files.
     */
    public void updateAll() {
        final Configuration.ConfigurationOptions config =
                Universal.get().configuration().getConfiguration(ConfigurationFiles.CONFIGURATION);
        this.version = Universal.get().universalMethods().getCommandBlockerVersion();
        if (config.getString("Version") == null) {
            config.set("Version", this.version, "The version this " +
                    "configuration file is set to.\n* PLEASE DO NOT CHANGE THIS VALUE. *");
            config.save();
            return;
        }
        if (config.getString("Version").equals(this.version)) {
            return;
        }
        this.intVersion = Integer.parseInt(this.version.replace(".", "").replace("-SNAPSHOT", ""));
        this.updateConfig();
        this.updateBlocked();
        this.updateOpBlock();
        this.updateMessages();
    }

    /**
     * Updates the config.yml file.
     */
    private void updateConfig() {
        final Configuration.ConfigurationOptions config =
                Universal.get().configuration().getConfiguration(ConfigurationFiles.CONFIGURATION);

        try { // Copies file to .old file
            final File sourceFile = new File(config.commandBlockerFolder + "config.yml");
            final File destinationFile = new File(config.commandBlockerFolder + "config.yml.old");
            if (!destinationFile.exists())
                if (!destinationFile.createNewFile()) {
                    return;
                }
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        // At end, set version to newest version
        config.set("Version", Universal.get().universalMethods().getCommandBlockerVersion(), "The version this " +
                "configuration file is set to.\n* PLEASE DO NOT CHANGE THIS VALUE. *");
        config.save();
    }

    /**
     * Updates the disabled.yml file.
     */
    private void updateBlocked() {
        final Configuration.ConfigurationOptions blocked =
                Universal.get().configuration().getConfiguration(ConfigurationFiles.BLOCKED);

        try { // Copies file to .old file
            final File sourceFile = new File(blocked.commandBlockerFolder + "disabled.yml");
            final File destinationFile = new File(blocked.commandBlockerFolder + "disabled.yml.old");
            if (!destinationFile.exists())
                if (!destinationFile.createNewFile()) {
                    return;
                }
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates the opblock.yml file.
     */
    private void updateOpBlock() {
        final Configuration.ConfigurationOptions opBlocked =
                Universal.get().configuration().getConfiguration(ConfigurationFiles.OPBLOCKED);

        try { // Copies file to .old file
            final File sourceFile = new File(opBlocked.commandBlockerFolder + "opblock.yml");
            final File destinationFile = new File(opBlocked.commandBlockerFolder + "opblock.yml.old");
            if (!destinationFile.exists())
                if (!destinationFile.createNewFile()) {
                    return;
                }
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the messages.yml file.
     */
    private void updateMessages() {
        final Configuration.ConfigurationOptions messages =
                Universal.get().configuration().getConfiguration(ConfigurationFiles.MESSAGES);

        try { // Copies file to .old file
            final File sourceFile = new File(messages.commandBlockerFolder + "messages.yml");
            final File destinationFile = new File(messages.commandBlockerFolder + "messages.yml.old");
            if (!destinationFile.exists())
                if (!destinationFile.createNewFile()) {
                    return;
                }
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

}
