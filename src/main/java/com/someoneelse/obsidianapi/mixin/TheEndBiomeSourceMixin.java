package com.someoneelse.obsidianapi.mixin;

import com.someoneelse.obsidianapi.BiomeRegistryTest;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.stream.Stream;

@Mixin(TheEndBiomeSource.class)
public class TheEndBiomeSourceMixin {

    @Shadow
    @Final
    private Holder<Biome> end;

    @Shadow
    @Final
    private Holder<Biome> islands;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public Holder<Biome> getNoiseBiome(int xPos, int yPos, int zPos, Climate.Sampler sampler) {
        int x = QuartPos.toBlock(xPos);
        int y = QuartPos.toBlock(yPos);
        int z = QuartPos.toBlock(zPos);

        int xSection = SectionPos.blockToSectionCoord(x);
        int zSection = SectionPos.blockToSectionCoord(z);

        double erosionDensity = sampler.erosion().compute(new DensityFunction.SinglePointContext(xSection, y, zSection));
        if(erosionDensity > -0.21875D) {
            return this.end;
        } else {
            return this.islands;
        }
    }
}
