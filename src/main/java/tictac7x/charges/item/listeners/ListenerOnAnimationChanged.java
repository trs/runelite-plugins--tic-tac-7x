package tictac7x.charges.item.listeners;

import net.runelite.api.Client;
import net.runelite.api.events.AnimationChanged;
import net.runelite.client.Notifier;
import tictac7x.charges.ChargesImprovedConfig;
import tictac7x.charges.item.ChargedItemBase;
import tictac7x.charges.item.triggers.OnAnimationChanged;
import tictac7x.charges.item.triggers.TriggerBase;

public class ListenerOnAnimationChanged extends ListenerBase {
    public ListenerOnAnimationChanged(final Client client, final ChargedItemBase chargedItem, final Notifier notifier, final ChargesImprovedConfig config) {
        super(client, chargedItem, notifier, config);
    }

    public void trigger(final AnimationChanged event) {
        for (final TriggerBase triggerBase : chargedItem.triggers) {
            if (!isValidTrigger(triggerBase, event)) continue;

            final OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;
            boolean triggerUsed = false;

            if (super.trigger(trigger)) {
                triggerUsed = true;
            }

            if (triggerUsed) return;
        }
    }

    public boolean isValidTrigger(final TriggerBase triggerBase, final AnimationChanged event) {
        if (!(triggerBase instanceof OnAnimationChanged)) return false;
        final OnAnimationChanged trigger = (OnAnimationChanged) triggerBase;

        // Player check.
        if (event.getActor() != client.getLocalPlayer()) {
            return false;
        }

        // Animation id check.
        animationIdCheck: if (trigger.animationId != null) {
            for (final int animationId : trigger.animationId) {
                if (event.getActor().getAnimation() == animationId) {
                    break animationIdCheck;
                }
            }

            return false;
        }

        return super.isValidTrigger(trigger);
    }
}