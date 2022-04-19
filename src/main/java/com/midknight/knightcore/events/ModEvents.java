package com.midknight.knightcore.events;

import com.midknight.knightcore.Knightcore;

import com.midknight.knightcore.item.KnightcoreBowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Knightcore.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void handleBowDrawEvent(LivingEntityUseItemEvent.Tick event) {

        int drawMod = 0;
        ItemStack item = event.getItem();

        if(item.getItem() instanceof KnightcoreBowItem bowItem) {
            drawMod = bowItem.drawMod;
        }
        if(drawMod != 0 && event.getDuration() > event.getItem().getUseDuration() - 20) {
            event.setDuration(event.getDuration() - drawMod);
        }
    }
}
