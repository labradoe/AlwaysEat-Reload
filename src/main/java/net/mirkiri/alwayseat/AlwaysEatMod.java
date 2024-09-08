package net.mirkiri.alwayseat;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.registry.Registries;
import net.mirkiri.alwayseat.mixin.FoodComponentMixin;
import net.mirkiri.alwayseat.config.AlwaysEatConfig;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Objects;

public class AlwaysEatMod implements ModInitializer {
	public static final String MOD_ID = "alwayseat";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	static HashMap<Item, Boolean> defaultValue = new HashMap<>(39);

	@Override
	public void onInitialize() {

		AutoConfig.register(AlwaysEatConfig.class, JanksonConfigSerializer::new);
		updateFoodItems();
	}

	public static void updateFoodItems() {

		for (Item item : Registries.ITEM) {
			FoodComponentMixin food = (FoodComponentMixin) item.getFoodComponent();
			if (food != null) {
				if (!defaultValue.containsKey(item)) {
					defaultValue.put(item, food.getAlwaysEdible());
				}

				String registryName = Objects.requireNonNull(Registries.ITEM.getId(item).toString());

				// In blacklist mode all items except the ones in the list will be set to true
				if (AlwaysEatConfig.getConfig().MODE.equals(AlwaysEatConfig.Mode.BLACKLIST.toString())) {
					if (!AlwaysEatConfig.getConfig().ITEM_LIST.contains(registryName)) {
						food.setAlwaysEdible(true);
					} else {
						food.setAlwaysEdible(defaultValue.get(item));
					}
				} else {
					// In whitelist mode only items in the list will be set to true
					if (AlwaysEatConfig.getConfig().ITEM_LIST.contains(registryName)) {
						food.setAlwaysEdible(true);
					} else {
						food.setAlwaysEdible(defaultValue.get(item));
					}
				}

				// If an item is in the uneatable items list always set it to false
				if (AlwaysEatConfig.getConfig().UNEATABLE_ITEMS.contains(registryName)) {
					food.setAlwaysEdible(false);
				}
			}
		}
	}
}