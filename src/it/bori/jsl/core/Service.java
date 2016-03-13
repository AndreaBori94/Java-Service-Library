package it.bori.jsl.core;

/**
 * Service class itself, extends this to create custom service
 * @author Andrea Bori
 *
 */
public class Service implements Runnable {

	/**
	 * Process Name ( if more than 1, just automatic add a prefix: _n )
	 */
	private String processName;
	/**
	 * Time in MS of waiting
	 */
	private int processDelay;
	/**
	 * Running status, true if it's running or false if it's not The tread it's
	 * always running but in 2 phase, when true it call the onEnabled and when
	 * false it call onDisabled
	 */
	private boolean isRunning = false;
	/**
	 * The report of the Service
	 */
	private it.bori.jsl.core.ServiceReport SR;

	/**
	 * The thread object that manage the process it's self ( it's native to java
	 * )
	 */
	private Thread thread;

	/**
	 * Default constructor
	 * 
	 * @param name
	 *            Name of the process
	 * @param delay
	 *            time to wait between each execution
	 */
	public Service(String name, int delay) {
		setName(name);
		setDelay(delay);
		this.thread = new Thread(this);
		this.SR = new ServiceReport();
	}

	/**
	 * Get the thread objet ( native to java )
	 * 
	 * @return
	 */
	public Thread getThread() {
		return this.thread;
	}

	/**
	 * Set the service status to enabled
	 */
	public void enable() {
		this.isRunning = true;
	}

	/**
	 * Set the service status to disabled
	 */
	public void disable() {
		this.isRunning = false;
	}

	/**
	 * Terminate the service ( just no running, no enabled, no disabled but it's
	 * still initialized )
	 */
	public void terminate() {
		this.thread.interrupt();
	}

	/**
	 * Restart the service
	 */
	public void restart() {
		terminate();
		this.thread.start();
	}

	/**
	 * Set the name of the service, DO NOT CALL AFTER ADDED TO SERVICEMANAGER
	 * INSTANCE
	 * 
	 * @param name
	 *            the name of the service
	 */
	public void setName(String name) {
		this.processName = name;
	}

	/**
	 * Set the delay ( time to wait in millisecond between each execution )
	 * 
	 * @param delay
	 *            time in millisecond
	 */
	public void setDelay(int delay) {
		this.processDelay = delay;
	}

	/**
	 * Get the delay ( time to wait in millisecond between each execution )
	 * 
	 * @return int time in milliseconds
	 */
	public int getDelay() {
		return this.processDelay;
	}

	/**
	 * Get the name of the service
	 * 
	 * @return String the name of the service
	 */
	public String getName() {
		return this.processName;
	}

	/**
	 * Override this. Behavior of thread when the status is: running true
	 */
	public void onEnabled() {
	}

	/**
	 * Override this. Behavior of thread when the status is: running false
	 */
	public void onDisabled() {
	}

	/**
	 * Get the report, it's used to communicate between service and application
	 * 
	 * @return ServiceReport - like JSON do
	 */
	public ServiceReport getReport() {
		return this.SR;
	}

	/**
	 * Update the report, setting a key with a value
	 * 
	 * @param key
	 *            the key of the report
	 * @param value
	 *            the value of the key
	 */
	public void updateReport(String key, String value) {
		getReport().setResponse(key, value);
	}

	/**
	 * Running method inherit from Runnable interface
	 */
	@Override
	public void run() {
		try {
			while (true) {
				if (isRunning) {
					onEnabled();
				} else {
					onDisabled();
				}
				Thread.sleep(getDelay());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
