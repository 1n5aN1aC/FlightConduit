package net.jojosolos.flightconduit.item;

import net.jojosolos.flightconduit.FlightConduit;
import net.jojosolos.flightconduit.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs extends CreativeModeTab{

    public static final ModCreativeModeTabs FC_TAB = new ModCreativeModeTabs(CreativeModeTab.TABS.length, "flightconduit");

    public ModCreativeModeTabs(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ModBlocks.FLIGHT_CONDUIT_BLOCK.get());
    }
}
