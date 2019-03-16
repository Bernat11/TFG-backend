package com.upf.etic.documentwithqr.controller;

import com.upf.etic.documentwithqr.model.Version;
import com.upf.etic.documentwithqr.service.VersionService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest
@WebAppConfiguration
public class VersionControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private VersionController versionController;

    @Test
    public void getVersion(){
        VersionService versionServiceMock = Mockito.mock(VersionService.class);
        Version mockVersion = Mockito.mock(Version.class);
        Mockito.when(versionServiceMock.getVersion()).thenReturn(mockVersion);
        versionController = new VersionController(versionServiceMock);
    }

}
