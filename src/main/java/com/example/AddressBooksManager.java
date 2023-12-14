package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.exceptions.*;
import com.validator.*;

// UC6: Address books manager to handle multiple address books
public class AddressBooksManager {
    HashMap<String, AddressBook> addressBooks;

    // UC9: store and save contacts by city and state
    private HashMap<String, ArrayList<Contact>> cityContacts;
    private HashMap<String, ArrayList<Contact>> stateContacts;

    // UC13: directory address to store files
    private String directoryPath;

    public AddressBooksManager(String dirPath) {
        this.addressBooks = new HashMap<String, AddressBook>();
        this.cityContacts = new HashMap<>();
        this.stateContacts = new HashMap<>();
        this.directoryPath = dirPath;

        // creating new directory to store addressbooks
        CSVOperations.createDirectory(this.directoryPath);
    }

    public String getDirectory() {
        return this.directoryPath;
    }

    // UC10: get number of contacts from city
    public int countInCity(String city) {
        ArrayList<Contact> contacts;
        if (cityContacts.containsKey(city)) {
            contacts = cityContacts.get(city);
        } else {
            contacts = new ArrayList<>();
        }
        return contacts.size();
    }

    // UC10: get number of contacts from state
    public int countInState(String state) {
        ArrayList<Contact> contacts;
        if (stateContacts.containsKey(state)) {
            contacts = stateContacts.get(state);
        } else {
            contacts = new ArrayList<>();
        }
        return contacts.size();
    }

    // method to update city contacts
    public void updateCityContacts(String city, Contact contact) {
        ArrayList<Contact> contacts;
        if (cityContacts.containsKey(city)) {
            contacts = cityContacts.get(city);
        } else {
            contacts = new ArrayList<>();
        }
        contacts.add(contact);
        cityContacts.put(city, contacts);
    }

    // method to update state contacts
    public void updateStateContacts(String state, Contact contact) {
        ArrayList<Contact> contacts;
        if (stateContacts.containsKey(state)) {
            contacts = stateContacts.get(state);
        } else {
            contacts = new ArrayList<>();
        }
        contacts.add(contact);
        stateContacts.put(state, contacts);
    }

    // UC8: method to search across all addressbooks with same city
    public ArrayList<Contact> findByCity(String city) {
        return this.cityContacts.containsKey(city) ? this.cityContacts.get(city) : new ArrayList<Contact>();
    }

    // UC8: method to search across all addressbooks with same state
    public ArrayList<Contact> findByState(String state) {
        return this.stateContacts.containsKey(state) ? this.stateContacts.get(state) : new ArrayList<Contact>();
    }

    public AddressBook getBookbyName(String name) {
        return addressBooks.containsKey(name) ? addressBooks.get(name) : null;
    }

    public void getAllBooks() {
        CSVOperations.listFiles(this.directoryPath);
    }

    public void createBook(String name) {
        addressBooks.put(name, new AddressBook(name, this.directoryPath));
    }

