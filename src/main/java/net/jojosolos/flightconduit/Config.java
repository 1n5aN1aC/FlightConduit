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

    private static final ForgeConfigSpec.DoubleValue RANGE_MULTIPLIER = BUILDER
            .comment("Flight effect range multiplier\n\nMultiplies the default range calculation (frame_blocks / 7 * 10)\nDefault: 1.0, Range: 0.05 to 10.0")
            .defineInRange("rangeMultiplier", 1.0, 0.05, 10.0);

    private static final ForgeConfigSpec.IntValue FEATHER_FALLING_DURATION = BUILDER
            .comment("Feather falling (slow falling) effect duration in ticks\n\nApplied when exiting the flight conduit range\nDefault: 300 ticks (15 seconds), Range: 0 to 1200 ticks (60 seconds)")
            .defineInRange("featherFallingDuration", 300, 0, 1200);

    private static final ForgeConfigSpec.BooleanValue FEATHER_FALLING_NEAR_GROUND = BUILDER
            .comment("Disable feather falling when near ground\n\nWhen enabled, feather falling will not be applied if the player is within a certain distance of the ground\nDefault: false")
            .define("featherFallingNearGround", false);

    private static final ForgeConfigSpec.IntValue FEATHER_FALLING_NEAR_GROUND_BLOCKS = BUILDER
            .comment("Distance from ground to disable feather falling\n\nOnly applies if featherFallingNearGround is enabled\nDefault: 5 blocks, Range: 1 to 20 blocks")
            .defineInRange("featherFallingNearGroundBlocks", 5, 1, 100);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static Set<Block> blocks;
    public static double rangeMultiplier;
    public static int featherFallingDuration;
    public static boolean featherFallingNearGround;
    public static int featherFallingNearGroundBlocks;

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
        
        rangeMultiplier = RANGE_MULTIPLIER.get();
        featherFallingDuration = FEATHER_FALLING_DURATION.get();
        featherFallingNearGround = FEATHER_FALLING_NEAR_GROUND.get();
        featherFallingNearGroundBlocks = FEATHER_FALLING_NEAR_GROUND_BLOCKS.get();
    }
}
