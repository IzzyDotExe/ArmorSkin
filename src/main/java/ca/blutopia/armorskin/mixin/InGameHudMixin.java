package ca.blutopia.armorskin.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

    @Shadow protected abstract Player getCameraPlayer();

    @Shadow private int displayHealth;
    @Shadow private int screenHeight;
    @Shadow private int screenWidth;

    @Shadow @Final private static ResourceLocation GUI_ICONS_LOCATION;

    @Redirect(method = "renderPlayerHealth", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 0))
    private void RedirectHalfArmor(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, int u, int v, int width, int height) {

    }
    @Redirect(method = "renderPlayerHealth", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 1))
    private void RedirectFullArmor(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, int u, int v, int width, int height) {

    }
    @Redirect(method = "renderPlayerHealth", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 2))
    private void RedirectEmptyArmor(GuiGraphics instance, ResourceLocation resourceLocation, int x, int y, int u, int v, int width, int height) {

    }

    @Inject(method = "renderPlayerHealth", at=@At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;push(Ljava/lang/String;)V", ordinal = 0))
    private void RenderArmorBarInject(GuiGraphics guiGraphics, CallbackInfo ci) {
        var player = getCameraPlayer();
        int i = Mth.ceil(player.getHealth());
        int j = displayHealth;
        float f = Math.max((float)player.getAttributeValue(Attributes.MAX_HEALTH), (float)Math.max(j, i));
        int p = Mth.ceil(player.getAbsorptionAmount());
        int q = Mth.ceil((f + (float)p) / 2.0f / 10.0f);
        int r = Math.max(10 - (q - 2), 3);
        int o = screenHeight - 39;
        int s = o - (q - 1) * r - 10;
        int x = screenWidth / 2 - 91;

        renderArmorBar(guiGraphics, player, x, s);
    }

    public <K> K getHighestNumericValue(Dictionary<K, Number> dict) {
        K highVal = null;
        Number prevVal = 0;
        for (Iterator<K> it = dict.keys().asIterator(); it.hasNext(); ) {
            K key = it.next();

            if (dict.get(key).doubleValue() >  prevVal.doubleValue()) {
                highVal = key;
                prevVal = dict.get(key);
            }
        }

        return highVal;
    }

    public void renderArmorBar(GuiGraphics graphics, Player playerEntity, int x, int y) {

        int armor = playerEntity.getArmorValue();
        var armorslots = playerEntity.getArmorSlots();

        Dictionary<ArmorMaterial, Number> defenseDict = new Hashtable<>();

        for (ItemStack slot :
                armorslots) {
            if (slot.getItem().getClass() != ArmorItem.class && slot.getItem().getClass() != DyeableArmorItem.class)
                continue;

            var item = (ArmorItem)slot.getItem().asItem();
            var mat = item.getMaterial();
            var defense = mat.getDefenseForType(item.getType());
            try {
                defenseDict.put(mat, defenseDict.get(mat).intValue() + defense);
            }catch (NullPointerException ex) {
                defenseDict.put(mat, defense);
            }
        }

        var current = getHighestNumericValue(defenseDict);

        int xrigid;
        for(int w = 0; w < 10; ++w) {

            if (current == null || defenseDict.get(current).intValue() <= 0)
                current = getHighestNumericValue(defenseDict);

            var Vvalue = switch (current != null? current.getName().toLowerCase() : "null") {
                case "diamond" -> 9;
                case "leather" -> 0;
                case "netherite" -> 18;
                case "gold" -> 27;
                case "chainmail" -> 36;
                default -> 45;
            };


            if (armor > 0) {
                xrigid = x + w * 8;
                if (w * 2 + 1 < armor) {

                    graphics.blit(GUI_ICONS_LOCATION, xrigid, y, 238, Vvalue, 9, 9);
                    if (current != null)
                        defenseDict.put(current, defenseDict.get(current).intValue() - 2);
                }

                if (w * 2 + 1 == armor) {
                    graphics.blit(GUI_ICONS_LOCATION, xrigid, y, 229, Vvalue, 9, 9);
                    if (current != null)
                        defenseDict.put(current, defenseDict.get(current).intValue() - 1);
                }

                if (w * 2 + 1 > armor) {
                    graphics.blit(GUI_ICONS_LOCATION, xrigid, y, 220, Vvalue, 9, 9);
                }
            }
        }
    }


}
