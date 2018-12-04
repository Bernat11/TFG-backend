package com.upf.etic.documentwithqr.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.HEIGHT;
import static com.upf.etic.documentwithqr.constants.ApplicationConstants.WIDTH;

public class ImageDimensionTest {
    
    private ImageDimension imageDimension = new ImageDimension(WIDTH, HEIGHT);
    
    @Test
    public void getWidthTest(){
        Assert.assertEquals(imageDimension.getWidth(), WIDTH);
    }

    @Test
    public void getHeightTest(){
        Assert.assertEquals(imageDimension.getHeight(), HEIGHT);
    }
}
