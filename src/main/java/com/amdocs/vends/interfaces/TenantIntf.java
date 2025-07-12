package com.amdocs.vends.interfaces;

import com.amdocs.vends.bean.Tenant;

import java.util.List;

public interface TenantIntf {
    void addTenant(Tenant tenant);
    List<String> getTenantPropertyDetails();
}
