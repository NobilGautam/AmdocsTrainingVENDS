package com.amdocs.vends.service;

import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.utils.singleton.LoggedInUser;

public class MainClass {
    public static void main(String[] args) {
        JDBC.getConnection();

        // // 1. Show welcome screen
        // // 2. Prompt for username/password
        // // 3. Authenticate user
        // // 4. Set LoggedInUser with real name
        // // 5. If tenant, call TenantHomepageMenu with correct userId
        //     // Replace with actual name from DB
        //     String tenantNameFromDB = "Rohit Sharma"; 
        //     // Replace with actual user ID
        //     int tenantUserId = 1; 
        //     // Set the name in the singleton
        //     LoggedInUser.setName(tenantNameFromDB);
        //     // Show the tenant menu
        //     TenantHomepageMenu menu = new TenantHomepageMenu(tenantUserId);
        //     menu.showMenu();
        //     // On logout (optional, if you want to clear the name)
        //     LoggedInUser.clear();
    }
}
