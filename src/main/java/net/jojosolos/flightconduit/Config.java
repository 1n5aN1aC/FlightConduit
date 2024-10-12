package net.jojosolos.flightconduit;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = FlightConduit.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLOCK_STRINGS = BUILDER
            .comment("Flight Conduit Mod Config - select blocks allowed for Conduit Structure\n\nAllowed blocks for structure around the flight conduit")
            .defineListAllowEmpty("blocks",
                    List.of("minecraft:oxidized_copper", "minecraft:waxed_oxidized_copper", "minecraft:weathered_copper", "minecraft:waxed_weathered_copper"),
                    Config::validateBlockName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Set<Block> blocks;

    private static boolean validateBlockName(final Object obj)
    {
        return obj instanceof final String blockName && ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(blockName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        blocks = BLOCK_STRINGS.get().stream()
                .map(blockName -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)))
                .collect(Collectors.toSet());
    }
}
