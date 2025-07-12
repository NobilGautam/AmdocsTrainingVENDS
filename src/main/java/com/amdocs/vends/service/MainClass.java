package com.amdocs.vends.service;

import com.amdocs.vends.utils.singleton.LoggedInUser;
import java.util.Scanner;
import com.amdocs.vends.dao.JDBC;

public class MainClass {
	public static void main(String[] args) {
	    // Scanner scanner = new Scanner(System.in);
	    // UserImpl userService = new UserImpl();

	    // while (true) {
	    //     System.out.println("\n========= Vends Smart Rental System =========");
	    //     System.out.println("1. Login");
	    //     System.out.println("2. Signup as Owner");
	    //     System.out.println("3. Exit");
	    //     System.out.print("Enter choice: ");
	    //     int  choice = scanner.nextInt();

	    //     switch (choice) {
	    //         case 1:
	    //             boolean successfulLogin = userService.login();
		// 			if (successfulLogin) {
		// 				userService.showAdminHomepage();
		// 			}
	    //             break;
	    //         case 2:
	    //             userService.signup();
	    //             break;
	    //         case 3:
	    //             System.out.println(" Thank you for using Vends Smart Rental System!");
		// 			System.exit(0);
	    //             break;
	    //         default:
	    //             System.out.println(" Invalid choice.");
	    //     }
	    // }
		String tenantNameFromDB = "Rohit Sharma"; 
		// Replace with actual user ID
		int tenantUserId = 1; 
		// Set the name in the singleton
		LoggedInUser.setName(tenantNameFromDB);
		// Show the tenant menu
		TenantHomepageMenu menu = new TenantHomepageMenu(tenantUserId);
		menu.showMenu();
		// On logout (optional, if you want to clear the name)
		//LoggedInUser.clear();
	}

  }
