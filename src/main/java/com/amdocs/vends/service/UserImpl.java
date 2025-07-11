package com.amdocs.vends.service;

import com.amdocs.vends.bean.Property;
import com.amdocs.vends.interfaces.UserIntf;
import com.amdocs.vends.utils.enums.PropertyType;
import com.amdocs.vends.utils.singleton.LoggedInUser;

import java.util.Scanner;

public class UserImpl implements UserIntf {

    Scanner scanner = new Scanner(System.in);

    public void showAdminHomepage() {
        PropertyImpl propertyService = new PropertyImpl();
        System.out.println("Welcome, Mr. " + LoggedInUser.getName());
        System.out.println();
        do {
            System.out.println("1. Add Property");
            System.out.println("2. Add Tenant");
            System.out.println("3. Show Properties and Tenants");
            System.out.println("4. Payment Approval Requests");
            System.out.println("5. Leave Approval Requests");
            System.out.println("6. Logout");
            System.out.print("Enter your choice between number 1 to 6: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Integer userId = LoggedInUser.getUserId();
                    String propertyType;
                    String address;
                    String city;
                    String state;
                    String status = "active";
                    System.out.println("Select Property Type:");
                    System.out.println("1. Villa    2. Flat    3. Plotted House");
                    System.out.print("Enter choice between 1-3: ");
                    int propertyTypeChoice = scanner.nextInt();
                    if (propertyTypeChoice == 1) {
                        propertyType = PropertyType.VILLA.getValue();
                    } else if (propertyTypeChoice == 2) {
                        propertyType = PropertyType.FLAT.getValue();
                    } else if (propertyTypeChoice == 3) {
                        propertyType = PropertyType.PLOTTED_HOUSE.getValue();
                    } else {
                        System.out.println("Wrong choice!");
                        break;
                    }
                    System.out.println("Enter Address: ");
                    scanner.nextLine();
                    address = scanner.nextLine();
                    System.out.println("Enter City: ");
                    city = scanner.nextLine();
                    System.out.println("Enter State: ");
                    state = scanner.nextLine();
                    Property property = new Property(userId, propertyType, address, city, state, status);
                    propertyService.addProperty(property);
                    System.out.println("Added property successfully!");
                    break;
                default: System.exit(0);
            }
        } while (true);
    }
}
