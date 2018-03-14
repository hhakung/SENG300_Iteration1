package test;

import source.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.junit.Test;
import org.junit.Assert;



public class MainTest {
	
	/**
	 * Checks that Main can be instantiated
	 */
	@Test
	public void testMainInstantiation() {
		Main m = new Main();
		assertNotNull(m);
	}
	
	/**
	 * Checks the finder method with relative path
	 */
	@Test
	public void testFinderRelative() {
		Main m = new Main();
		String testDirectory = AllTests.getBaseDir();
		File[] javaFiles = m.finder(testDirectory);
		assertNotNull(javaFiles);
	}
	
	/**
	 * Checks the finder method with absolute path
	 */
	@Test
	public void testFinderAbsolute() {
		Main m = new Main();
		String testDirectory = AllTests.getBaseDir();
		File file = new File(testDirectory);
		File[] javaFiles = m.finder(file.getAbsolutePath());
		assertNotNull(javaFiles);
	}
	
	/**
	 * Checks the finder method with invalid path
	 */
	@Test
	public void testFinderInvalid() {
		Main m = new Main();
		String testDirectory = "-1";
		File[] javaFiles = m.finder(testDirectory);
		assertNull(javaFiles);
	}
	
	/**
	 * Checks the finder method with null path
	 */
	@Test(expected = NullPointerException.class)
	public void testFinderNull() {
		Main m = new Main();
		String testDirectory = null;
		File[] javaFiles = m.finder(testDirectory);
		assertNull(javaFiles);
	}
	
	/**
	 * Checks the readFiletoString method with relative path
	 */
	@Test(expected = IOException.class)
	public void testReadFileToStringRelative() throws IOException {
		Main m = new Main();
		String testDirectory = AllTests.getBaseDir();
		String testFile = testDirectory + "\\something.java";
		char[] content = m.readFiletoString(testFile);
		assertNotNull(content);
	}
	
	/**
	 * Checks the readFiletoString method with absolute path
	 */
	@Test(expected = IOException.class)
	public void testReadFileToStringAbsolute() throws IOException {
		Main m = new Main();
		String testDirectory = AllTests.getBaseDir();
		String testFile = testDirectory + "\\something.java";
		File file = new File(testFile);
		char[] content = m.readFiletoString(file.getAbsolutePath());
		assertNotNull(content);
	}
	
	/**
	 * Checks the readFiletoString method with null path
	 */
	@Test(expected = NullPointerException.class)
	public void testReadFileToStringNull() throws IOException {
		Main m = new Main();
		String testFile = null;
		char[] content = m.readFiletoString(testFile);
		assertNotNull(content);
	}
	
	/**
	 * Checks the readFiletoString method with invalid path
	 */
	@Test(expected = IOException.class)
	public void testReadFileToStringInvalid() throws IOException {
		Main m = new Main();
		String testFile = "-1";
		char[] content = m.readFiletoString(testFile);
		assertNotNull(content);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse() {
		Main m = new Main();
		Main.type = "java.lang.String";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		System.out.println("java.lang.String");
		System.out.println(output[0]);
		System.out.println(output[1]);
		assertEquals(output[0], 0);
		assertEquals(output[1], 1);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse2() {
		Main m = new Main();
		Main.type = "DoesItWork";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		assertEquals(output[0], 1);
		assertEquals(output[1], 0);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse3() {
		Main m = new Main();
		Main.type = "DoesItWork.MaybeWorks";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		assertEquals(output[0], 1);
		assertEquals(output[1], 0);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse4() {
		Main m = new Main();
		Main.type = "int";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		assertEquals(output[0], 0);
		assertEquals(output[1], 2);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse5() {
		Main m = new Main();
		Main.type = "char";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		System.out.println(output[0]);
		System.out.println(output[1]);
		assertEquals(output[0], 0);
		assertEquals(output[1], 1);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse6() {
		Main m = new Main();
		Main.type = "Time";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		System.out.println("Time");
		System.out.println(output[0]);
		System.out.println(output[1]);
		assertEquals(output[0], 0);
		assertEquals(output[1], 1);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse7() {
		Main m = new Main();
		Main.type = "DoesItWork.Quark";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		System.out.println("DoesItWork.Quark");
		System.out.println(output[0]);
		System.out.println(output[1]);
		assertEquals(output[0], 1);
		assertEquals(output[1], 0);
	}
	
	/**
	 * Checks the parse method with valid inputs
	 */
	@Test
	public void testParse8() {
		Main m = new Main();
		Main.type = "PleaseWork";
		String source = "public class DoesItWork{\n"
				+ "private class MaybeWorks{} \n"
				+ "public void add(){ 1 + 1; }"
				+ "public void add2(){ add() }"
				+ "String a;"
				+ "a = \"Hello\";"
				+ "int b;"
				+ "char c;"
				+ "int d;"
				+ "Time e;"
				+ "enum Quark{ UP, DOWN}"
				+ "}\n"
				+ "interface PleaseWork{}\n";;
		char[] sourceChar = source.toCharArray();
		String unitName = "DoesItWork.java";
		int[] output = m.parse(sourceChar, unitName);
		assertEquals(output[0], 1);
		assertEquals(output[1], 0);
	}
	
	/**
	 * IntegrationTest
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void integrationTest() throws FileNotFoundException, IOException {
		String[] testInput = new String[2];
		File file = new File(AllTests.getBaseDir());
		testInput[0] = file.getAbsolutePath();
		testInput[1] = "java.lang.String";
		Main.main(testInput);
	}
}
