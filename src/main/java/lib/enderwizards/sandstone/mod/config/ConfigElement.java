package lib.enderwizards.sandstone.mod.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import lib.enderwizards.sandstone.util.LanguageHelper;
import cpw.mods.fml.client.config.ConfigGuiType;
import cpw.mods.fml.client.config.GuiConfigEntries.IConfigEntry;
import cpw.mods.fml.client.config.GuiEditArrayEntries.IArrayEntry;
import cpw.mods.fml.client.config.IConfigElement;

public class ConfigElement<T> implements IConfigElement<T> {
	
	private boolean isProperty;
	
	private String mod_id;
	private String key;
	
	private Object object;
	private Object def;
	
	public ConfigElement(String mod_id, String key, Object object, Object def) {
		this.mod_id = mod_id;
		this.key = key;
		
		this.object = object;
		this.def = def;
		if(object instanceof Map) {
			isProperty = false;
			assert(def instanceof Map);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends IArrayEntry> getArrayEntryClass() {
		// TODO Auto-generated method stub
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
		Map<String, Object> map = ((Map) object); 
		for(String key : map.keySet()) {
			elements.add(getTypedElement(mod_id, key, map.get(key), ((Map) def).get(key)));
		}
		return elements;
	}

	@Override
	public ConfigGuiType getType() {
		return getType(object);
	}
	
	public static IConfigElement<?> getTypedElement(String mod_id, String key, Object value, Object def) {
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
		return object instanceof List;
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
		return false;
	}

	@Override
	public Object getDefault() {
		return object == def;
	}

	@Override
	public Object[] getDefaults() {
		return new Object[]{ def };
	}

	@Override
	public void setToDefault() {
		object = def;
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
		return object;
	}

	@Override
	public Object[] getList() {
		return ((List) object).toArray();
	}

	@Override
	public void set(T value) {
		object = value;
	}

	@Override
	public void set(T[] aVal) {
		if(object instanceof List) {
			List list = ((List) object);
			list.clear();
			for(Object obj : aVal) {
				list.add(obj);
			}
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
