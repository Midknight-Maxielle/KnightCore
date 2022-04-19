package com.midknight.knightcore;

import com.midknight.knightcore.util.KnightcoreRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Knightcore.MOD_ID)
public class Knightcore
{
    public static final String MOD_ID = "knightcore";

    public Knightcore() {

        KnightcoreRegistry.registerITEM();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
