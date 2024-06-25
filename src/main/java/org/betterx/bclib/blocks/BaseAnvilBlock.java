package org.betterx.bclib.blocks;

import org.betterx.bclib.interfaces.tools.AddMineablePickaxe;
import org.betterx.bclib.items.BaseAnvilItem;
import org.betterx.bclib.util.BCLDataComponents;
import org.betterx.bclib.util.BlocksHelper;
import org.betterx.bclib.util.LootUtil;
import org.betterx.wover.block.api.CustomBlockItemProvider;
import org.betterx.wover.block.api.model.BlockModelProvider;
import org.betterx.wover.block.api.model.WoverBlockModelGenerators;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;

public abstract class BaseAnvilBlock extends AnvilBlock implements AddMineablePickaxe, CustomBlockItemProvider, BlockModelProvider {
    public static final IntegerProperty DESTRUCTION = BlockProperties.DESTRUCTION;
    public IntegerProperty durability;

    public BaseAnvilBlock(MapColor color) {
        this(Properties.ofFullCopy(Blocks.ANVIL).mapColor(color));
    }

    public BaseAnvilBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        if (getMaxDurability() != 3) {
            durability = IntegerProperty.create("durability", 0, getMaxDurability());
        } else {
            durability = BlockProperties.DEFAULT_ANVIL_DURABILITY;
        }
        builder.add(DESTRUCTION, durability);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void provideBlockModels(WoverBlockModelGenerators generator) {
        generator.vanillaGenerator.createAnvil(this);
    }

    @Override
    public BlockItem getCustomBlockItem(ResourceLocation blockID, Item.Properties settings) {
        return new BaseAnvilItem(this, settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        int destruction = state.getValue(DESTRUCTION);
        int durability = state.getValue(getDurabilityProp());
        int value = destruction * getMaxDurability() + durability;
        ItemStack tool = builder.getParameter(LootContextParams.TOOL);
        if (LootUtil.isCorrectTool(this, state, tool)) {
            ItemStack itemStack = new ItemStack(this);

            CustomData.update(BCLDataComponents.ANVIL_ENTITY_DATA, itemStack, (compoundTag) -> {
                compoundTag.putInt(BaseAnvilItem.DESTRUCTION, value);
            });

            return Lists.newArrayList(itemStack);
        }
        return Collections.emptyList();
    }

    public IntegerProperty getDurabilityProp() {
        return durability;
    }

    public int getMaxDurability() {
        return 5;
    }

    public BlockState damageAnvilUse(BlockState state, RandomSource random) {
        IntegerProperty durability = getDurabilityProp();
        int value = state.getValue(durability);
        if (value < getMaxDurability()) {
            return state.setValue(durability, value + 1);
        }
        value = state.getValue(DESTRUCTION);
        return value < 2 ? state.setValue(DESTRUCTION, value + 1).setValue(durability, 0) : null;
    }

    public BlockState damageAnvilFall(BlockState state) {
        int destruction = state.getValue(DESTRUCTION);
        return destruction < 2 ? state.setValue(DESTRUCTION, destruction + 1) : null;
    }

    @ApiStatus.Internal
    public static void destroyWhenNull(Level level, BlockPos blockPos, BlockState damaged) {
        if (damaged == null) {
            level.removeBlock(blockPos, false);
            level.levelEvent(LevelEvent.SOUND_ANVIL_BROKEN, blockPos, 0);
        } else {
            level.setBlock(blockPos, damaged, BlocksHelper.FLAG_SEND_CLIENT_CHANGES);
            level.levelEvent(LevelEvent.SOUND_ANVIL_USED, blockPos, 0);
        }
    }
}
