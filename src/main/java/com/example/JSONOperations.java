package com.example;

import java.nio.file.Path;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class JSONOperations {

    // method to read and store data in LinkedList
    public static ArrayList<Contact> readFromFile(String filePath) {
        if (checkIfExists(filePath)) {
            ArrayList<Contact> contactList = new ArrayList<>();
            Gson gson = new Gson();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                StringBuilder jsonData = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonData.append(line);
                }

                // Parse the content inside square brackets as a JSON array
                Type contactListType = new TypeToken<ArrayList<Contact>>() {
                }.getType();
                contactList = gson.fromJson(jsonData.toString(), contactListType);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return contactList;
        }
        System.out.println("File " + filePath + " does not exist");
        return null;
    }

    // method to write into a file
    public static void writeToFile(String filePath, Contact contact) {
        if (checkIfExists(filePath)) {
            ArrayList<Contact> contactList = readFromFile(filePath);
            Gson gson = new Gson();
            if (contactList == null) {
                contactList = new ArrayList<>();
            }
            contactList.add(contact);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                String jsonContactList = gson.toJson(contactList);
                writer.write(jsonContactList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File " + filePath + " does not exist");
        }
    }

    // method to create a file
    public static void createFile(String filePath) {
        if (!checkIfExists(filePath)) {
            Path path = Path.of(filePath);
            try {
                Files.createFile(path);
                System.out.println("Created file: " + filePath);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        } else {
            System.out.println("File " + filePath + " already exists.");
        }
    }

    // method to check if file exists or not
    public static boolean checkIfExists(String filePath) {
        Path path = Path.of(filePath);
        return Files.exists(path);
    }
}
