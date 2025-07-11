package com.amdocs.vends.service;

import com.amdocs.vends.utils.singleton.LoggedInUser;
import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    AuthServiceImpl authService = new AuthServiceImpl();

	    boolean running = true;

	    while (running) {
	        System.out.println("\n========= Vends Smart Rental System =========");
	        System.out.println("1. Login");
	        System.out.println("2. Signup as Owner");
	        System.out.println("3. Exit");
	        System.out.print("Enter choice: ");
	        int  choice = sc.nextInt();

	        switch (choice) {
	            case 1:
	                authService.login();
	                break;
	            case 2:
	                authService.signup();  
	                break;
	            case 3:
	                System.out.println(" Thank you for using Vends Smart Rental System!");
	                running = false;
	                break;
	            default:
	                System.out.println(" Invalid choice.");
	        }
	    }
	}

  }


