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
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.store.ItemKey;
import tictac7x.charges.store.Store;
import tictac7x.charges.item.triggers.TriggerItem;

public class J_BraceletOfExpeditious extends ChargedItem {
    public J_BraceletOfExpeditious(
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
        super(ChargesImprovedConfig.expeditious_bracelet, ItemKey.BRACELET_OF_EXPEDITIOUS, ItemID.EXPEDITIOUS_BRACELET, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.EXPEDITIOUS_BRACELET).needsToBeEquipped(),
        };

        this.triggers = new TriggerBase[] {
            new OnChatMessage("The bracelet shatters. Your next expeditious bracelet will start afresh from 30 charges.").fixedCharges(30),
            new OnChatMessage("Your expeditious bracelet helps you progress your slayer faster. It then crumbles to dust.").fixedCharges(30).notification("Your expeditious bracelet crumbles to dust."),
            new OnChatMessage("Your expeditious bracelet has (?<charges>.+) charges? left.").setDynamically(),
            new OnChatMessage("Your expeditious bracelet helps you progress your slayer( task)? faster. It has (?<charges>.+) charges? left.").setDynamically(),
        };
    }
}
