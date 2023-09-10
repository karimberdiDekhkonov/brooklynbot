package org.example;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class Admin {
    private final Set<String> adminList = new HashSet<>();

    public boolean addAdmin(String id){
        adminList.add(id);
        return true;
    }

    public Set<String> getAdminList(){
        return adminList;
    }
}
