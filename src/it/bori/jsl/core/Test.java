package it.bori.jsl.core;


public class Test {
	public static void main(String[] arg) {
		ServicesManager.addService(new it.bori.jsl.core.services.FileChangeService("C:\\test\\test.txt"));
		while (true) {
			if (ServicesManager.getServiceReport("FileChangeService",
					"HAS_CHANGE").equals("true")) {
				System.out.println(ServicesManager.getServiceReport("FileChangeService",
						"HAS_CHANGE").equals("true"));
				ServicesManager.setServiceReport("FileChangeService",
						"HAS_CHANGE", "false");
			}
		}
	}

	public static void main2(String[] arg) {
		ServicesManager.addService(new Service("anonymous", 1) {
			@Override
			public void onEnabled() {
				super.onEnabled();
				if (!new java.io.File("C:\\Ciao\\").exists()) {
					updateReport("DirectoryExist", "false");
					new java.io.File("C:\\Ciao\\").mkdir();
				} else {
					updateReport("DirectoryExist", "true");
				}
			}
		});

		while (true) {
			if (ServicesManager.getServiceReport("anonymous", "DirectoryExist")
					.equals("true")) {
			} else
				System.out.println("Il report dice che non esiste");
		}
	}
}