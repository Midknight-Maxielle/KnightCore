package com.midknight.knightcore.item;


import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class CoreBowItem extends BowItem {

    // Fields

    public int DMG_MOD;
    public int DRAW_MOD;
    public boolean INFINITE_FLAG;
    public boolean CREATIVE_FLAG;

    // Constructor Methods

    public CoreBowItem(Properties properties, int dmgMod, int drawMod, boolean inf) {
        super(properties);
        DMG_MOD = dmgMod;
        DRAW_MOD = drawMod;
        INFINITE_FLAG = inf;
    }

    // Methods //

    public boolean canDraw(ItemStack arrows, Player player) {
        CREATIVE_FLAG = player.getAbilities().instabuild;
        return
                CREATIVE_FLAG ||
                EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, arrows) > 0 ||
                !player.getProjectile(arrows).isEmpty() ||
                INFINITE_FLAG;
    }

    // Override Methods

   @Nonnull @ParametersAreNonnullByDefault
    public InteractionResultHolder<ItemStack> use (Level world, Player player, InteractionHand hand) {
        ItemStack bow = player.getItemInHand(hand);
        boolean drawableFlag = canDraw(bow, player);

        InteractionResultHolder<ItemStack> intResult = net.minecraftforge.event.ForgeEventFactory.onArrowNock(bow, world, player, hand, drawableFlag);
        if (intResult != null) return intResult;

        if (!CREATIVE_FLAG && !drawableFlag) {
            return InteractionResultHolder.fail(bow);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(bow);
        }
    }

    @Override @ParametersAreNonnullByDefault
    public void releaseUsing(ItemStack bow, Level worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player playerEntity) {
            boolean drawableFlag = canDraw(bow, playerEntity);
            CREATIVE_FLAG = playerEntity.getAbilities().instabuild;
            ItemStack arrowStack = playerEntity.getProjectile(bow);

            int i = this.getUseDuration(bow) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bow, worldIn, playerEntity, i, !arrowStack.isEmpty() || drawableFlag);
            if (i < 0) return;

            if (!arrowStack.isEmpty() || drawableFlag) {
                if (arrowStack.isEmpty()) {
                    arrowStack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean infiniteFlag = CREATIVE_FLAG || INFINITE_FLAG || (arrowStack.getItem() instanceof ArrowItem && ((ArrowItem)arrowStack.getItem()).isInfinite(arrowStack, bow, playerEntity));
                    if (!worldIn.isClientSide()) {
                        ArrowItem arrowitem = (ArrowItem)(arrowStack.getItem() instanceof ArrowItem ? arrowStack.getItem() : Items.ARROW);
                        AbstractArrow abArrowEntity = arrowitem.createArrow(worldIn, arrowStack, playerEntity);
                        abArrowEntity = customArrow(abArrowEntity);
                        abArrowEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abArrowEntity.setCritArrow(true);
                        }

                        int sumDamageModifier = (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, bow) + DMG_MOD);
                        if (sumDamageModifier > 0) { abArrowEntity.setBaseDamage(abArrowEntity.getBaseDamage() + (double)sumDamageModifier * 0.5D + 0.5D); }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, bow);
                        if (k > 0) {
                            abArrowEntity.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, bow) > 0) {
                            abArrowEntity.setSecondsOnFire(100);
                        }

                        bow.hurtAndBreak(1, playerEntity, (player) -> player.broadcastBreakEvent(playerEntity.getUsedItemHand()));
                        if (infiniteFlag || CREATIVE_FLAG && (arrowStack.getItem() == Items.SPECTRAL_ARROW || arrowStack.getItem() == Items.TIPPED_ARROW)) {
                            abArrowEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        worldIn.addFreshEntity(abArrowEntity);
                    }

                    worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (worldIn.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!infiniteFlag && !CREATIVE_FLAG) {
                        arrowStack.shrink(1);
                        if (arrowStack.isEmpty()) {
                            playerEntity.getInventory().removeItem(arrowStack);
                        }
                    }
                    playerEntity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }
}