    public void accessBook(AddressBook book, Scanner sc) {
        while (true) {
            System.out.println("Which function would you like to execute?");
            System.out.println("[1] Add New Contact");
            System.out.println("[2] Edit Existing Contact"); // TODO: use file I/O
            System.out.println("[3] Delete Existing Contact"); // TODO: use file I/O
            System.out.println("[4] Print Address Book");
            System.out.println("[5] Print Address Book sorted by Name");
            System.out.println("[6] Print Address Book sorted by City");
            System.out.println("[7] Print Address Book sorted by State");
            System.out.println("[8] Print Address Book sorted by Zip");
            System.out.println("[9] Print Contacts added between Dates");
            System.out.println("[10] Count Contacts by City");
            System.out.println("[11] Count Contacts by State");
            System.out.print("Enter your choice (Enter 0 to exit): ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0:
                    return;

                // UC2: adding contact from console
                case 1:
                    System.out.print("Enter First Name: ");
                    String first_name;
                    while (true) {
                        try {
                            first_name = sc.nextLine();
                            Validator.validateFirstName(first_name);
                            break;
                        } catch (InvalidFirstNameException exception) {
                            System.out.println("\n" + exception.getMessage());
                            System.out.print("Enter First Name: ");
                        }
                    }

                    if (book.hasDuplicate(first_name)) {
                        System.out.println("This contact already exists. You can choose to edit.\n");
                        break;
                    }

                    System.out.print("Enter Last Name: ");
                    String last_name;
                    while (true) {
                        try {
                            last_name = sc.nextLine();
                            Validator.validateLastName(last_name);
                            break;
                        } catch (InvalidLastNameException exception) {
                            System.out.println("\n" + exception.getMessage());
                            System.out.print("Enter Last Name: ");
                        }
                    }

                    System.out.print("Enter Phone Number: ");
                    String phone_number;
                    while (true) {
                        try {
                            phone_number = sc.nextLine();
                            Validator.validatePhoneNumber(phone_number);
                            break;
                        } catch (InvalidPhoneNumberException exception) {
                            System.out.println("\n" + exception.getMessage());
                            System.out.print("Enter Phone Number: ");
                        }
                    }

                    System.out.print("Enter Address: ");
                    String address = sc.nextLine();

                    System.out.print("Enter City: ");
                    String city = sc.nextLine();

                    System.out.print("Enter State: ");
                    String state = sc.nextLine();

                    System.out.print("Enter Zip Code: ");
                    int zip = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email;
                    while (true) {
                        try {
                            email = sc.nextLine();
                            Validator.validateEmail(email);
                            break;
                        } catch (InvalidEmailException exception) {
                            System.out.println("\n" + exception.getMessage());
                            System.out.print("Enter Email: ");
                        }
                    }

                    System.out.print("Enter Type of Contact: ");
                    String type = sc.nextLine();

                    // adding new contact in the address book
                    book.addContact(first_name, last_name, address, city, state, zip, phone_number, email, type);

                    // UC9: update city and state contacts
                    this.updateCityContacts(city,
                            new Contact(first_name, last_name, address, city, state, zip, phone_number, email));
                    this.updateStateContacts(state,
                            new Contact(first_name, last_name, address, city, state, zip, phone_number, email));

                    System.out.println("Contact added successfully!\n");
                    break;

                // UC3: edit the contact details using contact's first name
                case 2:
                    System.out.print("Enter first name of contact you wish to edit: ");
                    String search_first_name = sc.nextLine().toLowerCase().trim();

                    System.out.print("Enter last name of contact you wish to edit: ");
                    String search_last_name = sc.nextLine().toLowerCase().trim();

                    if (DBOperations.contactExists(search_first_name, search_last_name)) {
                        int person_id = DBOperations.getPersonID(search_first_name, search_last_name);
                        int address_id = DBOperations.getAddressID(search_first_name, search_last_name);
                        System.out.println("\n\tFollowing fields can be edited for this contact:");
                        System.out.println("\t[1] First Name");
                        System.out.println("\t[2] Last Name");
                        System.out.println("\t[3] Phone Number");
                        System.out.println("\t[4] Address");
                        System.out.println("\t[5] City");
                        System.out.println("\t[6] State");
                        System.out.println("\t[7] Zip");
                        System.out.println("\t[8] Email");
                        System.out.print("\tEnter field you wish to edit (enter 0 to exit): ");

                        int edit_choice = sc.nextInt();
                        sc.nextLine();

                        switch (edit_choice) {
                            case 0:
                                break;

                            case 1:
                                System.out.print("\n\tEnter New First Name: ");
                                String contact_first_name;
                                while (true) {
                                    try {
                                        contact_first_name = sc.nextLine();
                                        Validator.validateFirstName(contact_first_name);
                                        break;
                                    } catch (InvalidFirstNameException exception) {
                                        System.out.println("\n\t" + exception.getMessage());
                                        System.out.print("\n\tEnter New First Name: ");
                                    }
                                }
                                DBOperations.updatePersonDetail("first_name", contact_first_name, person_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            case 2:
                                System.out.print("\n\tEnter New Last Name: ");
                                String contact_last_name;
                                while (true) {
                                    try {
                                        contact_last_name = sc.nextLine();
                                        Validator.validateLastName(contact_last_name);
                                        break;
                                    } catch (InvalidLastNameException exception) {
                                        System.out.println("\n\t" + exception.getMessage());
                                        System.out.print("\n\tEnter New Last Name: ");
                                    }
                                }
                                DBOperations.updatePersonDetail("last_name", contact_last_name, person_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            case 3:
                                System.out.print("\n\tEnter New Phone Number: ");
                                String contact_phone_number;
                                while (true) {
                                    try {
                                        contact_phone_number = sc.nextLine();
                                        Validator.validatePhoneNumber(contact_phone_number);
                                        break;
                                    } catch (InvalidPhoneNumberException exception) {
                                        System.out.println("\n\t" + exception.getMessage());
                                        System.out.print("\n\tEnter New Phone Number: ");
                                    }
                                }
                                DBOperations.updatePersonDetail("phone", contact_phone_number, person_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            case 4:
                                System.out.print("\n\tEnter New Address: ");
                                String contact_address = sc.nextLine();
                                DBOperations.updateAddressDetail("address", contact_address, address_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            case 5:
                                System.out.print("\n\tEnter New City: ");
                                String contact_city = sc.nextLine();
                                DBOperations.updateAddressDetail("city", contact_city, address_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            case 6:
                                System.out.print("\n\tEnter New State: ");
                                String contact_state = sc.nextLine();
                                DBOperations.updateAddressDetail("state", contact_state, address_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            case 7:
                                System.out.print("\n\tEnter New Zip Code: ");
                                int contact_zip = sc.nextInt();
                                sc.nextLine();
                                DBOperations.updateAddressDetail("zip", Integer.toString(contact_zip), address_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            case 8:
                                System.out.print("\n\tEnter New Email: ");
                                String contact_email;
                                while (true) {
                                    try {
                                        contact_email = sc.nextLine();
                                        Validator.validateEmail(contact_email);
                                        break;
                                    } catch (InvalidEmailException exception) {
                                        System.out.println("\n\t" + exception.getMessage());
                                        System.out.print("\n\tEnter New Email: ");
                                    }
                                }
                                DBOperations.updatePersonDetail("email", contact_email, person_id);
                                System.out.println("Contact Edited Successfully!\n");
                                break;

                            default:
                                System.out.println("Invalid choice.");
                                break;
                        }
                    } else {
                        System.out.println("There is no contact having this first name.\n");
                    }
                    break;

                // UC4: delete existing contact using contact's first name
                case 3:
                    System.out.print("Enter first name of contact you wish to delete: ");
                    String delete_name = sc.nextLine().trim();

                    if (book.addressbook.containsKey(delete_name)) {
                        book.addressbook.remove(delete_name);
                        System.out.println("Contact Deleted Successfully!\n");
                    } else {
                        System.out.println("There is no contact having this first name.\n");
                    }
                    break;

                case 4:
                    ArrayList<Contact> contacts = book.getAddressBook();
                    if (contacts.isEmpty()) {
                        System.out.println("\nAddress book is empty.\n");
                    } else {
                        System.out.println("\nContacts in this address book are: ");
                        int i = 1;
                        for (Contact contact : contacts) {
                            System.out.println(i + ")\n" + contact + "\n");
                            i++;
                        }
                    }
                    break;

                // UC11: sort address book by name
                case 5:
                    ArrayList<Contact> sortedByName = book.sortByName();
                    if (sortedByName.isEmpty()) {
                        System.out.println("\nAddress book is empty.\n");
                    } else {
                        System.out.println("\nContacts in this address book are: ");
                        int i = 1;
                        for (Contact contact : sortedByName) {
                            System.out.println(i + ")\n" + contact + "\n");
                            i++;
                        }
                    }
                    break;

                // UC12: sort address book by city
                case 6:
                    ArrayList<Contact> sortedByCity = book.sortByCity();
                    if (sortedByCity.isEmpty()) {
                        System.out.println("\nAddress book is empty.\n");
                    } else {
                        System.out.println("\nContacts in this address book are: ");
                        int i = 1;
                        for (Contact contact : sortedByCity) {
                            System.out.println(i + ")\n" + contact + "\n");
                            i++;
                        }
                    }
                    break;

                // UC12: sort address book by state
                case 7:
                    ArrayList<Contact> sortedByState = book.sortByState();
                    if (sortedByState.isEmpty()) {
                        System.out.println("\nAddress book is empty.\n");
                    } else {
                        System.out.println("\nContacts in this address book are: ");
                        int i = 1;
                        for (Contact contact : sortedByState) {
                            System.out.println(i + ")\n" + contact + "\n");
                            i++;
                        }
                    }
                    break;

                // UC12: sort address book by zip
                case 8:
                    ArrayList<Contact> sortedByZip = book.sortByZip();
                    if (sortedByZip.isEmpty()) {
                        System.out.println("\nAddress book is empty.\n");
                    } else {
                        System.out.println("\nContacts in this address book are: ");
                        int i = 1;
                        for (Contact contact : sortedByZip) {
                            System.out.println(i + ")\n" + contact + "\n");
                            i++;
                        }
                    }
                    break;

                // UC18: getting contacts added between dates
                case 9:
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String start_date = sc.nextLine();

                    System.out.print("Enter End Date (YYYY-MM-DD): ");
                    String end_date = sc.nextLine();

                    ArrayList<Contact> addedBetweenDates = book.addedBetweenDates(start_date, end_date);
                    if (addedBetweenDates.isEmpty()) {
                        System.out.println("\nNo contacts added between given dates.\n");
                    } else {
                        System.out.println("\nContacts added between " + start_date + " and " + end_date + " are:");
                        int i = 1;
                        for (Contact contact : addedBetweenDates) {
                            System.out.println(i + ")\n" + contact + "\n");
                            i++;
                        }
                    }
                    break;

                case 10:
                    System.out.print("Enter name of the City: ");
                    String search_city = sc.nextLine();
                    int city_count = book.counContactsInCity(search_city);
                    if (city_count == 0) {
                        System.out.println("\nNo contacts found in " + search_city + ".\n");
                    } else {
                        System.out.println("\n" + city_count + " contacts found in city " + search_city + ".\n");
                    }
                    break;

                case 11:
                    System.out.print("Enter name of the State: ");
                    String search_state = sc.nextLine();
                    int state_count = book.counContactsInState(search_state);
                    if (state_count == 0) {
                        System.out.println("\nNo contacts found in " + search_state + ".\n");
                    } else {
                        System.out.println("\n" + state_count + " contacts found in state " + search_state + ".\n");
                    }
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}