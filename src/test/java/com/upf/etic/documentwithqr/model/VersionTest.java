package com.upf.etic.documentwithqr.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class VersionTest {

    private Version version = new Version("A version", "A version");

    @Test
    public void getWebAppVersionTest(){
        Assert.assertNotNull(version.getWebAppVersion());
    }

    @Test
    public void getQRversionTest(){
        Assert.assertNotNull(version.getQRversion());
    }
}
