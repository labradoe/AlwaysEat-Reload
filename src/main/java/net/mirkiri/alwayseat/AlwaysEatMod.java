package net.mirkiri.alwayseat;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.mirkiri.alwayseat.config.AlwaysEatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlwaysEatMod implements ModInitializer {
	public static final String MODID = "always_eat";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		AutoConfig.register(AlwaysEatConfig.class, JanksonConfigSerializer::new);
	}

}
