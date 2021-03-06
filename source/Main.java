package source;

/*Program takes in two command line arguments pathname and type. The pathname is simply the directory in which the user wants to be 
 * parsed and the type represents what the user would like to keep track of in respects to the number of declarations and references to 
 * that type. 
 * 
 * Actual functionality in the program:
 * -Takes in all the java files from the directory and creates an AST tree
 * -Parses them 
 * -Is able to identify the total number of type declarations and the total number of type references
 */
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.dom.*;
import java.nio.file.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.io.*;

public class Main {
	private static final IJavaProject A4_V3 = null;
	public static String type;
	public static Path path;

	public static void main (String[] args) throws FileNotFoundException, IOException {
		//Grabbing command line arguments
		path = Paths.get(args[0]);
		//System.out.println("Path: "+ path);

		String textPath = args[0];
		//System.out.println("textPath:"+textPath);

		type = args[1];
		//System.out.println("Type: "+type);

		Main m = new Main();

		//Picks out all the .java files
		File[] files = m.finder(textPath);

		//reads all java files and places contents into charArray
		int totalNumDeclarations = 0;
		int totalNumReferences = 0;
		for (File f: files) {
			int[] result = m.parse(m.readFiletoString(f.toString()), f.getName());
			totalNumDeclarations = totalNumDeclarations + result[0];
			totalNumReferences = totalNumReferences + result[1];
		}
		
		System.out.println(type + ". Declarations found: " + totalNumDeclarations + "; references found: " + totalNumReferences + ".");
	}

	/**
	 * Picks out all the .java files within the list of files from a directory
	 * @param directoryName
	 * @return the .java files by filename
	 */
	public File[] finder(String directoryName) {
		File dir = new File(directoryName);

        return dir.listFiles(new FilenameFilter() {
        	public boolean accept(File dir, String filename) { 
        		return filename.endsWith(".java"); 
        	}
        });
    }
	
	/**
	 * Reads all .java files and returns all their contents within a charArray
	 * @param the file name of the file to have it's contents stored
	 * @return the charArray
	 */
	public char[] readFiletoString (String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		
		reader.close();
		
		return fileData.toString().toCharArray();
	}

	/**
	 * Creates a parser and an AST tree. Also calls for ASTVisitor in order to traverse the tree 
	 * and gather information about the AST nodes. 
	 * @param sourceCode the sourceCode to be parsed into a AST tree
	 * @param unitName the name of the sourceCode file
	 * @return array of int that contains the number of declarations and the number of references for this sourceCode
	 */
	public int[] parse(char[] sourceCode, String unitName) {
		//System.out.println("IN PARSE METHOD");
		ASTParser parser = ASTParser.newParser(AST.JLS8);

		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sourceCode);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setEnvironment(null,  null,  null,  true);
		parser.setUnitName(unitName);
		
		CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		
//		if (cu.getAST().hasResolvedBindings()) {
//		    System.out.println("Binding activated.");
//		}
//		else {
//		    System.out.println("Binding is not activated.");
//		}
		
		TypeVisitor v = new TypeVisitor();
		//System.out.println("created a new instance of TypeVisitor");
		cu.accept(v);
		//System.out.println("finished visiting");
		
		int[] returnArray = new int[2];
		returnArray[0] = v.getDeclarationCounter();
		returnArray[1] = v.getReferenceCounter();
		
		return returnArray;
	}
}