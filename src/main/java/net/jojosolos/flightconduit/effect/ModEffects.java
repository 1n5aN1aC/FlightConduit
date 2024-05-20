package net.jojosolos.flightconduit.effect;

import net.jojosolos.flightconduit.FlightConduit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FlightConduit.MODID);

    public static final RegistryObject<MobEffect> FLIGHT = MOB_EFFECTS.register("flight",
            () -> new FlightConduitEffect(MobEffectCategory.BENEFICIAL, 1324151243));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
