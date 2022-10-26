package org.betterx.worlds.together.mixin.common;

import org.betterx.worlds.together.world.event.WorldBootstrap;

import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.WorldStem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldStem.class)
public class WorldStem_Mixin {

    //TODO:1.19.3 no general registry access available yet as the layerd access is generated after this
    @ModifyVariable(method = "<init>", argsOnly = true, at = @At(value = "INVOKE", target = "Ljava/lang/Record;<init>()V", shift = At.Shift.AFTER))
    LayeredRegistryAccess<RegistryLayer> wt_bake(LayeredRegistryAccess<RegistryLayer> registries) {
        return WorldBootstrap.enforceInLayeredRegistry(registries);
    }

}
