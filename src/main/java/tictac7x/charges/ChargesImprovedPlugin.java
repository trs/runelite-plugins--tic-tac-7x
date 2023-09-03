package tictac7x.charges;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GraphicChanged;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import tictac7x.charges.infoboxes.*;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;



@Slf4j
@PluginDescriptor(
	name = "Item Charges Improved",
	description = "Show charges of various items",
	tags = {
		"charges",
		"barrows",
		"bracelet",
		"ring",
		"xeric",
		"talisman",
		"book",
		"chronicle",
		"shield",
		"ash",
		"bone",
		"bottomless",
		"bucket",
		"fish",
		"gricoller",
		"can",
		"soul",
		"arclight",
		"bryophyta",
		"staff",
		"iban",
		"pharaoh",
		"sceptre",
		"skull",
		"sanguinesti",
		"trident",
		"dragonfire",
		"circlet",
		"camulet"
	}
)
public class ChargesImprovedPlugin extends Plugin {
	private final String plugin_version = "v0.3";
	private final String plugin_message = "" +
		"<colHIGHLIGHT>Item Charges Improved " + plugin_version + ":<br>" +
		"<colHIGHLIGHT>* General fixes and improvements<br>" +
		"<colHIGHLIGHT>* Log basket added";

	private final int VARBIT_MINUTES = 8354;

	public static final int CHARGES_UNKNOWN = -1;
	public static final int CHARGES_UNLIMITED = -2;

	@Inject
	private Client client;

	@Inject
	private ClientThread client_thread;

	@Inject
	private ItemManager items;

	@Inject
	private ConfigManager configs;

	@Inject
	private InfoBoxManager infoboxes;

	@Inject
	private OverlayManager overlays;

	@Inject
	private ChargesImprovedConfig config;

	@Inject
	private ChatMessageManager chat_messages;

	@Inject
	private Notifier notifier;

