package com.midknight.knightcore.util;

import com.midknight.knightcore.Knightcore;
import com.midknight.knightcore.entity.BarbedArrow;
import com.midknight.knightcore.entity.VillagerArrow;
import com.midknight.knightcore.item.arrows.BarbedArrowItem;
import com.midknight.knightcore.item.arrows.VillagerArrowItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class KnightcoreRegistry {

    protected static IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final String VILLAGER_ARROW_NAME = "villager_arrow";
    public static final String BARBED_ARROW_NAME = "barbed_arrow";

    // Deferred Registers

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.ENTITIES, Knightcore.MOD_ID);

    public static DeferredRegister<GlobalLootModifierSerializer<?>> GLMS =
        DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Knightcore.MOD_ID);

    public static final DeferredRegister<Item> ITEM =
    DeferredRegister.create(ForgeRegistries.ITEMS, Knightcore.MOD_ID);

    // - - - - - - - - - //
    // Registry Objects  //
    // - - - - - - - - - //

    // Arrow & Quivers Item //

    public static final RegistryObject<VillagerArrowItem> VILLAGER_ARROW =
            ITEM.register("villager_arrow",
                () -> new VillagerArrowItem(new Item.Properties().tab(KnightcoreTab.KNIGHTCORE_TAB
            )));

    public static final RegistryObject<BarbedArrowItem> BARBED_ARROW =
            ITEM.register("barbed_arrow",
                () -> new BarbedArrowItem(new Item.Properties().tab(KnightcoreTab.KNIGHTCORE_TAB
            )));

    public static final RegistryObject<KnightcoreQuiverItem> QUIVER =
            ITEM.register("quiver", () -> new KnightcoreQuiverItem(new Item.Properties()
                    .durability(128)
                    .tab(KnightcoreTab.KNIGHTCORE_TAB)
            ){

                @Override @ParametersAreNonnullByDefault
                public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
                    tooltip.add(new TranslatableComponent("tooltip.knightcore.quiver"));
                    super.appendHoverText(stack, worldIn, tooltip, flagIn);
                }
            });

    // Arrow Entities //
    public static final RegistryObject<EntityType<VillagerArrow>> VILLAGER_ARROW_ENTITY =
            ENTITY_TYPES.register("villager_arrow",
                    () -> EntityType.Builder
                            .<VillagerArrow>of(VillagerArrow::new, MobCategory.MISC)
                            .setCustomClientFactory(VillagerArrow::new)
                            .sized(0.5F, 0.5F)
                            .setTrackingRange(4)
                            .updateInterval(20)
                            .build(VILLAGER_ARROW_NAME)
            );

    public static final RegistryObject<EntityType<BarbedArrow>> BARBED_ARROW_ENTITY =
            ENTITY_TYPES.register("barbed_arrow",
                    () -> EntityType.Builder
                            .<BarbedArrow>of(BarbedArrow::new, MobCategory.MISC)
                            .setCustomClientFactory(BarbedArrow::new)
                            .sized(0.5F,0.5F)
                            .setTrackingRange(4)
                            .updateInterval(20)
                            .build(BARBED_ARROW_NAME)
            );


    // Register Methods

    public static void registerENTITY() {ENTITY_TYPES.register(eventBus);}
    public static void registerGLMS() {GLMS.register(eventBus);}
    public static void registerITEM() {ITEM.register(eventBus);}
}
