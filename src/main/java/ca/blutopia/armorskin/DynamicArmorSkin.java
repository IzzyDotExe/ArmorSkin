package ca.blutopia.armorskin;

import ca.blutopia.armorskin.config.ArmorType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class DynamicArmorSkin {

  private Map<ArmorMaterial, Integer> armorPoints = new HashMap<ArmorMaterial, Integer>();
  ArmorMaterial current = ArmorMaterial.NONE;
  private Minecraft client;

  public DynamicArmorSkin(Minecraft client) {
    this.client = client;
  }

  public <K> K getHighestNumericValue(Map<K, Integer> map) {
    K highVal = null;
    int prevVal = 0;

    for ( K key : map.keySet() )
    {
      if ( map.get( key ) > prevVal )
      {
        highVal = key;
        prevVal = map.get( key );
      }
    }

    if (highVal == null && map.size() == 1) return (K) map.keySet().toArray()[0];

    return highVal;
  }

  private void putOrAdd(Map<ArmorMaterial, Integer> map, ArmorMaterial key, int value) {
    if (map.containsKey(key)) {
      map.put(key, map.get(key) + value);
    } else {
      map.put(key, value);
    }
  }

  public void storeArmorValues() {

    armorPoints.clear();

    if (client.player == null) {
      return;
    }

    Player player = client.player;

    // Get armor pieces
    ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
    ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
    ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
    ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

    // Get materials
    ArmorMaterial bootsMaterial = getArmorMaterial( boots );
    ArmorMaterial leggingsMaterial = getArmorMaterial( leggings );
    ArmorMaterial chestplateMaterial = getArmorMaterial( chestplate );
    ArmorMaterial helmetMaterial = getArmorMaterial( helmet );

    // Get armor values (in defense points)
    putOrAdd(armorPoints, bootsMaterial, getArmorValue( boots));
    putOrAdd(armorPoints, leggingsMaterial, getArmorValue( leggings));
    putOrAdd(armorPoints, chestplateMaterial, getArmorValue( chestplate));
    putOrAdd(armorPoints, helmetMaterial, getArmorValue( helmet));

    current = ArmorMaterial.NONE;

  }

  public ArmorType getArmorTypeForIcon(int iconIndex) {

    // Set the current armor material we're handling
    if ( current == ArmorMaterial.NONE)
      current = getHighestNumericValue(armorPoints);

    if (current == ArmorMaterial.NONE) return ArmorType.EMPTY;

    if (armorPoints.get( current ) >= 2) {

      armorPoints.put( current, armorPoints.get( current ) - 2 );
      ArmorType type = getArmorType(current, false, null);

      if (armorPoints.get( current ) == 0) {
        armorPoints.remove( current );
        current = getHighestNumericValue(armorPoints);
      }

      return type;
    } else {
      armorPoints.remove( current );
      ArmorMaterial secondary = getHighestNumericValue(armorPoints);

      if (secondary == null) {
        secondary = ArmorMaterial.NONE;
      } else {
        armorPoints.put(secondary, armorPoints.get( secondary ) - 1);
        if (armorPoints.get( secondary ) == 0) armorPoints.remove( secondary );
      }

      ArmorType type = getArmorType(current, true, secondary);
      current = getHighestNumericValue( armorPoints );

      return type;
    }

  }

  private ArmorType getArmorType(ArmorMaterial primary, boolean isMixed, ArmorMaterial secondary) {
    if (!isMixed) {
      // Full icon of single material
      return switch ( primary )
      {
        case LEATHER -> ArmorType.LEATHER;
        case CHAINMAIL -> ArmorType.CHAIN;
        case IRON -> ArmorType.IRON;
        case GOLD -> ArmorType.GOLD;
        case DIAMOND -> ArmorType.DIAMOND;
        case NETHERITE -> ArmorType.NETHERITE;
        default -> ArmorType.EMPTY;
      };
    } else {
      // Mixed icon - primary background with secondary overlay
      if (primary == ArmorMaterial.LEATHER && secondary == ArmorMaterial.CHAINMAIL) return ArmorType.LEATHER_CHAIN;
      if (primary == ArmorMaterial.LEATHER && secondary == ArmorMaterial.IRON) return ArmorType.LEATHER_IRON;
      if (primary == ArmorMaterial.LEATHER && secondary == ArmorMaterial.GOLD) return ArmorType.LEATHER_GOLD;
      if (primary == ArmorMaterial.LEATHER && secondary == ArmorMaterial.DIAMOND) return ArmorType.LEATHER_DIAMOND;
      if (primary == ArmorMaterial.LEATHER && secondary == ArmorMaterial.NETHERITE) return ArmorType.LEATHER_NETHERITE;
      if (primary == ArmorMaterial.LEATHER && secondary == ArmorMaterial.NONE) return ArmorType.LEATHER_HALF;

      if (primary == ArmorMaterial.CHAINMAIL && secondary == ArmorMaterial.LEATHER) return ArmorType.CHAIN_LEATHER;
      if (primary == ArmorMaterial.CHAINMAIL && secondary == ArmorMaterial.IRON) return ArmorType.CHAIN_IRON;
      if (primary == ArmorMaterial.CHAINMAIL && secondary == ArmorMaterial.GOLD) return ArmorType.CHAIN_GOLD;
      if (primary == ArmorMaterial.CHAINMAIL && secondary == ArmorMaterial.DIAMOND) return ArmorType.CHAIN_DIAMOND;
      if (primary == ArmorMaterial.CHAINMAIL && secondary == ArmorMaterial.NETHERITE) return ArmorType.CHAIN_NETHERITE;
      if (primary == ArmorMaterial.CHAINMAIL && secondary == ArmorMaterial.NONE) return ArmorType.CHAIN_HALF;

      if (primary == ArmorMaterial.IRON && secondary == ArmorMaterial.LEATHER) return ArmorType.IRON_LEATHER;
      if (primary == ArmorMaterial.IRON && secondary == ArmorMaterial.CHAINMAIL) return ArmorType.IRON_CHAIN;
      if (primary == ArmorMaterial.IRON && secondary == ArmorMaterial.GOLD) return ArmorType.IRON_GOLD;
      if (primary == ArmorMaterial.IRON && secondary == ArmorMaterial.DIAMOND) return ArmorType.IRON_DIAMOND;
      if (primary == ArmorMaterial.IRON && secondary == ArmorMaterial.NETHERITE) return ArmorType.IRON_NETHERITE;
      if (primary == ArmorMaterial.IRON && secondary == ArmorMaterial.NONE) return ArmorType.IRON_HALF;

      if (primary == ArmorMaterial.GOLD && secondary == ArmorMaterial.LEATHER) return ArmorType.GOLD_LEATHER;
      if (primary == ArmorMaterial.GOLD && secondary == ArmorMaterial.CHAINMAIL) return ArmorType.GOLD_CHAIN;
      if (primary == ArmorMaterial.GOLD && secondary == ArmorMaterial.IRON) return ArmorType.GOLD_IRON;
      if (primary == ArmorMaterial.GOLD && secondary == ArmorMaterial.DIAMOND) return ArmorType.GOLD_DIAMOND;
      if (primary == ArmorMaterial.GOLD && secondary == ArmorMaterial.NETHERITE) return ArmorType.GOLD_NETHERITE;
      if (primary == ArmorMaterial.GOLD && secondary == ArmorMaterial.NONE) return ArmorType.GOLD_HALF;

      if (primary == ArmorMaterial.DIAMOND && secondary == ArmorMaterial.LEATHER) return ArmorType.DIAMOND_LEATHER;
      if (primary == ArmorMaterial.DIAMOND && secondary == ArmorMaterial.CHAINMAIL) return ArmorType.DIAMOND_CHAIN;
      if (primary == ArmorMaterial.DIAMOND && secondary == ArmorMaterial.IRON) return ArmorType.DIAMOND_IRON;
      if (primary == ArmorMaterial.DIAMOND && secondary == ArmorMaterial.GOLD) return ArmorType.DIAMOND_GOLD;
      if (primary == ArmorMaterial.DIAMOND && secondary == ArmorMaterial.NETHERITE) return ArmorType.DIAMOND_NETHERITE;
      if (primary == ArmorMaterial.DIAMOND && secondary == ArmorMaterial.NONE) return ArmorType.DIAMOND_HALF;

      if (primary == ArmorMaterial.NETHERITE && secondary == ArmorMaterial.LEATHER) return ArmorType.NETHERITE_LEATHER;
      if (primary == ArmorMaterial.NETHERITE && secondary == ArmorMaterial.CHAINMAIL) return ArmorType.NETHERITE_CHAIN;
      if (primary == ArmorMaterial.NETHERITE && secondary == ArmorMaterial.IRON) return ArmorType.NETHERITE_IRON;
      if (primary == ArmorMaterial.NETHERITE && secondary == ArmorMaterial.GOLD) return ArmorType.NETHERITE_GOLD;
      if (primary == ArmorMaterial.NETHERITE && secondary == ArmorMaterial.DIAMOND) return ArmorType.NETHERITE_DIAMOND;
      if (primary == ArmorMaterial.NETHERITE && secondary == ArmorMaterial.NONE) return ArmorType.NETHERITE_HALF;

      return ArmorType.EMPTY;
    }
  }

  private int getArmorValue(ItemStack armorPiece) {
    if (armorPiece.isEmpty() || !( armorPiece.getItem() instanceof ArmorItem armorItem )) {
      return 0;
    }
    return armorItem.getDefense();
  }

  private ArmorMaterial getArmorMaterial(ItemStack armorPiece) {
    if (armorPiece.isEmpty() || !(armorPiece.getItem() instanceof ArmorItem)) {
      return ArmorMaterial.NONE;
    }

    ArmorItem armorItem = (ArmorItem) armorPiece.getItem();

    if (armorItem == Items.LEATHER_HELMET || armorItem == Items.LEATHER_CHESTPLATE ||
      armorItem == Items.LEATHER_LEGGINGS || armorItem == Items.LEATHER_BOOTS) {
      return ArmorMaterial.LEATHER;
    } else if (armorItem == Items.CHAINMAIL_HELMET || armorItem == Items.CHAINMAIL_CHESTPLATE ||
      armorItem == Items.CHAINMAIL_LEGGINGS || armorItem == Items.CHAINMAIL_BOOTS) {
      return ArmorMaterial.CHAINMAIL;
    } else if (armorItem == Items.IRON_HELMET || armorItem == Items.IRON_CHESTPLATE ||
      armorItem == Items.IRON_LEGGINGS || armorItem == Items.IRON_BOOTS) {
      return ArmorMaterial.IRON;
    } else if (armorItem == Items.GOLDEN_HELMET || armorItem == Items.GOLDEN_CHESTPLATE ||
      armorItem == Items.GOLDEN_LEGGINGS || armorItem == Items.GOLDEN_BOOTS) {
      return ArmorMaterial.GOLD;
    } else if (armorItem == Items.DIAMOND_HELMET || armorItem == Items.DIAMOND_CHESTPLATE ||
      armorItem == Items.DIAMOND_LEGGINGS || armorItem == Items.DIAMOND_BOOTS) {
      return ArmorMaterial.DIAMOND;
    } else if (armorItem == Items.NETHERITE_HELMET || armorItem == Items.NETHERITE_CHESTPLATE ||
      armorItem == Items.NETHERITE_LEGGINGS || armorItem == Items.NETHERITE_BOOTS) {
      return ArmorMaterial.NETHERITE;
    }

    return ArmorMaterial.NONE;
  }

  private enum ArmorMaterial {
    NONE, LEATHER, CHAINMAIL, IRON, GOLD, DIAMOND, NETHERITE
  }
}
