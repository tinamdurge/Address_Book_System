package com.example;

import java.nio.file.Path;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class CSVOperations {
    // method to read and store data in LinkedList
    public static ArrayList<Contact> readFromFile(String filePath) {
        if (checkIfExists(filePath)) {
            ArrayList<Contact> data = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 8) {
                        String first_name = parts[0];
                        String last_name = parts[1];
                        String phone_number = parts[2];
                        String address = parts[3];
                        String city = parts[4];
                        String state = parts[5];
                        int zip = Integer.parseInt(parts[6]);
                        String email = parts[7];
                        data.add(new Contact(first_name, last_name, address, city, state, zip, phone_number, email));
                    }
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
            return data;
        }
        System.out.println("File " + filePath + " does not exist");
        return null;
    }

    // method to write data to file
    public static void writeToFile(String filePath, Contact contact) {
        if (checkIfExists(filePath)) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
                writer.println(contact.toCSVString());
                writer.println();
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        } else {
            System.out.println("File " + filePath + " does not exist");
        }
    }

    // method to delete data from file
    public static void deleteFromFile(String filePath, String delete_name) {
        if (checkIfExists(filePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(delete_name)) {
                        // TODO: something to delete
                    }
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
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

    // method to create a directory
    public static void createDirectory(String dirPath) {
        if (!checkIfExists(dirPath)) {
            Path path = Path.of(dirPath);
            try {
                Files.createDirectory(path);
                System.out.println("Created directory: " + dirPath);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }

        } else {
            System.out.println("Directory " + dirPath + " already exists.");
        }
    }

    // method to list all files from a directory
    public static void listFiles(String dirPath) {
        Path path = Path.of(dirPath);
        if (checkIfExists(dirPath)) {
            if (Files.isDirectory(path)) {
                try {
                    Files.list(path).forEach(System.out::println);
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                    exception.printStackTrace();
                }
            } else {
                System.out.println(dirPath + " is not a directory.");
            }
        } else {
            System.out.println("Directory " + dirPath + " does not exist.");
        }
    }

    // method to check if file exists or not
    public static boolean checkIfExists(String filePath) {
        Path path = Path.of(filePath);
        return Files.exists(path);
    }
}
