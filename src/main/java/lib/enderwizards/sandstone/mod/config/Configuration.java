package lib.enderwizards.sandstone.mod.config;

import io.ous.jtoml.JToml;
import io.ous.jtoml.Toml;
import io.ous.jtoml.impl.KeyGroup;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A helper class for parsing TOML and vanilla-style config files.
 * 
 * @author TheMike
 */
public class Configuration {

	public static TomlConfig toml(File file) {
		try {
			file.createNewFile();
			return new TomlConfig(JToml.parse(file));
		} catch (IOException e) { e.printStackTrace(); }
		return null;
	}
	
}
