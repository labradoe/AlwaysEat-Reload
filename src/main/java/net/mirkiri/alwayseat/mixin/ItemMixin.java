package net.mirkiri.alwayseat.mixin;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.mirkiri.alwayseat.config.AlwaysEatConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(Item.class)
public class ItemMixin {

    private static final HashMap<Item, Boolean> defaultValue = new HashMap<>(39);
    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/FoodComponent;isAlwaysEdible()Z"))
    private boolean isAlwaysEdible(FoodComponent food) {
        Item item = (Item) (Object) this;
        boolean isAlwaysEdible = false;
        if (item.isFood()) {
            if (!defaultValue.containsKey(item)) {
                defaultValue.put(item, food.isAlwaysEdible());
            }
            String registryName = Registry.ITEM.getId(item).toString();

            // In blacklist mode all items except the ones in the list will be set to true
            if (AlwaysEatConfig.getConfig().MODE.equals(AlwaysEatConfig.Mode.BLACKLIST.toString())) {
                if (!AlwaysEatConfig.getConfig().ITEM_LIST.contains(registryName)) {
                    isAlwaysEdible = true;
                } else {
                    isAlwaysEdible = defaultValue.get(item);
                }
            } else {
                // In whitelist mode only items in the list will be set to true
                if (AlwaysEatConfig.getConfig().ITEM_LIST.contains(registryName)) {
                    isAlwaysEdible = true;
                } else {
                    isAlwaysEdible = defaultValue.get(item);
                }
            }

            // If an item is in the uneatable items list always set it to false
            if (AlwaysEatConfig.getConfig().UNEATABLE_ITEMS.contains(registryName)) {
                isAlwaysEdible = false;
            }
        }
        return isAlwaysEdible;
    }
}
