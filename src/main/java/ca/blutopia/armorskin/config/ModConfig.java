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