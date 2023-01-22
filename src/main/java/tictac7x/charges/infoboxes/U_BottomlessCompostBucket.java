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

import javax.annotation.Nullable;

public class U_BottomlessCompostBucket extends ChargedItemInfoBox {
    @Nullable private String tooltip_extra;

    public U_BottomlessCompostBucket(final Client client, final ClientThread client_thread, final ConfigManager configs, final ItemManager items, final InfoBoxManager infoboxes, final ChargesImprovedConfig config, final Plugin plugin) {
        super(ItemID.BOTTOMLESS_COMPOST_BUCKET_22997, client, client_thread, configs, items, infoboxes, config, plugin);
        this.config_key = ChargesImprovedConfig.bottomless_compost_bucket;
        this.extra_config_keys = new String[]{"type"};
        this.triggers_items = new TriggerItem[]{
            new TriggerItem(ItemID.BOTTOMLESS_COMPOST_BUCKET, 0),
            new TriggerItem(ItemID.BOTTOMLESS_COMPOST_BUCKET_22997),
        };
        this.triggers_animations = new TriggerAnimation[]{
            new TriggerAnimation(8197, 1, false),
            new TriggerAnimation(832, 2, false, new int[]{ItemID.BUCKET}, "Take")
        };
        this.triggers_chat_messages = new TriggerChatMessage[]{
            new TriggerChatMessage("Your bottomless compost bucket has a single use of (?<type>.+) ?compost remaining.", 1),
            new TriggerChatMessage("Your bottomless compost bucket has (?<charges>.+) uses of (?<type>.+) ?compost remaining."),
            new TriggerChatMessage("Your bottomless compost bucket doesn't currently have any compost in it!(?<type>.*)", 0)
        };
        this.triggers_widgets = new TriggerWidget[]{
            new TriggerWidget("Your bottomless compost bucket is currently holding one use of (?<type>.+?) ?compost.", 1),
            new TriggerWidget("Your bottomless compost bucket is currently holding (?<charges>.+) uses of (?<type>.+?) ?compost."),
            new TriggerWidget("You discard the contents of your bottomless compost bucket.(?<type>.*)", 0),
            new TriggerWidget("You fill your bottomless compost bucket with .* buckets? of (?<type>.+?) ?compost. Your bottomless compost bucket now contains a total of (?<charges>.+) uses.")
        };
    }

    @Override
    public String getTooltip() {
        return super.getTooltip() + tooltip_extra;
    }
    
    private String getCompostType() {
        return configs.getConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.bottomless_compost_bucket_type);
    }

    @Override
    protected void onChargesUpdated() {
        super.onChargesUpdated();

        final @Nullable String type = getCompostType();
        this.tooltip_extra = super.getCharges() == 0 ? " (empty)" : super.getCharges() > 0 && type != null && type.length() > 0 ? " (" + type + ")" : " (unknown)";
    }
}