package tictac7x.gotr.overlays;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;
import tictac7x.gotr.store.Inventory;
import tictac7x.gotr.TicTac7xGotrImprovedConfig;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class GreatGuardianOverlay extends Overlay {
    private final Client client;
    private final ItemManager itemManager;
    private final ModelOutlineRenderer modelOutlineRenderer;
    private final TicTac7xGotrImprovedConfig config;
    private final Inventory inventory;

    private final BufferedImage imageElementalGuardianStones;
    private final BufferedImage imageCatalyticGuardianStones;

    private Optional<NPC> greatGuardian = Optional.empty();

    public GreatGuardianOverlay(final Client client, final ItemManager itemManager, final ModelOutlineRenderer modelOutlineRenderer, final TicTac7xGotrImprovedConfig config, final Inventory inventory) {
        this.client = client;
        this.itemManager = itemManager;
        this.modelOutlineRenderer = modelOutlineRenderer;
        this.config = config;
        this.inventory = inventory;

        this.imageCatalyticGuardianStones = itemManager.getImage(ItemID.CATALYTIC_GUARDIAN_STONE);
        this.imageElementalGuardianStones = itemManager.getImage(ItemID.ELEMENTAL_GUARDIAN_STONE);

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    public void onNpcSpawned(final NPC npc) {
        if (npc.getId() == NpcID.THE_GREAT_GUARDIAN) {
            greatGuardian = Optional.of(npc);
        }
    }

    public void onNpcDespawned(final NPC npc) {
        if (npc.getId() == NpcID.THE_GREAT_GUARDIAN) {
            greatGuardian = Optional.empty();
        }
    }

    public void onGameStateChanged(final GameState game_state) {
        if (game_state == GameState.LOADING) {
            greatGuardian = Optional.empty();
        }
    }

    @Override
    public Dimension render(final Graphics2D graphics) {
        // Great guardian highlighting disabled.
        if (!config.highlightGreatGuardian()) return null;

        // Great guardian NPC object not available.
        if (!greatGuardian.isPresent()) return null;

        // No essence in inventory.
        if (!inventory.hasGuardianStones()) return null;

        // Outline.
        try {
            modelOutlineRenderer.drawOutline(greatGuardian.get(), 2, inventory.hasElementalGuardianStones() ? config.getElementalColor() : config.getCatalyticColor(), 2);
        } catch (final Exception ignored) {}

        // Image.
        try {
            OverlayUtil.renderImageLocation(client, graphics, greatGuardian.get().getLocalLocation(), inventory.hasElementalGuardianStones() ? imageElementalGuardianStones : imageCatalyticGuardianStones, 550);
        } catch (final Exception ignored) {}

        return null;
    }
}