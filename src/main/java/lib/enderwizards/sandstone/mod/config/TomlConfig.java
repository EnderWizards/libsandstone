package lib.enderwizards.sandstone.mod.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.github.trainerguy22.jtoml.impl.Toml;

import cpw.mods.fml.client.config.IConfigElement;

public class TomlConfig implements Config {
	
	private Map<String, Object> config;
	private Map<String, Object> defaults = new HashMap<String, Object>();
	public File file;
	
	public TomlConfig(File file, Map<String, Object> config) {
		this.file = file;
		this.config = config;
	}
	
	public Map<String, Object> getGroup(String group) {
		return config.get(group) instanceof Map ? ((Map<String, Object>) config.get(group)) : null;
	}
	
	public Map<String, Object> getDefaultGroup(String group) {
		return defaults.get(group) instanceof Map ? ((Map<String, Object>) defaults.get(group)) : null;
	}
	
	public void require(String group, String key, Object fallback) {
		if(!config.containsKey(group) || !(config.get(group) instanceof Map)) {
			if(config.get(group) != null) {
				config.remove(group);
			}
			config.put(group, new HashMap<String, Object>());
		}
		getGroup(group).put(key, fallback);
		
		if(!defaults.containsKey(group)) {
			defaults.put(group, new HashMap<String, Object>());
		}
		getDefaultGroup(group).put(key, fallback);
	}
	
	public Object get(String group, String key) {
		if(getGroup(group) == null)
			return null;
		return getGroup(group).get(key);
	}
	
	public Integer getInt(String group, String key) {
		if(this.get(group, key) instanceof Long)
			return ((Long) this.get(group, key)).intValue();
		else if(this.get(group, key) instanceof Integer)
			return (Integer) this.get(group, key);
		return null;
	}
	
	public boolean getBool(String group, String key) {
		return ((Boolean) this.get(group, key));
	}

	@Override
	public List<String> getGroups() {
		List<String> groups = new ArrayList<String>();
		for(String key : config.keySet()) {
			if(getGroup(key) != null)
				groups.add(key);
		}
		
		return groups;
	}

	@Override
	public List<Object> getKeys(String prefix) {
		List<Object> keys = new ArrayList<Object>();
		
		if(getGroup(prefix) == null) {
			return keys;
		}
		
		keys.addAll(getGroup(prefix).values());
		return keys;
	}

	@Override
	public void save() {
		Toml.write(file, config);
	}

	@Override
	public List<IConfigElement> toGui(String mod_id) {
		List<IConfigElement> elements = new ArrayList<IConfigElement>();
		for(String key : config.keySet()) {
			if(defaults.containsKey(key)) {
				elements.add(ConfigElement.getTypedElement(mod_id, key, config.get(key), defaults.get(key)));
			}
		}
		return elements;
	}

}
