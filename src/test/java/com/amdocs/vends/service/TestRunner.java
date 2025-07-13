package com.amdocs.vends.service;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Simple test runner to execute UserImpl tests
 */
public class TestRunner {
    
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(UserImplTest.class);
        
        System.out.println("Test Results:");
        System.out.println("Total tests run: " + result.getRunCount());
        System.out.println("Tests passed: " + (result.getRunCount() - result.getFailureCount()));
        System.out.println("Tests failed: " + result.getFailureCount());
        
        if (result.getFailureCount() > 0) {
            System.out.println("\nFailed tests:");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.getTestHeader() + ": " + failure.getMessage());
            }
        }
        
        System.out.println("\nTest execution time: " + result.getRunTime() + "ms");
    }
} 