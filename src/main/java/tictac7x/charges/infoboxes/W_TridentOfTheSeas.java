package tictac7x.charges.infoboxes;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargedItemInfoBox;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.triggers.TriggerAnimation;
import tictac7x.charges.triggers.TriggerChatMessage;
import tictac7x.charges.triggers.TriggerItem;
import tictac7x.charges.triggers.TriggerWidget;

public class W_TridentOfTheSeas extends ChargedItemInfoBox {
    public W_TridentOfTheSeas(final Client client, final ClientThread client_thread, final ConfigManager configs, final ItemManager items, final InfoBoxManager infoboxes, final ChargesImprovedConfig config, final Plugin plugin) {
        super(ItemID.TRIDENT_OF_THE_SEAS, client, client_thread, configs, items, infoboxes, config, plugin);
        this.config_key = ChargesImprovedConfig.trident_of_the_seas;
        this.triggers_items = new TriggerItem[]{
            new TriggerItem(ItemID.UNCHARGED_TRIDENT),
            new TriggerItem(ItemID.TRIDENT_OF_THE_SEAS),
            new TriggerItem(ItemID.TRIDENT_OF_THE_SEAS_FULL)
        };
        this.triggers_chat_messages = new TriggerChatMessage[]{
            new TriggerChatMessage("Your Trident of the seas has one charge.", 1),
            new TriggerChatMessage("Your Trident of the seas( only)?( \\(full\\))? has (?<charges>.+) charges( left)?."),
            new TriggerChatMessage("Your Trident of the seas has run out of charges.", 0),
            new TriggerChatMessage("The Uncharged trident has no charges left. You need death runes, chaos runes, fire runes and coins to charge it.", 0),
            new TriggerChatMessage("Your Uncharged trident has no charges.", 0)
        };
        this.triggers_animations = new TriggerAnimation[]{
            new TriggerAnimation(1167, 1, true)
        };
        this.triggers_widgets = new TriggerWidget[]{
            new TriggerWidget("You add .* charges? to the Trident of the seas( \\(full\\))?. New total: (?<charges>.+)")
        };
    }
}