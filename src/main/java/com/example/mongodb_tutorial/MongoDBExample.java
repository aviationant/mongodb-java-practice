package com.example.mongodb_tutorial;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBExample {
    public static void main(String[] args) {

        String user = System.getenv("MONGO_USER");
        String pass = System.getenv("MONGO_PASS");

        if (user == null || pass == null) {
            System.err.println("Error: Environment variables MONGO_USER and MONGO_PASS must exist.");
            System.exit(1);
        }

        String connectionString = "mongodb+srv://" + user + ":" + pass + "@cluster0.84ihbp7.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("test");
            MongoCollection<Document> collection = database.getCollection("users");

            Document doc = new Document("name", "Phil Donahue").append("age", 30).append("hair", "brown");
            collection.insertOne(doc);
            System.out.println("Document inserted");

            FindIterable<Document> documents = collection.find();
            System.out.println("All documents:");
            for (Document d : documents) {
                System.out.println(d.toJson());
            }

            Document query = new Document("name", "John Doe");
            Document update = new Document("$set", new Document("age", 31));
            collection.updateOne(query, update);
            System.out.println("Document updated");

            //collection.deleteOne(query);
            //System.out.println("Document deleted");

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}