package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.client.Notifier;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItem;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnResetDaily;
import tictac7x.charges.item.triggers.TriggerBase;

public class ListenerOnResetDaily extends ListenerBase {
    public ListenerOnResetDaily(final Client client, final ChargedItemBase chargedItem, final Notifier notifier, final ChargesImprovedConfig config) {
        super(client, chargedItem, notifier, config);
    }

    public void trigger() {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase)) continue;
            final OnResetDaily trigger = (OnResetDaily) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase) {
        if (!(triggerBase instanceof OnResetDaily)) return false;
        final OnResetDaily trigger = (OnResetDaily) triggerBase;

        if (trigger.resetSpecificItem.isPresent() && !chargedItem.store.itemInPossession(trigger.resetSpecificItem.get())) {
            return false;
        }

        return super.isValidTrigger(trigger);
    }
}
