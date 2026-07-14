// SPDX-License-Identifier: GPL-3.0-or-later
// Copyright (C) 2021-2026 IzzyDotExe and ArmorSkin contributors
//
// This file is part of ArmorSkin. ArmorSkin is free software: you can
// redistribute it and/or modify it under the terms of the GNU General Public
// License as published by the Free Software Foundation, either version 3 of the
// License, or (at your option) any later version. See the LICENSE file for the
// full license text.

package ca.blutopia.armorskin.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="armorskin")
public class ModConfig implements ConfigData {

  @ConfigEntry.Category( "armorskin" )
  public ArmorType armorSkin =  ArmorType.DYNAMIC;

  @ConfigEntry.Category( "armorskin" )
  public boolean showElytra = true;

  @ConfigEntry.Category( "armorskin" )
  public int elytraIconPosition = 9;

}