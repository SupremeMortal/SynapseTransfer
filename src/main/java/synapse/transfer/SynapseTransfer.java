package transfer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
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

class SynapseTransfer extends PluginBase{
	/** @var config */
	private Config config;
	private Map<String, Object> conf;
	private HashMap<String, String> list;
	@Override
	public void onEnable(){
		new File(getDataFolder().toString()).mkdir();
		config = new Config(getDataFolder() + "config.yml");
		conf = config.getAll();
		try{
			list = (HashMap<String, String>) getConfig().get("list");
		}catch(Exception e){
			e.printStackTrace();
		}
		if(!getServer().isSynapseEnabled()){
			getLogger().error("Synapse Client service has been disabled, this plugin won't work!");
			setEnabled(false);
			return;
		}
		getLogger().info("Synapse Transfer has been enabled.");
	}
	@Override
	public void onDisable(){
		saveConfig();
		getLogger().info("Synapse Transfer has been disabled.");
	}
	/**
	 * @param string ld
	 * @return null|string
	 */
	public String getDescriptionByListData(String ld){
		if(list.containsKey(ld)){
			return list.get(ld);
		}
		return null;
	}

	/**
	 * @param $des
	 * @return array|null
	 */
	public String getClientDataByDescription(String des){
		foreach(getServer().getSynapse().getClientData() as $cdata){
			if($cdata["description"] == des){
				return $cdata;
			}
		}
		return null;
	}

	/**
	 * @param string $des
	 * @return null|string
	 */
	public String getClientHashByDescription(String des){
		foreach(getServer().getSynapse().getClientData() as $hash => $cdata){
			if($cdata["description"] == $des){
				return $hash;
			}
		}
		return null;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		switch(command.getName().toLowerCase()){
			case "scid":
				if(command.testPermission(sender)){
					if(args[0].isEmpty() || (args[0].toLowerCase() != "add" && args[0].toLowerCase() != "remove")){
						return false;
					}
					switch(args[0].toLowerCase()){
						case "add":
							if(!args[1].isEmpty() && !args[2].isEmpty()){
								list.put(args[1], args[2]);
								sender.sendMessage(TextFormat.GREEN+args[1]+" => "+args[2]);
								return true;
							}
							sender.sendMessage(TextFormat.RED+"Missing arguments");
							break;
						case "remove":
							if(list.containsKey(args[1])){
								list.remove(args[1]);
								sender.sendMessage(TextFormat.GREEN+args[1]+" has been removed successfully");
								return true;
							}
							sender.sendMessage(TextFormat.RED+args[1]+" does not exist");
							break;
					}
				}else{
					sender.sendMessage(TextFormat.RED+"You don't have permission to execute this command");
				}
				return true;
			case "listclients":
				if(command.testPermission(sender)){
					foreach($this->list as $c){
						if(($data = $this->getClientDataByDescription($c)) != null){
							sender.sendMessage("ID: $c Status: "+TextFormat.GREEN+"Online"+TextFormat.WHITE+" Players: "+TextFormat.GREEN+"{$data['playerCount']}"+TextFormat.WHITE+"/"+TextFormat.YELLOW+"{$data['maxPlayers']}");
						}else{
							sender.sendMessage("ID: $c Status: "+TextFormat.RED+"Offline");
						}
					}
				}else{
					sender.sendMessage(TextFormat.RED+"You don't have permission to execute this command");
				}
				return true;
			case "transfer":
				if(command.testPermission(sender)){
					if(args.length == 2){
						if(args[0].toLowerCase() == sender.getName().toLowerCase()){
							Player player = getServer().getPlayerExact(args[0]);
							Object des = getDescriptionByListData(args[1]);
							if(des == null){
								sender.sendMessage(TextFormat.RED+"Undefined SynapseClient "+args[1]);
								return true;
							}
							if(player instanceof Player && (hash = getClientHashByDescription(des)) != null){
								if(des == sender.getServer().getSynapse().getDescription()){
									sender.sendMessage(TextFormat.RED+"Cannot transfer to the current server");
									return true;
								}
								if($this->conf["show-transfer-message"] == true){
									getServer().broadcastMessage(TextFormat.AQUA+"[SynapseTransfer] "+player.getName()+" has been transferred to "+args[1]);
								}
								player.transfer(hash);
							}else{
								sender.sendMessage(TextFormat.RED+args[0]+" is not a SynapsePlayer or "+args[1]+" is not a SynapseClient");
							}
						}
					}else if(args.length == 1){
						Object des = getDescriptionByListData(args[0]);
						if(des == null){
							sender.sendMessage(TextFormat.RED+"Undefined SynapseClient "+args[0]);
							return true;
						}
						if((hash = getClientHashByDescription(des)) != null){
							if(des == sender.getServer().getSynapse().getDescription()){
								sender.sendMessage(TextFormat.RED+"Cannot transfer to the current server");
								return true;
							}
							if(sender instanceof Player){
								if(config.getBoolean("transfer-message") == true){
									getServer().broadcastMessage(TextFormat.AQUA+"[SynapseTransfer] "+sender.getName()+" has been transferred to "+args[0]);
								}
								sender.transfer(hash);
							}else{
								sender.sendMessage(TextFormat.RED+"You must be a SynapsePlayer to execute this command");
							}
						}
					}else{
						return false;
					}
				}else{
					sender.sendMessage(TextFormat.RED+"You don't have permission to execute this command");
				}
				return true;
		}
		return false;
	}
}
