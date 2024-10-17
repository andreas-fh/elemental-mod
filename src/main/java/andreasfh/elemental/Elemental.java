package andreasfh.elemental;

import andreasfh.elemental.block.ModBlocks;
import andreasfh.elemental.blockentity.ModBlockEntities;
import andreasfh.elemental.item.ModItemGroups;
import andreasfh.elemental.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Elemental implements ModInitializer {
	public static final String MOD_ID = "elemental";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();


	}
}