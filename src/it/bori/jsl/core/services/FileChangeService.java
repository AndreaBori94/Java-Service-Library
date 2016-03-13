package it.bori.jsl.core.services;


/**
 * This service take care of a single file. The process consist to check the
 * last time edited of the file, if it's found it's assign to the Report a true
 * on HAS_CHANGE and wait for application to ask isChanged(), when call that
 * method the value of HAS_CHANGE became false again, and so on. If the process
 * ends, and application it's closed, when application start again, if the file
 * was edited all information about last "start" was "loosed" example:
 * ServicesManager.addService(new FileChangeService("C:\\test\\test.txt"));
 * while (true) { if (ServicesManager.getServiceReport("FileChangeService",
 * "HAS_CHANGE").equals("true")) {
 * System.out.println(ServicesManager.getServiceReport("FileChangeService",
 * "HAS_CHANGE").equals("true"));
 * ServicesManager.setServiceReport("FileChangeService", "HAS_CHANGE", "false");
 * } }
 * 
 * @author Andrea Bori
 *
 */
public class FileChangeService extends it.bori.jsl.core.Service {

	/**
	 * Last time edited
	 */
	private long last = 0;
	/**
	 * Current time edited
	 */
	private long current = 0;
	/**
	 * The target file
	 */
	private java.io.File target = null;

	/**
	 * Default constructor of the class
	 * 
	 * @param pathOfFile
	 *            the path of the file to target the service
	 */
	public FileChangeService(String pathOfFile) {
		super("FileChangeService", 100);
		target = new java.io.File(pathOfFile);
		current = target.lastModified();
		last = target.lastModified();
		updateReport("HAS_CHANGE", "false");
	}

	/**
	 * Inherit by Runnable interface, native of java
	 */
	@Override
	public void onEnabled() {
		super.onEnabled();
		current = target.lastModified();
		if (current != last) {
			updateReport("HAS_CHANGE", "true");
			last = current;
		}
	}
}
