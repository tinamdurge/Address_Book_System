package com.example;

public class Contact {
    String first_name;
    String last_name;
    String address;
    String city;
    String state;
    int zip;
    String phone_number;
    String email;

    public Contact(String first_name, String last_name, String address, String city, String state, int zip,
            String phone_number, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone_number = phone_number;
        this.email = email;
    }

    // UC7: method to check duplicate contact
    public static boolean equals(String current, Contact contact) {
        String existing = contact.first_name.toLowerCase().trim() + " " + contact.last_name.toLowerCase().trim();
        return current.equals(existing);
    }

    public String toCSVString() {
        return this.first_name + ","
                + this.last_name + ","
                + this.phone_number + ","
                + this.address + ","
                + this.city + ","
                + this.state + ","
                + this.zip + ","
                + this.email;
    }

    @Override
    public String toString() {
        return "First Name: " + this.first_name
                + "\nLast Name: " + this.last_name
                + "\nPhone Number: " + this.phone_number
                + "\nAddress: " + this.address
                + "\nCity: " + this.city
                + "\nState: " + this.state
                + "\nZip code: " + this.zip
                + "\nEmail: " + this.email;
    }
}
