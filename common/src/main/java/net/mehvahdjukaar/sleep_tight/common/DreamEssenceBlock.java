package net.mehvahdjukaar.sleep_tight.common;

import net.mehvahdjukaar.sleep_tight.SleepTight;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DreamEssenceBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D);

    public DreamEssenceBlock(Properties properties) {
        super(properties);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext ec && ec.getEntity() instanceof Phantom) return Shapes.empty();
        return super.getCollisionShape(state, level, pos, context);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Phantom) level.destroyBlock(pos, false, entity);
        super.entityInside(state, level, pos, entity);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        level.addFreshEntity(new DreamerEssenceTargetEntity(level, pos));
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        level.addParticle(SleepTight.DREAM_PARTICLE.get(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0, 0, 0);
    }
}