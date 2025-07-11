package com.amdocs.vends.service;

import com.amdocs.vends.dao.JDBC;

public class MainClass {
    public static void main(String[] args) {
        JDBC.getConnection();
        UserImpl user = new UserImpl();
        user.showAdminHomepage();
    }
}
