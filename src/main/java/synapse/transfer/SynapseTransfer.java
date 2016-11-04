package synapse.transfer;

import org.itxtech.synapseapi.SynapseAPI;
import org.itxtech.synapseapi.SynapseEntry;
import org.itxtech.synapseapi.SynapsePlayer;
import org.itxtech.synapseapi.utils.ClientData.Entry;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * @author iTX Technologies ported by SupremeMortal
 * @link https://itxtech.org
 *
 */

public class SynapseTransfer extends PluginBase {
	private static SynapseAPI api;

	@Override
	public void onEnable() {
		getLogger().info(TextFormat.WHITE + "Synapse Transfer enabled sucessfully!");
		api = SynapseAPI.getInstance();

	}

	@Override
	public void onDisable() {
		getLogger().info(TextFormat.WHITE + "Synapse Transfer disabled sucessfully!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean player = false;
		boolean sPlayer = false;
		Player p = null;
		SynapsePlayer sp = null;
		if (sender instanceof Player) {
			player = true;
			p = (Player) sender;
			if (p instanceof SynapsePlayer) {
				sp = (SynapsePlayer) p;
				sPlayer = true;
			}

		}
		switch (command.getName().toLowerCase()) {
		case "server":
			if (sPlayer = true) {
				if (args.length == 0) {
					sender.sendMessage(TextFormat.GOLD + "You can connect to the following servers:");
					String servers = "";
					int size = sp.getSynapseEntry().getClientData().clientList.keySet().size();

					for (Entry se : sp.getSynapseEntry().getClientData().clientList.values()) {
						if (size == 1) {
							servers += se.getDescription() + ".";
						} else {
							servers += se.getDescription() + ", ";
						}
						size -= 1;
					}
					sender.sendMessage(TextFormat.GOLD + servers);
					return true;
				} else if (args.length == 1) {
					try {
						sp.transfer(sp.getSynapseEntry().getClientData().getHashByDescription(args[0]));
					} catch (Exception e) {
						sender.sendMessage("That server does not exist!");
						return true;
					}
				}
			} else {
				sender.sendMessage(TextFormat.RED + "You are not a synapse player!");
				return true;
			}
		}
		return false;
	}
}
