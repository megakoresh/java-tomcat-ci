/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metropolia.javaee.tomcat.test;

import com.metropolia.javaee.tomcat.SampleTextBean;
import java.io.File;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SampleTest {

    @Inject
    SampleTextBean beanToTest;

    @Deployment
    public static Archive<?> createDeploymnet() {        
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage("com.metropolia.javaee.tomcat")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        PomEquippedResolveStage mavenResolver = Maven.resolver().loadPomFromFile("pom.xml");
        File[] libs = mavenResolver.importRuntimeAndTestDependencies().resolve().withTransitivity().asFile();
        war.addAsLibraries(libs);
        return war;
    }

    @Test
    public void sampleTest() {
        assertTrue(beanToTest.getSessionIdentifier() instanceof String);
    }
}
