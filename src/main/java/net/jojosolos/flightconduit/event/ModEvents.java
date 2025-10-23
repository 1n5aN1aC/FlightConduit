package net.jojosolos.flightconduit.event;

import net.jojosolos.flightconduit.Config;
import net.jojosolos.flightconduit.FlightConduit;
import net.jojosolos.flightconduit.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod.EventBusSubscriber(modid = FlightConduit.MODID)
public class ModEvents {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onFlightConduitEffectEnds(MobEffectEvent.Expired event) {
        if (event.getEntity() instanceof Player player && !player.isCreative() && !player.isSpectator()
                && event.getEffectInstance().getEffect() == ModEffects.FLIGHT.get()) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();

            // Check if we should apply feather falling
            // If featherFallingApplyNearGround, we don't need to check distance always apply no matter what
            boolean shouldApplyFeatherFalling = true;
            if (!Config.featherFallingApplyNearGround) {
                double distanceToGround = getDistanceToGround(player);
                if (distanceToGround <= Config.featherFallingNearGroundBlocks) {
                    shouldApplyFeatherFalling = false;
                }
            }
            
            if (shouldApplyFeatherFalling) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, Config.featherFallingDuration, 1));
            }
        }
    }

    private static double getDistanceToGround(Player player) {
        Level level = player.level();
        BlockPos playerPos = player.blockPosition();
        
        // Search downward from player position to find the first solid block
        // Search twice the configured distance to ensure we can accurately determine if player is within range
        int searchDistance = Config.featherFallingNearGroundBlocks + 10;
        for (int i = 0; i < searchDistance; i++) {
            BlockPos checkPos = playerPos.below(i);
            
            // Check if we've reached the bottom of the world
            if (checkPos.getY() < level.getMinBuildHeight()) {
                return playerPos.getY() - level.getMinBuildHeight();
            }
            
            // Check if this block is solid (ground)
            if (!level.getBlockState(checkPos).isAir() && level.getBlockState(checkPos).isSolidRender(level, checkPos)) {
                return i;
            }
        }
        
        // If no ground found within search range, return a distance larger than our check range
        return searchDistance + 10;
    }
}
