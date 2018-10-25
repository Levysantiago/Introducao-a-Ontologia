package com.ontologia;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.jena.ontology.AllValuesFromRestriction;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.SomeValuesFromRestriction;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

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
	    
	    //Adicionando nova classe
	    OntClass novoRecheio = model.createClass(NAMESPACE + "Cheddar");
	    OntClass queijo = model.createClass(NAMESPACE + "Queijo");
	    novoRecheio.setSuperClass(queijo);
	    
	    //Adicionando um novo Indivíduo
	    Resource pizza = model.createResource(NAMESPACE + "Pizza");
	    Individual novaPizza = model.createIndividual(NAMESPACE + "novaPizza", pizza);
	    Property hasBase = model.getProperty(NAMESPACE + "hasBase");
	    Property hasRecheio = model.getProperty(NAMESPACE + "hasRecheio");
	    OntClass baseFina = model.getOntClass(NAMESPACE + "BaseFina");
	    OntClass parmesao = model.getOntClass(NAMESPACE + "Parmesao");
	    
	    //Inserindo restrições do indivíduo
	    SomeValuesFromRestriction temBaseFina = model.createSomeValuesFromRestriction(null, hasBase, baseFina);
	    novaPizza.addRDFType(temBaseFina);
	    SomeValuesFromRestriction temRecheioParmesao = model.createSomeValuesFromRestriction(null, hasRecheio, parmesao);
	    novaPizza.addRDFType(temRecheioParmesao);
	    
	    //Obtendo tipos do indivíduo
	    System.out.println("\nTipos da Nova Pizza:");
	    ExtendedIterator<OntClass> classes = novaPizza.listOntClasses(false);
	    while(classes.hasNext()) {
	    	String name = classes.next().getLocalName();
	    	if(name != null) {
	    		System.out.println(name);	    		
	    	}
	    }
	    
	    //Escrevendo mudanças
	    model.write(outputStreamWriter);
	    System.out.println("\nDados salvos com sucesso!");
		
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
