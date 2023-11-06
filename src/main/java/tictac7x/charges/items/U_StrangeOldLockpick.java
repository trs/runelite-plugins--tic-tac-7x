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
import tictac7x.charges.item.triggers.OnChatMessage;
import tictac7x.charges.item.triggers.TriggerBase;
import tictac7x.charges.item.triggers.TriggerItem;
import tictac7x.charges.store.ItemKey;
import tictac7x.charges.store.Store;

public class U_StrangeOldLockpick extends ChargedItem {
    public U_StrangeOldLockpick(
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
        super(ChargesImprovedConfig.strange_old_lockpick, ItemKey.STRANGE_OLD_LOCKPICK, ItemID.STRANGE_OLD_LOCKPICK, client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, store);

        this.items = new TriggerItem[]{
            new TriggerItem(ItemID.STRANGE_OLD_LOCKPICK),
            new TriggerItem(ItemID.STRANGE_OLD_LOCKPICK_FULL).fixedCharges(50)
        };
        
        this.triggers = new TriggerBase[] {
            new OnChatMessage("Your Strange old lockpick( now)? has (?<charges>.+) charges? remaining.").setDynamically(),
            new OnChatMessage("The Strange old lockpick crumbles to dust as you use it one last time.").notification("Your strange old lockpick crumbles to dust."),
        };
    }
}
