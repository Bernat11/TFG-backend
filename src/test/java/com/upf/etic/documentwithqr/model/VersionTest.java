package com.upf.etic.documentwithqr.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest
@WebAppConfiguration
public class VersionTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private Version version;

    @Test
    public void getWebAppVersionTest(){
        Assert.assertNotNull(version.getWebAppVersion());
    }

    @Test
    public void getQRversionTest(){
        Assert.assertNotNull(version.getQRversion());
    }
}
