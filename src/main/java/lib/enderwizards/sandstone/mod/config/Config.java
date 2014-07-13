package lib.enderwizards.sandstone.mod.config;

import java.io.File;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.ModContainer;

public interface Config {
	
	public void require(String group, String key, Object fallback);
		
	public Object get(String group, String key);
	
	public Integer getInt(String group, String key);
	
	public boolean getBool(String group, String key);
	
	public List<String> getGroups();
	
	public Map<String, Object> getGroup(String group);
	
	public List<Object> getKeys(String group);
	
	public File getFile();
	
	public void set(String group, String key, Object value);
		
	public List<IConfigElement> toGui(String mod_id);
	
	public void save();

}
