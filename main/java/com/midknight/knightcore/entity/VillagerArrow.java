package com.midknight.knightcore.entity;

import com.midknight.knightcore.util.KnightcoreRegistry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import javax.annotation.Nonnull;

public class VillagerArrow extends AbstractArrow {

    // Fields

    public static double baseDamage = 1.5D;
    public static EntityType<VillagerArrow> VILLAGER_ARROW_ENTITY = KnightcoreRegistry.VILLAGER_ARROW_ENTITY.get();

    // Constructor Methods

    public VillagerArrow(EntityType<? extends VillagerArrow> entity, Level world) {
        super(entity, world);
        this.setBaseDamage(baseDamage);
    }

    public VillagerArrow(Level world, LivingEntity shooter) {
        super(VILLAGER_ARROW_ENTITY, shooter, world);
        this.setBaseDamage(baseDamage);
    }

    public VillagerArrow(PlayMessages.SpawnEntity packet, Level world) {
        super(VILLAGER_ARROW_ENTITY, world);
        this.setBaseDamage(baseDamage);
    }

    @Override @Nonnull
    protected ItemStack getPickupItem() {
        return new ItemStack(KnightcoreRegistry.VILLAGER_ARROW.get());
    }

    @Override @Nonnull
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}