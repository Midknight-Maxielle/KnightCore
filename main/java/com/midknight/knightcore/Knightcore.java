package com.midknight.knightcore;

import com.midknight.knightcore.entity.renderers.BarbedArrowRenderer;
import com.midknight.knightcore.entity.renderers.VillagerArrowRenderer;
import com.midknight.knightcore.util.BowyeryItemModelProperties;
import com.midknight.knightcore.util.KnightcoreRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Knightcore.MOD_ID)
public class Knightcore
{
    public static final String MOD_ID = "bowyery";

    public Knightcore() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::arrowSetup);
        KnightcoreRegistry.registerENTITY();
        KnightcoreRegistry.registerGLMS();
        KnightcoreRegistry.registerITEM();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void arrowSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(KnightcoreRegistry.VILLAGER_ARROW_ENTITY.get(), VillagerArrowRenderer::new);
        event.registerEntityRenderer(KnightcoreRegistry.BARBED_ARROW_ENTITY.get(), BarbedArrowRenderer::new);
    }
}
