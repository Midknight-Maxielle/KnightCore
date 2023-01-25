package com.midknight.knightcore.util;

import com.midknight.knightcore.Knightcore;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class KnightcoreRegistry {

    protected static IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();


    // Deferred Registers

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Knightcore.MOD_ID);

    public static final DeferredRegister<Item> ITEM =
    DeferredRegister.create(ForgeRegistries.ITEMS, Knightcore.MOD_ID);

    // - - - - - - - - - //
    // Registry Objects  //
    // - - - - - - - - - //

    // Arrow & Quivers Item //


    // Register Methods

    public static void registerITEM() {ITEM.register(eventBus);}
}
