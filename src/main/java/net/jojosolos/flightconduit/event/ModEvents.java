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

            // Get distance to ground for both checking if we should apply and calculating duration
            double distanceToGround = getDistanceToGround(player);
            
            // Check if we should apply feather falling
            boolean shouldApplyFeatherFalling = true;
            if (!Config.featherFallingApplyNearGround) {
                if (distanceToGround <= Config.featherFallingNearGroundBlocks) {
                    shouldApplyFeatherFalling = false;
                }
            }
            
            if (shouldApplyFeatherFalling) {
                int duration;
                
                if (Config.featherFallingDynamicDuration) {
                    // Calculate exact duration needed based on height above ground
                    // With slow falling, players fall at approximately 0.125 blocks per tick
                    // So duration = distance / 0.125 = distance * 8
                    // Add a small buffer (20 ticks = 1 second) to ensure safe landing
                    duration = (int) Math.ceil(distanceToGround * 8.0) + 20;
                    
                    // Clamp duration to reasonable bounds (minimum 20 ticks, maximum 1200 ticks)
                    duration = Math.max(20, Math.min(1200, duration));
                } else {
                    // Use configured static duration
                    duration = Config.featherFallingDuration;
                }
                
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, duration, 1));
            }
        }
    }

    private static double getDistanceToGround(Player player) {
        Level level = player.level();
        BlockPos playerPos = player.blockPosition();
        
        // Determine search distance based on whether we need accurate measurements for dynamic duration
        // If dynamic duration is enabled, search up to 150 blocks (which would give ~1200 tick duration)
        // Otherwise, only search enough to check the near-ground threshold
        int searchDistance = Config.featherFallingDynamicDuration 
            ? 150 
            : (Config.featherFallingNearGroundBlocks + 10);
        
        // Search downward from player position to find the first solid block
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
