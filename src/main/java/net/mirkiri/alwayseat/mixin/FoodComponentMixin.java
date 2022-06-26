package net.mirkiri.alwayseat.mixin;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.mirkiri.alwayseat.config.AlwaysEatConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(FoodComponent.class)
public interface FoodComponentMixin {

    @Accessor("alwaysEdible")@Mutable
    public boolean getAlwaysEdible();

    @Accessor("alwaysEdible")@Mutable
    public void setAlwaysEdible(boolean alwaysEdible);

}