	@Provides
	ChargesImprovedConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(ChargesImprovedConfig.class);
	}

	private ChargedItemsOverlay overlay_charged_items;

	private ChargedItemInfoBox[] infoboxes_charged_items;

	private final ZoneId timezone = ZoneId.of("Europe/London");
	private String date = LocalDateTime.now(timezone).format(DateTimeFormatter.ISO_LOCAL_DATE);

	@Override
	protected void startUp() {
		infoboxes_charged_items = new ChargedItemInfoBox[]{
			// Weapons
			new W_Arclight(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new W_TridentOfTheSeas(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new W_SkullSceptre(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new W_IbansStaff(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new W_PharaohsSceptre(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new W_BryophytasStaff(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new W_SanguinestiStaff(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new W_CrystalBow(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			// Shields
			new S_KharedstMemoirs(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new S_Chronicle(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new S_CrystalShield(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new S_FaladorShield(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new S_DragonfireShield(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			// Jewellery
			new J_BraceletOfClay(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_BraceletOfExpeditious(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_BraceletOfFlamtaer(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_BraceletOfSlaughter(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_CelestialRing(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_RingOfRecoil(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_RingOfSuffering(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_SlayerRing(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_XericsTalisman(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new J_Camulet(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			// Helms
			new H_CircletOfWater(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			// Capes
			new C_ArdougneCloak(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new C_Coffin(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new C_MagicCape(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			// Utilities
			new U_AshSanctifier(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_BoneCrusher(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_BottomlessCompostBucket(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_FishBarrel(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_GricollersCan(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_SoulBearer(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_TeleportCrystal(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_Waterskin(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_OgreBellows(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new U_LogBasket(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			new BarrowsAhrimsHood(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsAhrimsRobetop(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsAhrimsRobeskirt(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsAhrimsStaff(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			new BarrowsDharoksHelm(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsDharoksPlatebody(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsDharoksPlatelegs(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsDharoksGreataxe(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			new BarrowsGuthansHelm(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsGuthansPlatebody(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsGuthansChainskirt(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsGuthansWarspear(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			new BarrowsKarilsCoif(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsKarilsLeathertop(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsKarilsLeatherskirt(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsKarilsCrossbow(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			new BarrowsToragsHelm(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsToragsPlatebody(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsToragsPlatelegs(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsToragsHammers(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),

			new BarrowsVeracsHelm(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsVeracsBrassard(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsVeracsPlateskirt(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
			new BarrowsVeracsFlail(client, client_thread, configs, items, infoboxes, chat_messages, notifier, config, this),
		};
		overlay_charged_items = new ChargedItemsOverlay(config, infoboxes_charged_items);

		overlays.add(overlay_charged_items);
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infoboxes.addInfoBox(infobox));
	}

	@Override
	protected void shutDown() {
		overlays.remove(overlay_charged_items);
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infoboxes.removeInfoBox(infobox));
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event) {
		log.debug("ITEM CONTAINER | " + event.getContainerId());

		for (final ChargedItemInfoBox infobox : this.infoboxes_charged_items) {
			infobox.onItemContainersChanged(event);
		}

		final ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
		final ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);

		// We need to know about items to show messages about resetting charges.
		if (!config.getResetDate().equals(date)) {
			resetCharges(date);
		}
	}

	@Subscribe
	public void onChatMessage(final ChatMessage event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onChatMessage(event));
		log.debug(
			"MESSAGE | " +
			"type: " + event.getType().name() +
			", message: " + event.getMessage().replaceAll("</?col.*?>", "") +
			", sender: " + event.getSender()
		);
	}

	@Subscribe
	public void onAnimationChanged(final AnimationChanged event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onAnimationChanged(event));
		if (event.getActor() == client.getLocalPlayer()) {
			log.debug("ANIMATION | " +
				"id: " + event.getActor().getAnimation()
			);
		}
	}

	@Subscribe
	public void onGraphicChanged(final GraphicChanged event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onGraphicChanged(event));
		if (event.getActor() == client.getLocalPlayer()) {
			log.debug("GRAPHIC | " +
				"id: " + event.getActor().getGraphic()
			);
		}
	}

	@Subscribe
	public void onConfigChanged(final ConfigChanged event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onConfigChanged(event));
		if (event.getGroup().equals(ChargesImprovedConfig.group)) {
			log.debug("CONFIG | " +
				"key: " + event.getKey() +
				", old value: " + event.getOldValue() +
				", new value: " + event.getNewValue()
			);
		}
	}

	@Subscribe
	public void onHitsplatApplied(final HitsplatApplied event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onHitsplatApplied(event));
		log.debug("HITSPLAT | " +
			"actor: " + (event.getActor() == client.getLocalPlayer() ? "self" : "enemy") +
			", type: " + event.getHitsplat().getHitsplatType() +
			", amount:" + event.getHitsplat().getAmount() +
			", others = " + event.getHitsplat().isOthers() +
			", mine = " + event.getHitsplat().isMine()
		);
	}

	@Subscribe
	public void onWidgetLoaded(final WidgetLoaded event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onWidgetLoaded(event));
		log.debug("WIDGET | " +
			"group: " + event.getGroupId()
		);
	}

	@Subscribe
	public void onMenuOptionClicked(final MenuOptionClicked event) {
		Arrays.stream(infoboxes_charged_items).forEach(infobox -> infobox.onMenuOptionClicked(event));
		log.debug("OPTION | " +
			"option: " + event.getMenuOption() +
			", target: " + event.getMenuTarget() +
			", action name: " + event.getMenuAction().name() +
			", action id: " + event.getMenuAction().getId()
		);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged event) {
		if (event.getGameState() != GameState.LOGGED_IN) return;

		// Send message about plugin updates for once.
		if (!config.getVersion().equals(plugin_version)) {
			configs.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.version, plugin_version);
			chat_messages.queue(QueuedMessage.builder()
				.type(ChatMessageType.CONSOLE)
				.runeLiteFormattedMessage(plugin_message)
				.build()
			);
		}
	}

	@Subscribe
	public void onVarbitChanged(final VarbitChanged event) {
		// If server minutes are 0, it's a new day!
		if (event.getVarbitId() == VARBIT_MINUTES && client.getGameState() == GameState.LOGGED_IN && event.getValue() == 0) {
			final String date = LocalDateTime.now(timezone).format(DateTimeFormatter.ISO_LOCAL_DATE);
			resetCharges(date);
		}
	}

	@Subscribe
	public void onGameTick(final GameTick gametick) {
		for (final ChargedItemInfoBox infobox : this.infoboxes_charged_items) {
			infobox.onGameTick(gametick);
		}
	}

	private void resetCharges(final String date) {
		configs.setConfiguration(ChargesImprovedConfig.group, ChargesImprovedConfig.date, date);
		Arrays.stream(infoboxes_charged_items).forEach(ChargedItemInfoBox::resetCharges);
	}

	public static String getChargesMinified(final int charges) {
		if (charges == CHARGES_UNLIMITED) return "∞";
		if (charges == CHARGES_UNKNOWN) return "?";
		if (charges < 1000) return String.valueOf(charges);
		if (charges >= 1000000) return charges / 1000000 + "M";

		final int thousands = charges / 1000;
		final int hundreds = Math.min((charges % 1000 + 50) / 100, 9);

		return thousands + (thousands < 10 && hundreds > 0 ? "." + hundreds : "") + "K";
	}
}
