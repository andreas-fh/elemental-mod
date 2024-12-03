package andreasfh.elemental;

import andreasfh.elemental.block.ModBlocks;
import andreasfh.elemental.blockentity.ModBlockEntities;
import andreasfh.elemental.command.CommandManager;
import andreasfh.elemental.component.ModComponents;
import andreasfh.elemental.item.ModItemGroups;
import andreasfh.elemental.item.ModItems;
import andreasfh.elemental.data.record.Ability;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Elemental implements ModInitializer {
	public static final String MOD_ID = "elemental";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Ability.registerAbilities();
		CommandManager.registerCommands();
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();
		ModComponents.registerDataComponentTypes();

		
	}
}