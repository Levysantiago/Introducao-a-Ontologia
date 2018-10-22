package com.ontologia;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

public class Leitura {
	
	//Namespace da ontologia
	public static final String NAMESPACE = "http://www.semanticweb.org/levy/ontologies/2018/9/pizza-test#";
	//Caminho do arquivo
	private static String filePath = "/home/levy/Documents/Protege-5.2.0/projects/pizza.owl";
    
	public static void main(String[] args) {
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		//Criando um modelo para inferência
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
		
		//Abrindo o arquivo como leitura
        inputStream = FileManager.get().open(filePath);
        inputStreamReader = new InputStreamReader(inputStream);
        
        //Transformando o arquivo em modelo de ontologia
        model.read(inputStreamReader, "RDF/XML");
        
        System.out.println("\nSubclasses de Pizza:");
        //Obtendo classe Pizza
        OntClass pizza = model.getOntClass(NAMESPACE + "Pizza");
        //Listando todas as subclasses de Pizza
        ExtendedIterator<OntClass> subclasses = pizza.listSubClasses(true);
        while(subclasses.hasNext()) {
        	String sub = subclasses.next().getLocalName();
        	if(sub != null) {
        		System.out.println(sub);
        	}
        }
        
        System.out.println("\nPizzas de queijo: ");
        //Obtendo classe Pizza
        OntClass cheesyPizza = model.getOntClass(NAMESPACE + "CheesyPizza");
        //Listando todas as subclasses de Pizza
        subclasses = cheesyPizza.listSubClasses(false);
        while(subclasses.hasNext()) {
        	String sub = subclasses.next().getLocalName();
        	if(sub != null) {
        		System.out.println(sub);
        	}
        }
        
        //Fechando os streams de arquivo
        try {
        	inputStreamReader.close();
        	inputStream.close();
	        inputStream = null;
	        inputStreamReader = null;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
