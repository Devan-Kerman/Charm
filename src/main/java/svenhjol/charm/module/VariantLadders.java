package svenhjol.charm.module;

import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import svenhjol.charm.Charm;
import svenhjol.charm.base.CharmModule;
import svenhjol.charm.base.enums.IVariantMaterial;
import svenhjol.charm.base.enums.VanillaVariantMaterial;
import svenhjol.charm.base.iface.Module;
import svenhjol.charm.block.VariantLadderBlock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Module(mod = Charm.MOD_ID, description = "Ladders available in all types of vanilla wood.")
public class VariantLadders extends CharmModule {
    public static final Map<IVariantMaterial, VariantLadderBlock> LADDER_BLOCKS = new HashMap<>();

    public static boolean isEnabled = false;

    @Override
    public void register() {
        VanillaVariantMaterial.getTypes().forEach(type -> {
            LADDER_BLOCKS.put(type, new VariantLadderBlock(this, type));
        });

        isEnabled = this.enabled;
    }

    @Override
    public void clientInit() {
        LADDER_BLOCKS.values().forEach(ladder -> {
            RenderTypeLookup.setRenderLayer(ladder, RenderType.getCutout());
        });
    }

    public static boolean canEnterTrapdoor(World world, BlockPos pos, BlockState state) {
        if (isEnabled && state.get(TrapDoorBlock.OPEN)) {
            BlockState down = world.getBlockState(pos.down());
            return LADDER_BLOCKS.values().stream().anyMatch(b -> b == down.getBlock()) && down.get(LadderBlock.FACING) == state.get(TrapDoorBlock.HORIZONTAL_FACING);
        }

        return false;
    }

    @Override
    public List<ResourceLocation> getRecipesToRemove() {
        return Arrays.asList(new ResourceLocation(Charm.MOD_ID, "woodcutters/vanilla_ladder_from_planks"));
    }
}
