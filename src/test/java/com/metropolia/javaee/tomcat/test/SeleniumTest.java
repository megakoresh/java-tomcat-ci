/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metropolia.javaee.tomcat.test;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


//mvn -Dtest=SeleniumTest -Parq-tomcat-managed test
public class SeleniumTest {
    private static final String TEST_SERVER_URL = "https://java-tomcat-ci-dev.herokuapp.com/javaee-tomcat-0.1/index.xhtml"; //this should be your heroku app
    private WebDriver chromeDriver;   
    private static ChromeDriverService chromeService;
    
    @BeforeClass
    public static void startServices() throws IOException{
        chromeService = new ChromeDriverService.Builder()                
                .usingDriverExecutable(new File("D://toolbox/utils/chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        chromeService.start();
    }
    
    @Before
    public void openBrowsers(){
        //Here we would have to put sauce labs url instead if we want to run it on Travis
        chromeDriver = new RemoteWebDriver(chromeService.getUrl(), DesiredCapabilities.chrome());
        chromeDriver.get(TEST_SERVER_URL);   
    }
    
    @After
    public void saveScreenshotAndCloseBrowser() throws IOException{
        saveScreenshot("chrome_selenium_test.png");
        chromeDriver.quit();
    }
    
    @AfterClass
    public static void shutdownServices(){
        chromeService.stop();
    }
    
    @Test
    public void performTest1() throws IOException, InterruptedException {
        //find a form field by name                
        synchronized(TEST_SERVER_URL){
            TEST_SERVER_URL.wait(3000);
        }
//        WebElement searchField = driver.findElement(By.name("q")); //your element's name here
//        searchField.sendKeys("memes");
//        searchField.submit();
        chromeDriver.navigate().refresh();
        boolean allFine = new WebDriverWait(chromeDriver, 3).until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver t) {
                return t.getTitle().startsWith("Faces");
            }
        });
        assertTrue(allFine);
    }
    
    @Test
    public void performTest1UsingHtmlUnit() throws InterruptedException{
        WebDriver driver = new HtmlUnitDriver();
        driver.get(TEST_SERVER_URL);
        synchronized(TEST_SERVER_URL){
            TEST_SERVER_URL.wait(3000);
        }
//        WebElement searchField = driver.findElement(By.name("q")); //your element's name here
//        searchField.sendKeys("memes");
//        searchField.submit();
        driver.navigate().refresh();
        boolean allFine = new WebDriverWait(driver, 2).until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver t) {
                return t.getTitle().startsWith("Faces");
            }
        });
        assertTrue(allFine);
    }
    
    private void saveScreenshot(String name) throws IOException{
        File screenshot = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(name));
    }
      
    private static String getProjectHostDir(){
        File projectDir = new File(System.getProperty("project.basedir"));
        if(projectDir.exists()){
            return projectDir.getParent();
        } else {
            return null;
        }
    }    
}
