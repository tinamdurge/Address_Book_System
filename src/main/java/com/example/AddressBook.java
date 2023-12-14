package com.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

class AddressBook {
    // UC3: used HashMap for efficient searching by name
    String name; // UC6
    HashMap<String, Contact> addressbook;

    // UC13: file path for address book
    private String filePath;

    public AddressBook(String name, String dirPath) {
        this.name = name;
        this.addressbook = new HashMap<String, Contact>();

        // for json operations
        this.filePath = dirPath + "/" + name + "JSON.json";
        JSONOperations.createFile(this.filePath); // creating new file for address book
    }

    // UC12: method to sort entries by city
    public ArrayList<Contact> sortByCity() {
        ArrayList<Contact> contacts = DBOperations.readContacts();
        contacts.sort(Comparator.comparing(contact -> contact.city));
        return contacts;
    }

    // UC12: method to sort entries by state
    public ArrayList<Contact> sortByState() {
        ArrayList<Contact> contacts = DBOperations.readContacts();
        contacts.sort(Comparator.comparing(contact -> contact.state));
        return contacts;
    }

    // UC12: method to sort entries by zip
    public ArrayList<Contact> sortByZip() {
        ArrayList<Contact> contacts = DBOperations.readContacts();
        contacts.sort(Comparator.comparing(contact -> contact.zip));
        return contacts;
    }

    // UC11: method to sort entries by name
    public ArrayList<Contact> sortByName() {
        ArrayList<Contact> contacts = DBOperations.readContacts();
        contacts.sort(Comparator.comparing(contact -> contact.first_name));
        return contacts;
    }

    // UC8: method to get all contacts by city
    public ArrayList<Contact> getAllbyCity(String city) {
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Entry<String, Contact> entry : this.addressbook.entrySet()) {
            if (entry.getValue().city.equals(city)) {
                contacts.add(entry.getValue());
            }
        }
        return contacts;
    }

    // UC8: method to get all contacts by state
    public ArrayList<Contact> getAllbyState(String state) {
        ArrayList<Contact> contacts = new ArrayList<>();
        for (Entry<String, Contact> entry : this.addressbook.entrySet()) {
            if (entry.getValue().state.equals(state)) {
                contacts.add(entry.getValue());
            }
        }
        return contacts;
    }

    // UC7: method to check for duplicate entry
    public boolean hasDuplicate(String first_name) {
        return this.addressbook.containsKey(first_name.toLowerCase().trim());
    }

    // UC1: add new contact function
    public void addContact(String first_name, String last_name, String address, String city, String state, int zip,
            String phone_number, String email, String type) {
        Contact contact = new Contact(first_name, last_name, address, city, state, zip, phone_number, email);
        addressbook.put(first_name.toLowerCase().trim(),
                contact);

        // adding to database
        DBOperations.addContact(contact, this.name, type);
    }

    public ArrayList<Contact> getAddressBook() {
        // for reading from DB
        return DBOperations.readContacts();
    }

    // method to get contacts added between dates
    public ArrayList<Contact> addedBetweenDates(String start_date, String end_date) {
        return DBOperations.addedBetweenDates(start_date, end_date);
    }

    // method to get count of contacts from city
    public int counContactsInCity(String city) {
        HashMap<String, Integer> contactsByCity = DBOperations.countContactsByCity();
        return contactsByCity.containsKey(city) ? contactsByCity.get(city) : 0;
    }

    // method to get count of contacts from city
    public int counContactsInState(String state) {
        HashMap<String, Integer> contactsByState = DBOperations.countContactsByState();
        return contactsByState.containsKey(state) ? contactsByState.get(state) : 0;
    }
}
