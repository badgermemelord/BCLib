package org.betterx.bclib.integration.emi;

//import org.betterx.bclib.blocks.LeveledAnvilBlock;
//import org.betterx.bclib.util.RomanNumeral;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import com.mojang.blaze3d.vertex.Tesselator;
//import com.mojang.math.Matrix4f;
//import net.minecraft.ChatFormatting;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.Font;
//import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.util.FormattedCharSequence;
//
//import com.google.common.collect.Lists;
//import dev.emi.emi.EmiPort;
//import dev.emi.emi.EmiUtil;
//import dev.emi.emi.api.recipe.EmiRecipe;
//import dev.emi.emi.api.recipe.EmiRecipeCategory;
//import dev.emi.emi.api.render.EmiRenderable;
//import dev.emi.emi.api.render.EmiTexture;
//
//import java.util.Comparator;
//import java.util.List;
//
//public class EMIAnvilRecipeCategory extends EmiRecipeCategory {
//    private final int anvilLevel;
//    private final List<FormattedCharSequence> titleLines;
//
//
//    public EMIAnvilRecipeCategory(ResourceLocation id, EmiRenderable icon, int anvilLevel) {
//        super(id, icon);
//        this.anvilLevel = anvilLevel;
//        titleLines = LeveledAnvilBlock.getNamesForLevel(anvilLevel);
//    }
//
//    public EMIAnvilRecipeCategory(ResourceLocation id, EmiRenderable icon, EmiRenderable simplified, int anvilLevel) {
//        super(id, icon, simplified);
//        this.anvilLevel = anvilLevel;
//        titleLines = LeveledAnvilBlock.getNamesForLevel(anvilLevel);
//    }
//
//    public EMIAnvilRecipeCategory(
//            ResourceLocation id,
//            EmiRenderable icon, EmiTexture simplified,
//            Comparator<EmiRecipe> sorter,
//            int anvilLevel
//    ) {
//        super(id, icon, simplified, sorter);
//        this.anvilLevel = anvilLevel;
//        titleLines = LeveledAnvilBlock.getNamesForLevel(anvilLevel);
//
//
//    }
//
//    @Override
//    public void renderSimplified(PoseStack stack, int x, int y, float delta) {
//        super.renderSimplified(stack, x, y, delta);
//        final Font font = Minecraft.getInstance().font;
//        final String content = RomanNumeral.toRoman(anvilLevel);
//
//        final MultiBufferSource.BufferSource bufferSource = MultiBufferSource
//                .immediate(Tesselator.getInstance().getBuilder());
//        final int xx = x + 19 - 2 - font.width(content);
//        final int yy = y + 6 + 3;
//        final Matrix4f matrix = stack.last().pose();
//
//        font.drawInBatch(content, xx - 1, yy - 1, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//        font.drawInBatch(content, xx, yy - 1, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//        font.drawInBatch(content, xx + 1, yy - 1, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//        font.drawInBatch(content, xx - 1, yy, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//        font.drawInBatch(content, xx + 1, yy, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//        font.drawInBatch(content, xx - 1, yy + 1, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//        font.drawInBatch(content, xx + 1, yy + 1, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//        font.drawInBatch(content, xx, yy + 1, 0xFF000000, false, matrix, bufferSource, false, 0, 0xF000F0);
//
//        font.drawInBatch(content, xx, yy, 0xFFFFFFFF, true, matrix, bufferSource, false, 0, 0xF000F0);
//        bufferSource.endBatch();
//    }
//
//    public List<ClientTooltipComponent> getTooltip() {
//        List<ClientTooltipComponent> list = Lists.newArrayList();
//        if (titleLines.isEmpty()) {
//            list.add(ClientTooltipComponent.create(EmiPort.ordered(EmiPort.translatable(EmiUtil.translateId(
//                    "emi.category.",
//                    this.getId()
//            )))));
//        } else {
//            for (var line : titleLines)
//                list.add(ClientTooltipComponent.create(line));
//        }
//
//
//        list.add(ClientTooltipComponent.create(EmiPort.ordered(EmiPort.literal(
//                EmiUtil.getModName(this.getId()
//                                       .getNamespace()),
//                new ChatFormatting[]{ChatFormatting.BLUE, ChatFormatting.ITALIC}
//        ))));
//        return list;
//    }
//}
