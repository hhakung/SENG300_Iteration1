package source;

/**
 * Class extends to ASTVisitor in which we call to be able to 
 * traverse the AST tree and retrieve information on the ASTNodes. 
 */
import java.util.Iterator;
import org.eclipse.jdt.core.dom.*;

public class TypeVisitor extends ASTVisitor {
	
	int declarationCounter = 0;
	
	public boolean visit(VariableDeclarationStatement node) {
		System.out.println("In visit method");
		for (Iterator<?> iter = node.fragments().iterator(); iter.hasNext();) {
		
			//Identifying the number of declarations made
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) iter.next();
			IVariableBinding binding = fragment.resolveBinding();
			declarationCounter++;
			
			System.out.println("binding: " +binding);
			
			//The following lines would be used to get the type of each declaration that was identified
			//String typeFound = binding.getVariableDeclaration().toString();
			//String typeSplit = typeFound.split(" "); //Simply get the type that was declared 
	
		}
		System.out.println("# of Declarations: "+declarationCounter);
		return true;
	}
/*	
	//Checks on the references  
	public boolean visitNode(SimpleName node){
		if (binding.equals(simpleName.resolveBinding())) {};
		
		return;
	}
*/
}

