package net.jojosolos.flightconduit.event;

import net.jojosolos.flightconduit.FlightConduit;
import net.jojosolos.flightconduit.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
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
        Player player = (Player) event.getEntity();
        if (!player.isCreative() && !player.isSpectator()
                && event.getEffectInstance().getEffect() == ModEffects.FLIGHT.get()) {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
            player.onUpdateAbilities();

            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 300, 1));
        }
    }
}
