package ca.blutopia.armorskin.config;

public enum ArmorType
{
  LEATHER(ArmorConfig.FULL_U, ArmorConfig.LEATHER_V),
  LEATHER_HALF(ArmorConfig.HALF_U, ArmorConfig.LEATHER_V),
  LEATHER_IRON( ArmorConfig.IRON_HALF_U, ArmorConfig.LEATHER_V),
  LEATHER_DIAMOND( ArmorConfig.DIAMOND_HALF_U, ArmorConfig.LEATHER_V),
  LEATHER_NETHERITE( ArmorConfig.NETHERITE_HALF_U, ArmorConfig.LEATHER_V),
  LEATHER_GOLD( ArmorConfig.GOLD_HALF_U, ArmorConfig.LEATHER_V),
  LEATHER_CHAIN( ArmorConfig.CHAIN_HALF_U, ArmorConfig.LEATHER_V),
  LEATHER_TURTLE(ArmorConfig.TURTLE_HALF_U, ArmorConfig.LEATHER_V),

  CHAIN(ArmorConfig.FULL_U, ArmorConfig.CHAIN_V),
  CHAIN_HALF(ArmorConfig.HALF_U, ArmorConfig.CHAIN_V),
  CHAIN_IRON( ArmorConfig.IRON_HALF_U, ArmorConfig.CHAIN_V),
  CHAIN_DIAMOND( ArmorConfig.DIAMOND_HALF_U, ArmorConfig.CHAIN_V),
  CHAIN_NETHERITE( ArmorConfig.NETHERITE_HALF_U, ArmorConfig.CHAIN_V),
  CHAIN_GOLD( ArmorConfig.GOLD_HALF_U, ArmorConfig.CHAIN_V),
  CHAIN_LEATHER( ArmorConfig.CHAIN_HALF_U, ArmorConfig.CHAIN_V),
  CHAIN_TURTLE(ArmorConfig.TURTLE_HALF_U, ArmorConfig.CHAIN_V),

  IRON(ArmorConfig.FULL_U, ArmorConfig.IRON_V),
  IRON_HALF(ArmorConfig.HALF_U, ArmorConfig.IRON_V),
  IRON_CHAIN( ArmorConfig.CHAIN_HALF_U, ArmorConfig.IRON_V),
  IRON_DIAMOND( ArmorConfig.DIAMOND_HALF_U, ArmorConfig.IRON_V),
  IRON_NETHERITE( ArmorConfig.NETHERITE_HALF_U, ArmorConfig.IRON_V),
  IRON_GOLD( ArmorConfig.GOLD_HALF_U, ArmorConfig.IRON_V),
  IRON_LEATHER( ArmorConfig.IRON_HALF_U, ArmorConfig.IRON_V),
  IRON_TURTLE(ArmorConfig.TURTLE_HALF_U, ArmorConfig.IRON_V),

  GOLD(ArmorConfig.FULL_U, ArmorConfig.GOLD_V),
  GOLD_HALF(ArmorConfig.HALF_U, ArmorConfig.GOLD_V),
  GOLD_IRON( ArmorConfig.IRON_HALF_U, ArmorConfig.GOLD_V),
  GOLD_DIAMOND( ArmorConfig.DIAMOND_HALF_U, ArmorConfig.GOLD_V),
  GOLD_NETHERITE( ArmorConfig.NETHERITE_HALF_U, ArmorConfig.GOLD_V),
  GOLD_LEATHER( ArmorConfig.GOLD_HALF_U, ArmorConfig.GOLD_V),
  GOLD_CHAIN( ArmorConfig.CHAIN_HALF_U, ArmorConfig.GOLD_V),
  GOLD_TURTLE(ArmorConfig.TURTLE_HALF_U, ArmorConfig.GOLD_V),

  DIAMOND(ArmorConfig.FULL_U, ArmorConfig.DIAMOND_V),
  DIAMOND_HALF(ArmorConfig.HALF_U, ArmorConfig.DIAMOND_V),
  DIAMOND_IRON( ArmorConfig.IRON_HALF_U, ArmorConfig.DIAMOND_V),
  DIAMOND_CHAIN( ArmorConfig.CHAIN_HALF_U, ArmorConfig.DIAMOND_V),
  DIAMOND_NETHERITE( ArmorConfig.NETHERITE_HALF_U, ArmorConfig.DIAMOND_V),
  DIAMOND_LEATHER( ArmorConfig.DIAMOND_HALF_U, ArmorConfig.DIAMOND_V),
  DIAMOND_GOLD( ArmorConfig.GOLD_HALF_U, ArmorConfig.DIAMOND_V),
  DIAMOND_TURTLE(ArmorConfig.TURTLE_HALF_U, ArmorConfig.DIAMOND_V),

  NETHERITE( ArmorConfig.FULL_U, ArmorConfig.NETHERITE_V ),
  NETHERITE_HALF( ArmorConfig.HALF_U, ArmorConfig.NETHERITE_V ),
  NETHERITE_IRON( ArmorConfig.IRON_HALF_U, ArmorConfig.NETHERITE_V ),
  NETHERITE_DIAMOND( ArmorConfig.DIAMOND_HALF_U, ArmorConfig.NETHERITE_V ),
  NETHERITE_CHAIN( ArmorConfig.CHAIN_HALF_U, ArmorConfig.NETHERITE_V ),
  NETHERITE_GOLD( ArmorConfig.GOLD_HALF_U, ArmorConfig.NETHERITE_V ),
  NETHERITE_LEATHER( ArmorConfig.NETHERITE_HALF_U, ArmorConfig.NETHERITE_V ),
  NETHERITE_TURTLE(ArmorConfig.TURTLE_HALF_U, ArmorConfig.NETHERITE_V),

  TURTLE(ArmorConfig.FULL_U, ArmorConfig.TURTLE_V),
  TURTLE_HALF(ArmorConfig.HALF_U, ArmorConfig.TURTLE_V),
  TURTLE_LEATHER(ArmorConfig.TURTLE_HALF_U, ArmorConfig.TURTLE_V),
  TURTLE_IRON(ArmorConfig.IRON_HALF_U, ArmorConfig.TURTLE_V),
  TURTLE_DIAMOND(ArmorConfig.DIAMOND_HALF_U, ArmorConfig.TURTLE_V),
  TURTLE_NETHERITE(ArmorConfig.NETHERITE_HALF_U, ArmorConfig.TURTLE_V),
  TURTLE_GOLD(ArmorConfig.GOLD_HALF_U, ArmorConfig.TURTLE_V),
  TURTLE_CHAIN(ArmorConfig.CHAIN_HALF_U, ArmorConfig.TURTLE_V),

  ELYTRA(ArmorConfig.EMPTY_U, ArmorConfig.ELYTRA_V),
  EMPTY(ArmorConfig.EMPTY_U, ArmorConfig.LEATHER_V),
  DYNAMIC(0,0);

  public final int u;
  public final int v;

  ArmorType( int u, int v )
  {
    this.u = u;
    this.v = v;
  }
}

