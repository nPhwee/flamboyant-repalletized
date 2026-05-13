package co.eltrut.flamboyant.mixin.betterend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    targets = "org.betterx.betterend.integration.FlamboyantRefabricatedIntegration",
    remap = false
)
public class MixinFlamboyantRefabricatedIntegration {

    private static final Logger LOGGER =
        LogManager.getLogger("Flamboyant/BetterEndCompat");

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void flamboyant$cancelBrokenIntegrationInit(CallbackInfo ci) {
        LOGGER.info("[Flamboyant] Cancelled BetterEnd's FlamboyantRefabricatedIntegration.init() " +
            "— Flamboyant's own BetterEndCompat handles hydralux petal block registration " +
            "for all 20 colors via DeferredRegister.");
        ci.cancel();
    }
}
