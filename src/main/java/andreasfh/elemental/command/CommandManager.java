package andreasfh.elemental.command;

import andreasfh.elemental.Elemental;
import andreasfh.elemental.client.hud.AbilityHudOverlay;
import andreasfh.elemental.data.record.Ability;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import static net.minecraft.server.command.CommandManager.*;

import java.util.Map;



public class CommandManager {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(literal(Elemental.MOD_ID)
                        .then(literal("debug")
                                
                                // AbilityHUD borders
                                .then(literal("border")
                                        .executes(commandContext -> {
                                            commandContext.getSource().sendFeedback(() -> Text.literal("Need arguments"), false);
                                            return 0;
                                        })
                                        .then(literal("border1")
                                                .executes(commandContext -> {
                                                    commandContext.getSource().sendFeedback(() -> Text.literal("Changed to border1"), false);
                                                    AbilityHudOverlay.setAbilityHudTexture(Identifier.of(Elemental.MOD_ID, "textures/gui/hud/abilityhud1.png"));
                                                    return 1;
                                                }))
                                        .then(literal("border2")
                                                .executes(commandContext -> {
                                                    commandContext.getSource().sendFeedback(() -> Text.literal("Changed to border2"), false);
                                                    AbilityHudOverlay.setAbilityHudTexture(Identifier.of(Elemental.MOD_ID, "textures/gui/hud/abilityhud2.png"));
                                                    return 1;
                                                })))

                                // Select abilities
                                .then(literal("set_abilities")
                                        .then(argument("first_ability", StringArgumentType.string())
                                                .suggests((commandContext, suggestionsBuilder) -> CommandSource.suggestMatching(
                                                        Ability.getAbilities().keySet(), suggestionsBuilder))
                                                .then(argument("second_ability", StringArgumentType.string())
                                                        .suggests((commandContext, suggestionsBuilder) -> CommandSource.suggestMatching(
                                                                Ability.getAbilities().keySet(), suggestionsBuilder))
                                                        .then(argument("third_ability", StringArgumentType.string())
                                                                .suggests((commandContext, suggestionsBuilder) -> CommandSource.suggestMatching(
                                                                        Ability.getAbilities().keySet(), suggestionsBuilder))
                                                                .executes(commandContext -> {

                                                                    String firstAbility = StringArgumentType.getString(
                                                                            commandContext, "first_ability");
                                                                    String secondAbility = StringArgumentType.getString(
                                                                            commandContext, "second_ability");
                                                                    String thirdAbility = StringArgumentType.getString(
                                                                            commandContext, "third_ability");

                                                                    setAbilities(firstAbility, secondAbility, thirdAbility);
                                                                    return 1;
                                                                })
                                                        )))))));
    }

    private static void setAbilities(String abilityKey1, String abilityKey2, String abilityKey3) {
        Map<String, Ability> abilities = Ability.getAbilities();
        Ability firstAbility = abilities.get(abilityKey1);
        Ability secondAbility = abilities.get(abilityKey2);
        Ability thirdAbility = abilities.get(abilityKey3);

        AbilityHudOverlay.selectAbilities(firstAbility, secondAbility, thirdAbility);
    }
}