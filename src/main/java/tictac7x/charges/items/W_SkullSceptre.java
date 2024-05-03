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
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.OnMenuEntryAdded;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.Store;

public class W_SkullSceptre extends ChargedItem {
    public W_SkullSceptre(
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
        super(ChargesImprovedConfig.skull_sceptre, ItemID.SKULL_SCEPTRE, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store, gson);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.SKULL_SCEPTRE),
            new TriggerItem(ItemID.SKULL_SCEPTRE_I)
        };

        this.triggers = new TriggerBase[] {
            // Teleport.
            new OnChatMessage("Your Skull Sceptre has (?<charges>.+) charges? left.").setDynamically(),
            // Check.
            new OnChatMessage("Concentrating deeply, you divine that the sceptre has (?<charges>.+) charges? left.").setDynamically(),
            // Charge.
            new OnChatMessage("You charge the Skull Sceptre with .+ fragments?. It now contains( the maximum number of charges,)? (?<charges>.+)( charges?)?.").setDynamically(),
            // Common menu entries.
            new OnMenuEntryAdded("Divine").replaceOption("Check"),
            new OnMenuEntryAdded("Invoke").replaceOption("Teleport"),
        };
    }
}