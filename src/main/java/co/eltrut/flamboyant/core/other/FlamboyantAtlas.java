package co.eltrut.flamboyant.core.other;

import co.eltrut.flamboyant.core.Flamboyant;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class FlamboyantAtlas {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Map<String, BedInfo> BED_INFO_MAP = new HashMap<>();

    public static synchronized void addBedInfo(String color) {
        BED_INFO_MAP.put(color, new BedInfo(color));
    }

    public static BedInfo get(String key) {
        return BED_INFO_MAP.get(key);
    }

    public static class BedInfo {

        final ResourceLocation loc;
        private Material material;

        public BedInfo(String color) {
            this.loc = new ResourceLocation(Flamboyant.MOD_ID, "entity/bed/" + color);
            LOGGER.info("Registered bed info for: " + color);
        }

        public Material getMaterial() {
            if (this.material == null) {
                this.material = new Material(Sheets.BED_SHEET, this.loc);
            }
            return this.material;
        }
    }
}
