package net.mirkiri.alwayseat;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static final String CATEGORY_EATABLE = "eatable";

    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.ConfigValue<List<?>> ITEM_LIST;
    public static ForgeConfigSpec.ConfigValue<List<?>> UNEATABLE_ITEMS;
    public static ForgeConfigSpec.EnumValue<Mode> MODE;

    enum Mode {
        BLACKLIST,
        WHITELIST
    }

    static {

        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

        BUILDER.comment("Eatable settings").push(CATEGORY_EATABLE);

        ITEM_LIST = BUILDER
                .worldRestart()
                .comment(
                        "List of items",
                        "Depending on the mode only these items will be made eatable (WHITELIST) or these items will keep their vanilla behaviour (BLACKLIST)",
                        "If an item is not affected according to the rules above they will keep their vanilla behaviour"
                )
                .defineList("item_list", new ArrayList<>(), Config::isValidResourceLocation);
        UNEATABLE_ITEMS = BUILDER
                .worldRestart()
                .comment(
                        "List of items",
                        "These items will be made uneatable while full (Overrides vanilla behaviour)"
                )
                .defineList("uneatable_list", new ArrayList<>(), Config::isValidResourceLocation);
        MODE = BUILDER
                .worldRestart()
                .comment("Mode as explained in other settings")
                .defineEnum("mode", Mode.BLACKLIST);

        BUILDER.pop();

        CONFIG = BUILDER.build();
    }

    static boolean isValidResourceLocation(Object o) {
        if(o instanceof ResourceLocation) return true;

        if(o instanceof String) {
            String resourceName = (String) o;
            String[] astring = ResourceLocation.decompose(resourceName, ':');
            return ResourceLocation.isValidNamespace(org.apache.commons.lang3.StringUtils.isEmpty(astring[0]) ? "minecraft" : astring[0]) && ResourceLocation.isValidPath(astring[1]);
        }

        return false;
    }


}