package synapse.transfer;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.itxtech.nemisys.Client;
import org.itxtech.nemisys.Player;
import org.itxtech.nemisys.command.Command;
import org.itxtech.nemisys.command.CommandSender;
import org.itxtech.nemisys.event.EventHandler;
import org.itxtech.nemisys.event.EventPriority;
import org.itxtech.nemisys.event.Listener;
import org.itxtech.nemisys.event.server.ServerCommandEvent;
import org.itxtech.nemisys.plugin.PluginBase;
import org.itxtech.nemisys.utils.TextFormat;

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
	@Override
	public void onEnable() {
		getLogger().info(TextFormat.WHITE + "Synapse Transfer enabled sucessfully!");

	}

	@Override
	public void onDisable() {
		getLogger().info(TextFormat.WHITE + "Synapse Transfer disabled sucessfully!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean player=false;
		if (sender instanceof Player) {
			player=true;
		}
		switch (command.getName().toLowerCase()) {
		case "server":
			Map<String, Client> data = getServer().getClients();
			Collection<Client> clients = data.values();
			if (args.length == 0) {
				sender.sendMessage(TextFormat.GOLD + "Tou can connect to the following servers:");
				String servers = "";
				int size = clients.size();
				for (Client c : clients) {
					if (size == 1) {
						servers += c.getDescription() + ".";
					} else {
						servers += c.getDescription() + ", ";
					}
				}
				sender.sendMessage(TextFormat.GOLD + servers);
			}
			return true;
		}
		return false;
	}
}
