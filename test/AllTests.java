package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MainTest.class, TypeVisitorTest.class })
public class AllTests {
	private static String BASEDIR = "\\Users\\Esther\\eclipse-workspace\\SENG300\\SENG 300 A3\\src\\main\\java\\test";
	
	public static String getBaseDir() {
		return BASEDIR;
	}
}
