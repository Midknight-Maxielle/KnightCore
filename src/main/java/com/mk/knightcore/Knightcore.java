package com.mk.knightcore;

import com.mk.knightcore.util.KnightcoreRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Knightcore.MOD_ID)
public class Knightcore
{
    public static final String MOD_ID = "knightcore";

    public Knightcore() {

        KnightcoreRegistry.registerENTITY_TYPES();
        KnightcoreRegistry.registerITEMS();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
