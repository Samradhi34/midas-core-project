package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPopulator {
    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private DatabaseConduit databaseConduit;

    public void populate() {
    	
    	 String[] lines = fileLoader.loadStrings("/test_data/lkjhgfdsa.hjkl");

    	    for (String line : lines) {
    	        if (line == null || line.trim().isEmpty()) continue;

    	        String[] parts = line.split(",");

    	        String name = parts[0].trim();
    	        float balance = Float.parseFloat(parts[1].trim());

    	        UserRecord user = new UserRecord(name, balance);
    	        databaseConduit.save(user);
    	    }   	
    }
}
    
    
