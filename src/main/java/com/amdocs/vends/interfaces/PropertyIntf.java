package com.amdocs.vends.interfaces;

import com.amdocs.vends.bean.Property;

public interface PropertyIntf {

    void addProperty(Property property);
    boolean checkPropertyBelongsToUser(Integer userId, Integer propertyId);
}
