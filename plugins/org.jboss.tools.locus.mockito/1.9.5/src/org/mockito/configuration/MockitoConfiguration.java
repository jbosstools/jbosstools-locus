package org.mockito.configuration;

public class MockitoConfiguration extends DefaultMockitoConfiguration {

	private static boolean cleansStackTrace = false;
	
	private static boolean enableClassCache = true;
	
	@Override
	public boolean cleansStackTrace() {
		return cleansStackTrace;
	}

	@Override
	public boolean enableClassCache() {
		return enableClassCache;
	}

	public static void setCleansStackTrace(boolean cleansStackTrace) {
		MockitoConfiguration.cleansStackTrace = cleansStackTrace;
	}

	public static void setEnableClassCache(boolean enableClassCache) {
		MockitoConfiguration.enableClassCache = enableClassCache;
	}

}
