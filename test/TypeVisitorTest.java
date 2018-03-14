package test;

import source.*;
import static org.junit.Assert.*;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.junit.Test;

public class TypeVisitorTest {

	/**
	 * Checks that TypeVisitor can be instantiated
	 */
	@Test
	public void testTypeVisitorInstantiation() {
		TypeVisitor v = new TypeVisitor();
		assertNotNull(v);
	}
	
	/**
	 * Checks that TypeVisitor is a subclass of ASTVisitor
	 */
	@Test
	public void testTypeVisitorIsSubclassOfASTVisitor() {
		TypeVisitor v = new TypeVisitor();
		assertTrue(v instanceof ASTVisitor);
	}
}
