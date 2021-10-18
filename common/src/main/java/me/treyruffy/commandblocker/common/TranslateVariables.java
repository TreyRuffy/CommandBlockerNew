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
package me.treyruffy.commandblocker.common;

import java.util.HashMap;
import net.kyori.adventure.text.Component;

/**
 * The interface to help you translate variables.
 */
public interface TranslateVariables {

    /**
     * Translates variables from a component.
     *
     * @param message the component to translate
     * @return the translated component
     */
    Component translateVariables(Component message);

    /**
     * Translates player variables from a component.
     *
     * @param message the component to translate
     * @param player the player to translate for
     * @return the translated component
     */
    Component translateVariables(Component message, Object player);

    /**
     * Translates player variables from a component with additional variables.
     *
     * @param message the component to translate
     * @param player the player to translate for
     * @param additionalVariables additional placeholders to translate in the text
     * @return the translated component
     */
    Component translateVariables(Component message, Object player, HashMap<String, String> additionalVariables,
                                 HashMap<String, Component> additionalComponentVariables);

}
