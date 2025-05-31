package com.amigoscode.mockdata;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.amigoscode.beans.Car;
import com.amigoscode.beans.Person;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class MockData {

    public static List<Person> getPeople() throws IOException {
        InputStream inputStream = Resources.getResource("people.json").openStream();
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        Type listType = new TypeToken<ArrayList<Person>>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }

    public static List<Car> getCars() throws IOException {
        InputStream inputStream = Resources.getResource("cars.json").openStream();
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        Type listType = new TypeToken<ArrayList<Car>>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }

    public <T> T fromJson(final String json, final Class<T> clazz) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, false); 
		return mapper.readValue(json, clazz);
	}
	
	public static <T> List<T> constructCollectionType(String json, 
			Class<? extends Collection> collectionClass, Class<T> clazzB) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(collectionClass, clazzB));
	}
	
	 // Method to read JSON from file and convert to List<Person>
    public static List<Person> readJsonFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Use TypeReference to specify the target type (List<Person>)
        TypeReference<List<Person>> typeReference = new TypeReference<List<Person>>() {};
        
        // Read and parse the JSON file
        return objectMapper.readValue(new File(filePath), typeReference);
    }
    
    // Alternative method: Read from JSON string directly
    public static List<Person> readJsonFromString(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Person>> typeReference = new TypeReference<List<Person>>() {};
        
        return objectMapper.readValue(jsonString, typeReference);
    }
    
    // Method to read from classpath resource
    public static List<Person> readJsonFromResource(String resourcePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Person>> typeReference = new TypeReference<List<Person>>() {};
        
        // Read from classpath
        return objectMapper.readValue(
        		MockData.class.getClassLoader().getResourceAsStream(resourcePath), 
            typeReference
        );
    }

    public static void main(String[] args) {
        try {
            // Method 1: Read from file path
            //List<Person> people = readJsonFromResource("people.json");
        	
        	List<Person> responseList = constructCollectionType("", List.class, Person.class);
            
            // Print the results
//            System.out.println("Parsed " + people.size() + " people:");
//            for (Person person : people) {
//                System.out.println(person);
//            }
            
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
    }
}
