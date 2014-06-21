package lib.enderwizards.sandstone.mod.config;

import io.ous.jtoml.Toml;

import java.util.HashMap;
import java.util.Map;

public class TomlConfig {
	
	private Map<String, Map<String, Object>> require = new HashMap<String, Map<String, Object>>();
	private Toml config;
	
	public TomlConfig(Toml toml) {
		this.config = toml;
	}
	
	public void require(String prefix, String key, Object fallback) {
		if(!require.containsKey(prefix))
			require.put(prefix, new HashMap<String, Object>());
		require.get(prefix).put(key, fallback);
	}
	
	public Object get(String prefix, String key) {
		if(config.getKeyGroup(prefix) == null || config.getKeyGroup(prefix).getValue(key) == null) {
			if(require.containsKey(prefix) && require.get(prefix).containsKey(key))
				return require.get(prefix).get(key);
			return null;
		}
		return config.getKeyGroup(prefix).getValue(key);
	}
	
	public Integer getInt(String prefix, String key) {
		if(this.get(prefix, key) instanceof Long)
			return ((Long) this.get(prefix, key)).intValue();
		else if(this.get(prefix, key) instanceof Integer)
			return (Integer) this.get(prefix, key);
		return null;
	}
	
	public boolean getBool(String prefix, String key) {
		return ((Boolean) this.get(prefix, key));
	}

}
