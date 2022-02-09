package tictac7x.storage;

import javax.inject.Inject;

import com.google.gson.JsonObject;
import net.runelite.api.Client;
import lombok.extern.slf4j.Slf4j;
import com.google.inject.Provides;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Storage",
	description = "Show overlays of various storages and searchable panel",
	tags = { "storage", "bank", "inventory" }
)
public class StoragePlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private ClientThread client_thread;

	@Inject
	private StorageConfig config;

	@Inject
	private ConfigManager configs;

	@Inject
	private OverlayManager overlays;

	@Inject
	private ItemManager items;

	@Provides
	StorageConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(StorageConfig.class);
	}

	private Storage bank;
	private Storage inventory;
	private StorageManager storage_manager;
	private StorageOverlay overlay_bank;
	private StorageOverlay overlay_inventory;

	@Override
	protected void startUp() {
		if (bank == null) {
			bank = new Storage(configs, items, client_thread, InventoryID.BANK, "bank", true, true);
			inventory = new Storage(configs, items, client_thread, InventoryID.INVENTORY, "inventory", false, true);
			storage_manager = new StorageManager(client, inventory, bank);

			overlay_bank = new StorageOverlay(bank);
			overlay_inventory = new StorageOverlay(inventory);
		}

		overlays.add(overlay_bank);
		overlays.add(overlay_inventory);
	}

	@Override
	protected void shutDown() {
		overlays.remove(overlay_bank);
		overlays.remove(overlay_inventory);
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		storage_manager.onItemContainerChanged(event);
		bank.onItemContainerChanged(event);
		inventory.onItemContainerChanged(event);
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		bank.onConfigChanged(event);
		inventory.onConfigChanged(event);
	}
}
