package com.amdocs.vends.interfaces;

import com.amdocs.vends.bean.User;
import com.amdocs.vends.utils.exceptions.DuplicateUsernameException;

public interface UserIntf {
    Integer addUser(User user) throws DuplicateUsernameException;
    void signup();
    boolean login();
    void logout();
}
