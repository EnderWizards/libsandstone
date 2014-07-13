package lib.enderwizards.sandstone.mod.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;

import lib.enderwizards.sandstone.util.LanguageHelper;
import cpw.mods.fml.client.config.ConfigGuiType;
import cpw.mods.fml.client.config.GuiConfigEntries.IConfigEntry;
import cpw.mods.fml.client.config.GuiEditArrayEntries.IArrayEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class ConfigElement<T> implements IConfigElement<T> {
	
	private boolean isProperty;
	
	private String mod_id;
	
	private String group;
	private String key;
			
	private Config config;
	private Config def;
	
	public ConfigElement(String mod_id, String key, Map<String, Object> config, Map<String, Object> def) {
		this(mod_id, "", key, config, def);
	}
	
	public ConfigElement(String mod_id, String group, String key, Map<String, Object> config, Map<String, Object> def) {
		this.mod_id = mod_id;
						
		this.group = group;
		this.key = key;
		
		this.config = new ConfigImpl(null, config);
		this.def = new ConfigImpl(null, def);
		if(this.config.get(group, key) instanceof Map) {
			isProperty = false;
			assert(this.def.get(group, key) instanceof Map);
		} else {
			isProperty = true;
		}
	}
	
	@Override
	public boolean isProperty() {
		return isProperty;
	}

	@Override
	public Class<? extends IConfigEntry> getConfigEntryClass() {
		return null;
	}

	@Override
	public Class<? extends IArrayEntry> getArrayEntryClass() {
		return null;
	}

	@Override
	public String getName() {
		return key;
	}

	@Override
	public String getQualifiedName() {
		return key;
	}

	@Override
	public String getLanguageKey() {
		return mod_id + ".configgui." + this.getName();
	}

	@Override
	public String getComment() {
		return LanguageHelper.getLocalization(mod_id + ".configgui." + this.getName() + ".tooltip");
	}

	@Override
	public List<IConfigElement> getChildElements() {
		List<IConfigElement> elements = new ArrayList<IConfigElement>();
		Map<String, Object> map = config.getGroup(key); 
		for(String key1 : map.keySet()) {
			elements.add(getTypedElement(mod_id, key, key1, map, (Map) def.get("", key)));
		}
		return elements;
	}

	@Override
	public ConfigGuiType getType() {
		return getType(config.get(group, key));
	}
	
	public static ConfigElement<?> getTypedElement(String mod_id, String key, Map<String, Object> value, Map<String, Object> def) {
		return getTypedElement(mod_id, "", key, value, def);
	}
	
	public static ConfigElement<?> getTypedElement(String mod_id, String group, String key, Map<String, Object> value, Map<String, Object> def) {
		if(getType(value) == null) {
			return new ConfigElement<String>(mod_id, key, value, def);
		}
		
		switch(getType(value)) {
		case BOOLEAN:
		    return new ConfigElement<Boolean>(mod_id, key, value, def);
		case DOUBLE:
		    return new ConfigElement<Double>(mod_id, key, value, def);
		case INTEGER:
		    return new ConfigElement<Integer>(mod_id, key, value, def);
		default:
		    return new ConfigElement<String>(mod_id, key, value, def);
		}
	}
	
	public static ConfigGuiType getType(Object object) {
		if(object instanceof Boolean) {
			return ConfigGuiType.BOOLEAN;
		} else if(object instanceof Integer) {
			return ConfigGuiType.INTEGER;
		} else if(object instanceof String) {
			return ConfigGuiType.STRING;
		} else if(object instanceof Map) {
			return ConfigGuiType.CONFIG_CATEGORY;
		}
		return null;
	}

	@Override
	public boolean isList() {
		return config.get(group, key) instanceof List;
	}

	@Override
	public boolean isListLengthFixed() {
		return false;
	}

	@Override
	public int getMaxListLength() {
		return -1;
	}

	@Override
	public boolean isDefault() {
		return config.get(group, key) == def.get(group, key);
	}

	@Override
	public Object getDefault() {
		return def.get(group, key);
	}

	@Override
	public Object[] getDefaults() {
		return new Object[]{ def.get(group, key) };
	}

	@Override
	public void setToDefault() {
		config.set(group, key, def.get(group, key));;
	}

	@Override
	public boolean requiresWorldRestart() {
		return true;
	}

	@Override
	public boolean showInGui() {
		return true;
	}

	@Override
	public boolean requiresMcRestart() {
		return false;
	}

	@Override
	public Object get() {
		return config.get(group, key);
	}

	@Override
	public Object[] getList() {
		return ((List<Object>) config.get(group, key)).toArray();
	}

	@Override
	public void set(T value) {
		config.set(group, key, value);
	}

	@Override
	public void set(T[] aVal) {
		if(isProperty) {
			config.set(group, key, aVal);
		}
	}

	@Override
	public String[] getValidValues() {
		return null;
	}

	@Override
	public T getMinValue() {
		return (T) String.valueOf(Integer.MIN_VALUE);
	}

	@Override
	public T getMaxValue() {
		return (T) String.valueOf(Integer.MAX_VALUE);
	}

	@Override
	public Pattern getValidationPattern() {
		return null;
	}

}
