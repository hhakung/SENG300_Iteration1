package source;

/**
 * Class extends to ASTVisitor in which we call to be able to 
 * traverse the AST tree and retrieve information on the ASTNodes. 
 */
import java.util.Iterator;
import org.eclipse.jdt.core.dom.*;

public class TypeVisitor extends ASTVisitor {
	
	private int declarationCounter = 0;
	private int referenceCounter = 0;
	private IVariableBinding matchingBinding = null;
	
	public int getDeclarationCounter() {
		return declarationCounter;
	}
	
	public int getReferenceCounter() {
		return referenceCounter;
	}
	
	public boolean visit(VariableDeclarationStatement node) {
		//System.out.println("In visit method");
		for (Iterator<?> iter = node.fragments().iterator(); iter.hasNext();) {
		
			//Identifying the number of declarations made
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) iter.next();
			IVariableBinding binding = fragment.resolveBinding();
			
			//System.out.println("binding: " +binding);
			
			//The following lines would be used to get the type of each declaration that was identified
			String typeFound = binding.getVariableDeclaration().toString();
			//System.out.println("typeFound: " +typeFound);
			
			String[] typeSplit = typeFound.split(" "); //Simply get the type that was declared 
			if (typeSplit[0].equals(Main.type))
			{
				this.declarationCounter++;
				matchingBinding = binding;
			}
		}
		
		//System.out.println("# of Declarations: "+declarationCounter);
		return true;
	}
	
	public boolean visit(SimpleName node) {
		// while matchingBinding is null, don't do anything; 
		// this means that we haven't yet to find the variable declaration of the type.
		// Otherwise, check if the binding of the declaration and the binding of reference are the same.
		// If they are, add 1 to the referenceCounter.
		if (matchingBinding != null) {
			if(matchingBinding.equals(node.resolveBinding())) {
				referenceCounter++;
			}
		}
		
		//System.out.println("# of References: "+referenceCounter);
		return true;
	}
}

