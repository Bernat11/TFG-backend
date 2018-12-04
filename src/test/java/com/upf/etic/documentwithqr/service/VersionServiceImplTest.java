package com.upf.etic.documentwithqr.service;

import com.upf.etic.documentwithqr.model.Version;
import com.upf.etic.documentwithqr.service.impl.VersionServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VersionServiceImplTest {

    @Test
    public void getVersionTest(){
        Version mockVersion = Mockito.mock(Version.class);
        VersionServiceImpl versionService = new VersionServiceImpl(mockVersion);
        Assert.assertNotNull(versionService.getVersion());
    }

}
