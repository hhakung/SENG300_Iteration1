package source;

/**
 * Class extends to ASTVisitor in which we call to be able to 
 * traverse the AST tree and retrieve information on the ASTNodes. 
 */
import java.util.Iterator;
import org.eclipse.jdt.core.dom.*;

public class TypeVisitor extends ASTVisitor {
	
	private int declarationCounter = 0; // count of only the type declarations
	private int referenceCounter = 0; // count of all the identifiers except for the type declarations
	
	public int getDeclarationCounter() {
		return declarationCounter;
	}
	
	public int getReferenceCounter() {
		return referenceCounter;
	}
	
	/**
	 * Overriding the visit function; counts how many of type declarations there are.
	 * @param node TypeDeclaration node to visit
	 * @return boolean to indicate whether to continue visiting the nodes of TypeDeclaration in the tree
	 */
	public boolean visit(TypeDeclaration node) {
		ITypeBinding binding = node.resolveBinding();
		String typeFound = binding.getQualifiedName();
		
		if (typeFound.equals(Main.type)) {
			this.declarationCounter++;
		}
		
		return true;
	}
	
	/**
	 * Overriding the visit function; counts how many of type references there are.
	 * @param node SimpleName node to visit
	 * @return boolean to indicate whether to continue visiting the nodes of SimpleName in the tree
	 */
	public boolean visit(SimpleName node) {
		ITypeBinding binding = node.resolveTypeBinding();
		if (binding != null) {
			
			// take care of both the qualified name and the simple name
			if (binding.getQualifiedName().equals(Main.type) || binding.getName().equals(Main.type))
			{
				if (!node.isDeclaration()) {
					// if the node is not a declaration, add 1 to referenceCounter
					referenceCounter++;
				}
				else if (binding.isPrimitive()) {
					// if the node is a declaration, but if it is primitive, it is NOT java type declaration.
					// add 1 to referenceCounter
					referenceCounter++;
				}
			}
		}

		return true;
	}
}

