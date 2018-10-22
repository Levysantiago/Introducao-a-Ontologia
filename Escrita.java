package com.ontologia;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class Escrita {
	//Namespace da ontologia
	public static final String NAMESPACE = "http://www.semanticweb.org/levy/ontologies/2018/9/pizza-test#";
	//Caminho do arquivo
	private static String filePath = "/home/levy/Documents/Protege-5.2.0/projects/pizza.owl";
		
	public static void main(String args[]) {
		OutputStream outputStream = null;
	    OutputStreamWriter outputStreamWriter = null;
	    InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
	    OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_RULE_INF);
	    
	    //Abrindo o arquivo como leitura
        inputStream = FileManager.get().open(filePath);
        inputStreamReader = new InputStreamReader(inputStream);
        
        //Transformando o arquivo em modelo de ontologia
        model.read(inputStreamReader, "RDF/XML");
        
        //Fechando os streams de arquivo
        try {
        	inputStreamReader.close();
        	inputStream.close();
	        inputStream = null;
	        inputStreamReader = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
        
	    
		//Abrindo arquivo para escrita
	    try {
			outputStream = new FileOutputStream(filePath);
			outputStreamWriter = new OutputStreamWriter(outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    
	    //Adicionando outra classe
	    OntClass novoRecheio = model.createClass(NAMESPACE + "Cheddar");
	    OntClass queijo = model.createClass(NAMESPACE + "Queijo");
	    novoRecheio.setSuperClass(queijo);
	    
	    //Escrevendo mudanças
	    model.write(outputStreamWriter);
	    System.out.println("Dados salvos com sucesso!");
		
	    //Fechando os streams
	    try {
			outputStream.close();
			outputStreamWriter.close();
	        outputStream = null;
	        outputStreamWriter = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
