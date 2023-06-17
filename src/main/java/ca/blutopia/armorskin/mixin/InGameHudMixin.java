package ca.blutopia.armorskin.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

    @Shadow protected abstract Player getCameraPlayer();

    @Shadow private int displayHealth;
    @Shadow private int screenHeight;
    @Shadow private int screenWidth;
    private static final ResourceLocation ARMORSKIN_ICONS_LOCATION = new ResourceLocation("armorskin/textures/gui/armorskin.png");

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

    public void renderArmorBar(GuiGraphics graphics, Player playerEntity, int x, int y) {

        int armor = playerEntity.getArmorValue();
        int xrigid;
        for(int w = 0; w < 10; ++w) {
            if (armor > 0) {
                xrigid = x + w * 8;
                if (w * 2 + 1 < armor) {
                    graphics.blit(ARMORSKIN_ICONS_LOCATION, xrigid, y, 18, 9, 9, 9);
                }

                if (w * 2 + 1 == armor) {
                    graphics.blit(ARMORSKIN_ICONS_LOCATION, xrigid, y, 9, 9, 9, 9);
                }

                if (w * 2 + 1 > armor) {
                    graphics.blit(ARMORSKIN_ICONS_LOCATION, xrigid, y, 0, 9, 9, 9);
                }
            }
        }
    }


}
