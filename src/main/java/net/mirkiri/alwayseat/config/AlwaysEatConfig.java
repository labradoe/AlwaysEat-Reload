package net.mirkiri.alwayseat.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;
import java.util.List;

@Config(name = "always_eat")
public class AlwaysEatConfig implements ConfigData {
    @Comment("Depending on the mode only these items will be made eatable (WHITELIST) or these items will keep their vanilla behaviour (BLACKLIST)" +
            ", If an item is not affected according to the rules above they will keep their vanilla behaviour")
    public List<String> ITEM_LIST = new ArrayList<>();

    @Comment("These items will be made uneatable while full (Overrides vanilla behaviour)")
    public List<String> UNEATABLE_ITEMS = new ArrayList<>();

    @Comment("Mode as explained in other settings, Allowed Values: BLACKLIST, WHITELIST")
    public String MODE = Mode.BLACKLIST.toString();

    public enum Mode {
        BLACKLIST,
        WHITELIST
    }

    public static AlwaysEatConfig getConfig() {
        return AutoConfig.getConfigHolder(AlwaysEatConfig.class).getConfig();
    }
}
