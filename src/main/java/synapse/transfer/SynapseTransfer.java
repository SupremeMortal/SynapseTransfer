package synapse.transfer;

import java.util.Map;
import java.util.Set;

import org.itxtech.nemisys.Client;
import org.itxtech.nemisys.Player;
import org.itxtech.nemisys.command.Command;
import org.itxtech.nemisys.command.CommandSender;
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

public class SynapseTransfer extends PluginBase{
	@Override
	public void onEnable(){
		getLogger().info(TextFormat.WHITE+"Synapse Transfer enabled sucessfully!");
	}
	@Override
	public void onDisable(){
		getLogger().info(TextFormat.WHITE+"Synapse Transfer disabled sucessfully!");
	}
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)){
			return false;
		}else{
			Map<String,Client> clients=getServer().getClients();
			Set<String>keys=clients.keySet();
	        if(args.length==0 || args.length < 1){
	        	sender.sendMessage(TextFormat.GOLD+"Tou can connect to the following servers:");
	        	String servers="";
	        	int size = keys.size();
	        	for(String name:keys){
	        		if(size==1){
	        			servers+=name+".";
	        		}else{
	        			servers+=name+", ";
	        		}
	        	}
	        	sender.sendMessage(TextFormat.GOLD+servers);
	        }
		}
        return true;
    }
}
