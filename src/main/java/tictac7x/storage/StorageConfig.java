package tictac7x.storage;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(StorageConfig.group)
public interface StorageConfig extends Config {
	String group = "tictac7x-storage";

	enum InventoryEmpty { TOP, FIRST, LAST, BOTTOM, HIDDEN }
	enum InventoryDensity { COMPACT, REGULAR }

	@ConfigSection(
		name = "Inventory",
		description = "Inventory overlay",
		position = 1
	) String inventory = "inventory";

		@ConfigItem(
			keyName = "inventory_show",
			name = "Show inventory overlay",
			description = "Show overlay of inventory items.",
			section = inventory,
			position = 2
		) default boolean showInventory() { return true; }

		@ConfigItem(
			keyName = "inventory_empty",
			name = "Empty slots location",
			description = "Where to show how many empty slots inventory has.",
			section = inventory,
			position = 3
		) default InventoryEmpty getInventoryEmptyStyle() { return InventoryEmpty.BOTTOM; }

		@ConfigItem(
			keyName = "inventory_hide",
			name = "Hide when inventory tab is open",
			description = "Hide inventory overlay, when inventory tab is open.",
			section = inventory,
			position = 4
		) default boolean hideInventoryOverlayWhenInventoryIsOpen() { return true; }

		@ConfigItem(
			keyName = "inventory_whitelist_enabled",
			name = "Use whitelist for inventory",
			description = "Whitelist for inventory is disabled by default to show all items in the invetory.",
			section = inventory,
			position = 5
		) default boolean isInventoryWhitelistEnabled() { return false; }

		@ConfigItem(
			keyName = "inventory_empty_zero",
			name = "Show 0 spaces left",
			description = "Show that inventory is full and 0 spaces are left.",
			section = inventory,
			position = 6
		) default boolean showInventoryZeroSpaceLeft() { return true; }

		@ConfigItem(
			keyName = "inventory_whitelist",
			name = "Inventory whitelist",
			description = "Names of items to show in the inventory overlay.",
			section = inventory,
			position = 7
		) default String getInventoryWhitelist() { return ""; }

		@ConfigItem(
			keyName = "inventory_blacklist",
			name = "Inventory blacklist",
			description = "Names of items to hide from the inventory overlay.",
			section = inventory,
			position = 8
		) default String getInventoryBlacklist() { return ""; }

	@ConfigSection(
		name = "Bank",
		description = "Bank overlay",
		position = 2
	) String bank = "bank";

		@ConfigItem(
			keyName = "bank",
			name = "bank",
			description = "bank",
			section = bank,
			position = 1,
			hidden = true
		) default String getBank() { return ""; }

		@ConfigItem(
			keyName = "bank_show",
			name = "Show bank overlay",
			description = "Show overlay of bank items.",
			section = bank,
			position = 2
		) default boolean showBank() { return true; }

		@ConfigItem(
			keyName = "bank_hide",
			name = "Hide when bank is open",
			description = "Hide bank overlay, when bank is open.",
			section = bank,
			position = 3
		) default boolean hideBankOverlayWhenBankIsOpen() { return false; }

		@ConfigItem(
			keyName = "bank_whitelist",
			name = "Bank whitelist",
			description = "Names of items to show in the bank overlay.",
			section = bank,
			position = 4
		) default String getBankWhitelist() { return "Coins,\r\n ore"; }

		@ConfigItem(
			keyName = "bank_blacklist",
			name = "Bank blacklist",
			description = "Names of items to hide from the bank overlay.",
			section = bank,
			position = 5
		) default String getBankBlacklist() { return ""; }

	@ConfigSection(
		name = "General",
		description = "General settings",
		position = 3
	) String general = "general";

		String storages = "storages";
		@ConfigItem(
			keyName = storages,
			name = storages,
			description = storages,
			section = general,
			position = 1,
			hidden = false
		) default String getStorages() { return "{}"; }

		@ConfigItem(
			keyName = "general_density",
			name = "Grid density",
			description = "Change the density of the items in the overlays.",
			section = general,
			position = 2
		) default InventoryDensity getOverlayDensity() { return InventoryDensity.REGULAR; }
}
