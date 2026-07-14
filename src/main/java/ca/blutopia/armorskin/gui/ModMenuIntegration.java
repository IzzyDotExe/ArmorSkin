// SPDX-License-Identifier: GPL-3.0-or-later
// Copyright (C) 2021-2026 IzzyDotExe and ArmorSkin contributors
//
// This file is part of ArmorSkin. ArmorSkin is free software: you can
// redistribute it and/or modify it under the terms of the GNU General Public
// License as published by the Free Software Foundation, either version 3 of the
// License, or (at your option) any later version. See the LICENSE file for the
// full license text.

package ca.blutopia.armorskin.gui;

import ca.blutopia.armorskin.config.ModConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {

    return parent -> AutoConfigClient.getConfigScreen( ModConfig.class, parent).get();

  }
}
