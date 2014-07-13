package lib.enderwizards.sandstone.mod.config;

import java.util.List;

import cpw.mods.fml.client.config.IConfigElement;

public interface Config {
	
	public void require(String group, String key, Object fallback);
		
	public Object get(String group, String key);
	
	public Integer getInt(String group, String key);
	
	public boolean getBool(String group, String key);
	
	public List<String> getGroups();
	
	public List<Object> getKeys(String group);
	
	public List<IConfigElement> toGui(String mod_id);
	
	public void save();

}
