package tictac7x.charges.items;

import com.google.gson.Gson;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.*;
import tictac7x.charges.store.Store;

public class W_SlayerStaffE extends ChargedItem {
    public W_SlayerStaffE(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Gson gson
    ) {
        super(ChargesImprovedConfig.slayer_staff_e, ItemID.SLAYERS_STAFF_E, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SLAYERS_STAFF_E)
        };

        this.triggers = new TriggerBase[] {
            // Enchant.
            new OnChatMessage("The spell enchants your staff. The tatty parchment crumbles to dust.").fixedCharges(2500),

            // Check.
            new OnChatMessage("Your staff has (?<charges>.+) charges?.").setDynamically(),

            // Attack.
            new OnAnimationChanged(1576).isEquipped().decreaseCharges(1),
        };
    }
}