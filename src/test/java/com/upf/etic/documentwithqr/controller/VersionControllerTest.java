package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.model.Version;
import com.upf.etic.documentwithqr.service.impl.VersionServiceImpl;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VersionControllerTest {

    VersionServiceImpl mockVersionServiceImpl = Mockito.mock(VersionServiceImpl.class);
    VersionController versionController = new VersionController(mockVersionServiceImpl);

    @Test
    public void getVersionTest() {
        Version mockVersion = Mockito.mock(Version.class);
        Mockito.when(mockVersionServiceImpl.getVersion()).thenReturn(mockVersion);
        Assert.assertNotNull(versionController.getVersion());
    }

}
