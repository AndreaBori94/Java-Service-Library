package it.bori.jsl.core;

/**
 * Manage list of service
 * 
 * @author Andrea Bori
 *
 */
public class ServicesManager {

	/**
	 * List of service ( all service added will be stored here )
	 */
	private static java.util.List<it.bori.jsl.core.Service> services = new java.util.ArrayList<it.bori.jsl.core.Service>();

	/**
	 * Generate a simple report of all services running
	 * 
	 * @return the report
	 */
	public static ServiceReport getStatus() {
		it.bori.jsl.core.ServiceReport SR = new it.bori.jsl.core.ServiceReport();
		SR.setResponse("SERVICES_COUNT", "" + services.size());
		int media = 0;
		for (int i = 0; i < services.size(); i++) {
			media += services.get(i).getDelay();
		}
		media = media / services.size();
		SR.setResponse("SERVICES_DELAY", "" + media);
		return SR;
	}

	/**
	 * Just return the requested service, if no service has been found, simply
	 * return null
	 * 
	 * @param serviceName
	 *            the name of the service
	 * @return Object the service instance founded
	 */
	public static it.bori.jsl.core.Service getService(String serviceName) {
		Service s = null;
		for (int it = 0; it != ServicesManager.services.size(); it++) {
			s = ServicesManager.services.get(it);
			if (s.getName().equals(serviceName)) {
				return s;
			}
		}
		return s;
	}

	/**
	 * Seek the service, call his method to get the reponse and ask for a
	 * specific key. It's used to do short request
	 * 
	 * @param serviceName
	 *            The name of the service that contain the required response
	 * @param key
	 *            the key that contain the required value
	 * @return String the value founded or null if nothing has been found
	 */
	public static String getServiceReport(String serviceName, String key) {
		String rsp = ServicesManager.getService(serviceName).getReport()
				.getReposnse(key);
		if (rsp == null) {
			return "RESPONSE_UNDEFINED";
		} else
			return rsp;
	}

	/**
	 * Seek the service, call his method to get the response and ask for a
	 * specific key to be changed it's used as way to "reply" to the service
	 * 
	 * @param serviceName
	 *            The name of the service that contain the require response
	 * @param key
	 *            the key that contain the required value that need to be
	 *            changed
	 * @param value
	 *            the new value to be assiged
	 */
	public static void setServiceReport(String serviceName, String key,
			String value) {
		ServicesManager.getService(serviceName).getReport()
				.setResponse(key, value);
	}

	/**
	 * Add a new service to the service list, if another service with the same
	 * name will be found, it's just add the service with an N as suffix
	 * 
	 * @param s
	 *            The new service that need to be added
	 * @return Integer number of services that has the same name
	 */
	public static int addService(Service s) {
		if (getService(s.getName()) == null) {
			s.getThread().start();
			s.enable();
			ServicesManager.services.add(s);
			return 0;
		} else {
			int i = 0;
			do {
				if (getService(s.getName() + "_" + i) == null) {
					s.setName(s.getName() + "_" + i);
					s.getThread().start();
					s.enable();
					ServicesManager.services.add(s);
					return i;
				} else {
					i++;
				}
			} while (true);
		}
	}
}