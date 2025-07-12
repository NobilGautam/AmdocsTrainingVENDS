package com.amdocs.vends.service;

import java.util.Scanner;
import com.amdocs.vends.bean.Property;
import com.amdocs.vends.bean.Tenant;
import com.amdocs.vends.dao.LeaveRequestDAO;
import com.amdocs.vends.dao.PaymentDAO;
import com.amdocs.vends.dao.PropertyDAO;
import com.amdocs.vends.utils.singleton.LoggedInUser;

public class TenantHomepageMenu {
    private int tenantUserId; // Set this when calling showMenu

    public TenantHomepageMenu(int tenantUserId) {
        this.tenantUserId = tenantUserId;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            String tenantName = LoggedInUser.getName();
            System.out.println("\nHey, " + tenantName);
            System.out.println(" ");
            System.out.println("--- Tenant Homepage ---");
            System.out.println("1. View Property Details");
            System.out.println("2. Pay Rent");
            System.out.println("3. Vacate Property");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewPropertyDetails();
                    break;
                case 2:
                    payRent();
                    break;
                case 3:
                    vacateProperty();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }

    // --- Features ---
    private void viewPropertyDetails() {
        PropertyDAO propertyDAO = new PropertyDAO();
        Property property = propertyDAO.getPropertyByTenantUserId(tenantUserId);
        if (property != null) {
            System.out.println("\nProperty ID: " + property.getPropertyId());
            System.out.println("Address: " + property.getAddress() +
                    ", City: " + property.getCity() +
                    ", State: " + property.getState());
            System.out.println("Rent: Rs." + property.getRent());
            System.out.println("Status: " + property.getStatus());
        } else {
            System.out.println("No property assigned or found for your account.");
        }
    }

    private void payRent() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Rent Amount: Rs.");
        double rentAmount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        System.out.print("Enter Month (e.g., July 2025): ");
        String rentMonth = scanner.nextLine();

        // 2. Insert payment request into the database
        boolean success = PaymentDAO.requestRentPayment(tenantUserId, rentAmount, rentMonth);

        if (success) {
            System.out.println("[Request Sent for Approval]");
        } else {
            System.out.println("Failed to send payment request. Please try again.");
        }
    }

    private void vacateProperty() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Confirm Vacate Property? [Y/N]: ");
        String input = scanner.nextLine();
        if (!input.equalsIgnoreCase("Y")) {
            System.out.println("Vacate request cancelled.");
            return;
        }
    
        boolean requestSuccess = LeaveRequestDAO.createLeaveRequest(tenantUserId);
        if (requestSuccess) {
            System.out.println("[Leave Request Submitted]");
        } else {
            System.out.println("Failed to submit leave request. Please try again.");
        }
    }

    // scanner.close();
}
