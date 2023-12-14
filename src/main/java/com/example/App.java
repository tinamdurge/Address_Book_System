package com.example;

import java.util.ArrayList;
import java.util.Scanner;

import com.exceptions.*;

public class App {
    public static void main(String[] args)
            throws InvalidFirstNameException, InvalidLastNameException, InvalidEmailException,
            InvalidPhoneNumberException {
        System.out.println("Welcome to Address Book Program");
        App app = new App();

        // UC6: creating address book manager to handle multiple address books
        AddressBooksManager manager = new AddressBooksManager("addressbooks");
        app.takeConsoleInputs(manager);
    }

    public void takeConsoleInputs(AddressBooksManager manager)
            throws InvalidFirstNameException, InvalidLastNameException, InvalidEmailException,
            InvalidPhoneNumberException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nFollowing functions can be executed:");
            System.out.println("[1] Create New Address Book");
            System.out.println("[2] Access Address Book"); // TODO: use file I/O
            System.out.println("[3] View all Address Books"); // TODO: for JSON
            System.out.println("[4] Search contacts by City");
            System.out.println("[5] Search contacts by State");
            System.out.println("[6] Count contacts by City");
            System.out.println("[7] Count contacts by State");
            System.out.print("Enter your choice (Enter 0 to exit): ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0:
                    sc.close();
                    return;

                case 1:
                    System.out.print("Enter Address Book Name: ");
                    String name = sc.nextLine();
                    manager.createBook(name);
                    System.out.println("Address Book created successfully.");
                    break;

                case 2:
                    System.out.print("Enter Address Book's Name: ");
                    String search_name = sc.nextLine();
                    if (manager.getBookbyName(search_name) != null) {
                        System.out.println("\nAccessing Address Book " + search_name);
                        manager.accessBook(manager.getBookbyName(search_name), sc);
                    } else {
                        System.out.println("No Address Book found with this name.");
                    }
                    break;

                case 3:
                    System.out.println("Following are all the books in this manager:");
                    manager.getAllBooks();
                    break;

                case 4:
                    System.out.print("Enter name of City: ");
                    String city = sc.nextLine();
                    ArrayList<Contact> city_contacts = manager.findByCity(city);
                    if (city_contacts.isEmpty()) {
                        System.out.println("No contacts found from " + city);
                    } else {
                        System.out.println("Contacts from " + city + " are:");
                        for (int i = 0; i < city_contacts.size(); i++) {
                            Contact contact = city_contacts.get(i);
                            System.out.println("  " + (i + 1) + ") " + contact.first_name + " " + contact.last_name);
                        }
                    }
                    break;

                case 5:
                    System.out.print("Enter name of State: ");
                    String state = sc.nextLine();
                    ArrayList<Contact> state_contacts = manager.findByState(state);
                    if (state_contacts.isEmpty()) {
                        System.out.println("No contacts found from " + state);
                    } else {
                        System.out.println("Contacts from " + state + " are:");
                        for (int i = 0; i < state_contacts.size(); i++) {
                            Contact contact = state_contacts.get(i);
                            System.out.println("  " + (i + 1) + ") " + contact.first_name + " " + contact.last_name);
                        }
                    }
                    break;

                case 6:
                    System.out.print("Enter name of city: ");
                    String city_search = sc.nextLine();
                    System.out.println(
                            "Number of contacts from " + city_search + " are: " + manager.countInCity(city_search));
                    break;

                case 7:
                    System.out.print("Enter name of state: ");
                    String state_search = sc.nextLine();
                    System.out.println(
                            "Number of contacts from " + state_search + " are: " + manager.countInState(state_search));
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}