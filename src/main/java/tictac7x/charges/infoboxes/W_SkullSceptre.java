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

public class W_SkullSceptre extends ChargedItemInfoBox {
    public W_SkullSceptre(final Client client, final ClientThread client_thread, final ConfigManager configs, final ItemManager items, final InfoBoxManager infoboxes, final ChargesImprovedConfig config, final Plugin plugin) {
        super(ItemID.SKULL_SCEPTRE, client, client_thread, configs, items, infoboxes, config, plugin);
        this.config_key = ChargesImprovedConfig.skull_sceptre;
        this.triggers_items = new TriggerItem[]{
            new TriggerItem(ItemID.SKULL_SCEPTRE),
            new TriggerItem(ItemID.SKULL_SCEPTRE_I)
        };
        this.triggers_chat_messages = new TriggerChatMessage[]{
            new TriggerChatMessage("Your Skull Sceptre has (?<charges>.+) charges left."),
        };
        this.triggers_widgets = new TriggerWidget[]{
            new TriggerWidget("Concentrating deeply, you divine that the sceptre has (?<charges>.+) charges? left."),
            new TriggerWidget("You charge the Skull Sceptre with .+ fragments?. It now contains (?<charges>.+) charges?.")
        };
    }
}