package synapse.transfer;

import org.itxtech.synapseapi.SynapseAPI;
import org.itxtech.synapseapi.SynapseEntry;
import org.itxtech.synapseapi.SynapsePlayer;
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
		api=SynapseAPI.getInstance();

	}

	@Override
	public void onDisable() {
		getLogger().info(TextFormat.WHITE + "Synapse Transfer disabled sucessfully!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean player=false;
		Player p;
		SynapsePlayer sp;
		if (sender instanceof Player) {
			player=true;
			p = (Player)sender;
			sp = (SynapsePlayer)p;
			
		}
		switch (command.getName().toLowerCase()) {
		case "server":
			if (args.length == 0) {
				sender.sendMessage(TextFormat.GOLD + "You can connect to the following servers:");
				String servers = "";
				int size = api.getSynapseEntries().keySet().size();
				for (SynapseEntry se : api.getSynapseEntries().values()) {
					if (size == 1) {
						servers += se.getServerDescription() + ".";
					} else {
						servers += se.getServerDescription() + ", ";
					}
					size-=1;
				}
				sender.sendMessage(TextFormat.GOLD + servers);
			}
			return true;
		}
		return false;
	}
}//((SynapsePlayer) event.getPlayer()).transfer(((SynapsePlayer) event.getPlayer()).getSynapseEntry().getClientData().getHashByDescription("lobby"));
