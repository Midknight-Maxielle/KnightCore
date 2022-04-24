package com.midknight.knightcore.events;

import com.midknight.knightcore.Knightcore;

import com.midknight.knightcore.item.CoreBowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Knightcore.MOD_ID)
public class CoreEventHandler {

    @SubscribeEvent
    public static void handleBowFOV(FOVModifierEvent event) {
        if (event.getEntity().isUsingItem() && event.getEntity().getMainHandItem().getItem() instanceof CoreBowItem) {

            float fovModifier = event.getEntity().getTicksUsingItem() / 20.0F;
            if (fovModifier > 1.0F) { fovModifier = 1.0F; }
            else { fovModifier *= fovModifier; }
            event.setNewfov(event.getFov() * (1.0F - fovModifier * 0.20F));
        }
    }

    @SubscribeEvent
    public static void handleBowDrawEvent(LivingEntityUseItemEvent.Start event) {

        int drawMod = 0;
        ItemStack item = event.getItem();

        if(item.getItem() instanceof CoreBowItem bowItem) { drawMod = bowItem.DRAW_MOD; }
        if(drawMod != 0 && event.getDuration() > item.getUseDuration() - 20) {
            event.setDuration(event.getDuration() - drawMod);
        }
    }
}
