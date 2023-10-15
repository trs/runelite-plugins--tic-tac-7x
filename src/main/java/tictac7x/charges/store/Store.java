package tictac7x.charges.store;

import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.game.ItemManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Store {
    private final ItemManager items;
    private int gametick = 0;
    private int gametick_before = 0;

    public Optional<ItemContainer> inventory = Optional.empty();
    public Optional<ItemContainer> equipment = Optional.empty();
    public Optional<Item[]> inventory_items = Optional.empty();
    public Optional<Item[]> bank_items = Optional.empty();

    public final List<MenuEntry> menuEntries = new ArrayList<>();

    public Store(final ItemManager items) {
        this.items = items;
    }

    public void onItemContainerChanged(final ItemContainerChanged event) {
        if (event.getContainerId() == InventoryID.INVENTORY.getId()) {
            inventory = Optional.of(event.getItemContainer());
        }

        if (event.getContainerId() == InventoryID.EQUIPMENT.getId()) {
            equipment = Optional.of(event.getItemContainer());
        }
    }

    public void onInventoryItemsChanged(final ItemContainerChanged event) {
        if (event.getContainerId() == InventoryID.INVENTORY.getId()) {
            inventory_items = Optional.of(event.getItemContainer().getItems());
        }
    }

    public void onBankItemsChanged(final ItemContainerChanged event) {
        if (event.getContainerId() == InventoryID.BANK.getId()) {
            bank_items = Optional.of(event.getItemContainer().getItems());
        }
    }

    public void onMenuOptionClicked(final MenuOptionClicked event) {
        final String menu_target = event.getMenuTarget().replaceAll("</?col.*?>", "");
        final String menu_option = event.getMenuOption();

        if (
            // Not menu.
            menu_target.isEmpty() ||
            // Menu option not found.
            menu_option == null || menu_option.isEmpty()
        ) {
            return;
        }

        // Gametick changed, clear previous menu entries since they are no longer valid.
        if (gametick >= gametick_before + 2) {
            gametick = 0; gametick_before = 0;
            menuEntries.clear();
        }

        // Save menu option and target for other triggers to use.
        menuEntries.add(new MenuEntry(menu_target, menu_option));
    }

    public void onGameTick(final GameTick ignored) {
        gametick++;
    }

    public boolean notInMenuTargets(final String target) {
        return menuEntries.stream().noneMatch(entry -> entry.target.contains(target));
    }

    public boolean notInMenuOptions(final String option) {
        return menuEntries.stream().noneMatch(entry -> entry.option.equals(option));
    }

    public boolean notInMenuEntries(final MenuEntry menuEntry) {
        return !inMenuEntries(menuEntry);
    }

    public boolean inMenuEntries(final MenuEntry menuEntry) {
        return menuEntries.stream().anyMatch(entry -> entry.target.equals(menuEntry.target) && entry.option.equals(menuEntry.option));
    }

    public int getInventoryItemsDifference(final ItemContainerChanged event) {
        if (
            event.getItemContainer().getId() != InventoryID.INVENTORY.getId() ||
            !inventory_items.isPresent()
        ) return 0;

        return itemsDifference(inventory_items.get(), event.getItemContainer().getItems());
    }

    public int getBankItemsDifference(final ItemContainerChanged event) {
        if (event.getContainerId() != InventoryID.BANK.getId() || !bank_items.isPresent()) {
            return 0;
        }

        int difference = 0;
        final Map<Integer, Integer> differences = new HashMap<>();

        // New bank items.
        for (final Item new_item : event.getItemContainer().getItems()) {
            differences.put(new_item.getId(), new_item.getQuantity());
            items.getItemComposition(0).getPlaceholderId();
        }

        // Previous bank items.
        for (final Item old_item : bank_items.get()) {
            differences.put(old_item.getId(), differences.getOrDefault(old_item.getId(), 0) - old_item.getQuantity());
        }

        // Find differences between all items.
        for (final Map.Entry<Integer, Integer> item_difference : differences.entrySet()) {
            if (item_difference.getValue() > 0) {
                difference += item_difference.getValue();
            }
        }

        return difference;
    }

    private int itemsDifference(final Item[] items_before, final Item[] items_after) {
        final int items_before_count = (int) Arrays.stream(items_before).filter(item -> item.getId() != -1).count();
        final int items_after_count = (int) Arrays.stream(items_after).filter(item -> item.getId() != -1).count();

        return Math.abs(items_before_count - items_after_count);
    }

    public int getInventoryItemCount(final int itemId) {
        return inventory.map(itemContainer -> itemContainer.count(itemId)).orElse(0);
    }

    public int getEquipmentItemCount(final int itemId) {
        return equipment.map(itemContainer -> itemContainer.count(itemId)).orElse(0);
    }

    public boolean inventoryContainsItem(final int itemId) {
        return inventory.map(itemContainer -> itemContainer.contains(itemId)).orElse(false);
    }

    public boolean equipmentContainsItem(final int itemId) {
        return equipment.map(itemContainer -> itemContainer.contains(itemId)).orElse(false);
    }
}
