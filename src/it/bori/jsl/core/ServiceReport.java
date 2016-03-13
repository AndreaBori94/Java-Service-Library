package it.bori.jsl.core;


/**
 * Used by service, to communicate with application
 * 
 * @author Andrea Bori
 *
 */
public class ServiceReport {

	/**
	 * Contains information like JSON do
	 */
	private java.util.Map<String, String> map = new java.util.HashMap<String, String>();

	/**
	 * Set a new value for a specific key ( Response element )
	 * 
	 * @param key
	 *            key where value will be assigned
	 * @param value
	 *            value stored at given key
	 */
	public void setResponse(String key, String value) {
		map.put(key, value);
	}

	/**
	 * Retrieve a specific value form a specific key
	 * 
	 * @param key
	 *            key where get the value
	 * @return Specific value for the given key or simply null if no value has
	 *         been found
	 */
	public String getReposnse(String key) {
		return map.get(key);
	}
}
