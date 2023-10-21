package tictac7x.charges.items;

import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.triggers.TriggerChatMessage;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.item.triggers.TriggerMenuEntryAdded;
import tictac7x.charges.store.Charges;
import tictac7x.charges.store.ItemKey;
import tictac7x.charges.store.Store;

public class U_TackleBox extends ChargedItem {
    public U_TackleBox(
        final Client client,
        final ClientThread client_thread,
        final ConfigManager configs,
        final ItemManager items,
        final InfoBoxManager infoboxes,
        final ChatMessageManager chat_messages,
        final Notifier notifier,
        final ChargesImprovedConfig config,
        final Store store,
        final Plugin plugin
    ) {
        super(ItemKey.TACKLE_BOX, ItemID.TACKLE_BOX, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store);
        this.triggersItems = new TriggerItem[]{
            new TriggerItem(ItemID.TACKLE_BOX).fixedCharges(Charges.UNLIMITED),
        };
        this.triggersMenusEntriesAdded = new TriggerMenuEntryAdded[]{
            new TriggerMenuEntryAdded("Destroy").hide(),
        };
    }
}
