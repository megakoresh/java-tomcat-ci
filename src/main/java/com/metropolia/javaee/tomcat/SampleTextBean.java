/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metropolia.javaee.tomcat;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author megakoresh
 */
@ManagedBean(name = "sampleTextBean")
@SessionScoped
public class SampleTextBean {
    
    private final ArrayList<String> datastore = new ArrayList<>();
    
    private String sessionIdentifier;

    public SampleTextBean() {        
    }
    
    @PostConstruct
    public void init(){
        datastore.add("Damn, son, where'd you find this?");
        datastore.add("Mnt dew + doritors = dewritos.");
        datastore.add("What if java is not kill?");
        datastore.add("gr8 m8 i r8 8/8");
        datastore.add("Get shrekt!");
        datastore.add("Fokin' nopescoped, m8!");
        datastore.add("Sample Text");
        sessionIdentifier = UUID.randomUUID().toString();
    }
    
    public String generateSampleText(){
        Random rand = new Random();
        int index = rand.nextInt(datastore.size());
        return datastore.get(index);
    }
    
    public String getSessionIdentifier() {
        return sessionIdentifier;
    }
}
