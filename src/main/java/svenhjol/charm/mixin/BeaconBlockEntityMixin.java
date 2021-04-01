package svenhjol.charm.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import svenhjol.charm.event.ApplyBeaconEffectsCallback;

@Mixin(BeaconBlockEntity.class)
public abstract class BeaconBlockEntityMixin extends BlockEntity {
    public BeaconBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Inject(
        method = "applyPlayerEffects",
        at = @At("HEAD")
    )
    private static void hookAddEffects(World world, BlockPos pos, int level, StatusEffect primary, StatusEffect secondary, CallbackInfo ci) {
        if (world != null)
            ApplyBeaconEffectsCallback.EVENT.invoker().interact(world, pos, level, primary, secondary);
    }
}
