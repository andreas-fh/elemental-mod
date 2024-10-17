package andreasfh.elemental.client.block;

import andreasfh.elemental.Elemental;
import andreasfh.elemental.blockentity.ModBlockEntities;
import andreasfh.elemental.blockentity.custom.ArcaneAltarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class ArcaneAltarScrollRenderer implements BlockEntityRenderer<ArcaneAltarBlockEntity> {

    public ArcaneAltarScrollRenderer(BlockEntityRendererFactory.Context ctx) {}

    public static void registerRendering() {
        Elemental.LOGGER.info("Registering Block Entity Rendering for " + Elemental.MOD_ID);
        BlockEntityRendererFactories.register(ModBlockEntities.ARCANE_ALTAR_ENTITY, ArcaneAltarScrollRenderer::new);
    }

    @Override
    public void render(ArcaneAltarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack itemStack = entity.getDisplayedItem();
        if (!itemStack.isEmpty()) {
            matrices.push();
            // Calculate the current offset in the y value
            double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
            // Move the item
            matrices.translate(0.5, 1.25 + offset, 0.5);

            // Rotate the item
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 4));

            MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformationMode.GROUND, light,
                    overlay, matrices, vertexConsumers, entity.getWorld(), 0);

            // Mandatory call after GL calls
            matrices.pop();
        }
    }
}
