package com.upf.etic.documentwithqr.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.HEIGHT;
import static com.upf.etic.documentwithqr.constants.ApplicationConstants.WIDTH;

public class ImageDimensionTest {
    
    private ImageDimension imageDimension = new ImageDimension(WIDTH, HEIGHT);

    @Test
    public void setWidthTest(){
        imageDimension.setWidth(WIDTH);
        Assert.assertEquals(imageDimension.getWidth(), WIDTH);
    }

    @Test
    public void setHeightTest(){
        imageDimension.setHeight(HEIGHT);
        Assert.assertEquals(imageDimension.getHeight(), HEIGHT);
    }
}
