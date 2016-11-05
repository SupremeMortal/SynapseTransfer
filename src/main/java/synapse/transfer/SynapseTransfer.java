package synapse.transfer;

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
 * @author SupremeMortal
 * @link https://github.com/SupremeMortal/SynapseTransfer
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
		switch (command.getName().toLowerCase()) {
		case "server":
			if (isSynapsePlayer(sender)) {
				SynapsePlayer sp = (SynapsePlayer) sender;
				if (args.length == 0) {
					sender.sendMessage(TextFormat.GOLD + "You are currently connected to: "
							+ sp.getSynapseEntry().getServerDescription());
					sender.sendMessage(TextFormat.GOLD + "You can connect to the following servers:");
					sender.sendMessage(TextFormat.GOLD + retrieveServers(sp));
					return true;
				} else if (args.length == 1) {
					transferPlayer(sp, args[0]);
					return true;
				}
				return false;
			} else {
				sender.sendMessage(TextFormat.RED + "You are not a synapse player!");
				return true;
			}
		case "send":
			if (args.length != 0 || args.length < 3) {
				if (args.length != 1) {
					if (args[0] == "*") {
						sender.sendMessage(TextFormat.GOLD + transferAllPlayers(args[1]));
					} else {
						if (getServer().getPlayer(args[0]) != null) {
							if (isSynapsePlayer(getServer().getPlayer(args[0]))) {
								SynapsePlayer sp = (SynapsePlayer) getServer().getPlayer(args[0]);
								if (transferPlayer(sp, args[1]) == 1) {
									sender.sendMessage(TextFormat.GREEN + "Player successfully transfered");
								} else {
									sender.sendMessage(TextFormat.RED + "Server does not exist!");
								}
							}
						} else {
							sender.sendMessage(TextFormat.RED + "Player does not exist!");
						}
					}
					return true;
				} else {
					if (getServer().getPlayer(args[0]) == null) {
						sender.sendMessage(TextFormat.RED + "Player does not exist!");
					} else {
						Player p1 = getServer().getPlayer(args[0]);
						if (isSynapsePlayer(p1)) {
							sender.sendMessage(TextFormat.GOLD + p1.getName() + " can currently connect to:");
							sender.sendMessage(TextFormat.GOLD + retrieveServers((SynapsePlayer) p1));
						} else {
							sender.sendMessage(TextFormat.RED + "This player is not connected through Synapse!");
						}
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	private String retrieveServers(SynapsePlayer sp) {
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
		return servers;
	}

	private String transferAllPlayers(String server) {
		int players = 0, failed = 0;
		for (Player pl : getServer().getOnlinePlayers().values()) {
			if (isSynapsePlayer(pl)) {
				failed += transferPlayer((SynapsePlayer) pl, server);
			}
			players += 1;
		}
		return (players - failed) + "/" + players + " successfully transfered";
	}

	private int transferPlayer(SynapsePlayer sp, String server) {
		try {
			sp.transfer(sp.getSynapseEntry().getClientData().getHashByDescription(server));
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}

	private boolean isSynapsePlayer(Object o) {
		if (o instanceof SynapsePlayer) {
			return true;
		}
		return false;
	}
}
