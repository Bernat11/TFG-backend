package com.upf.etic.documentwithqr.controller;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ViewsControllerTest {

    ViewsController viewsController = new ViewsController();

    @Test
    public void getViewHomeTest(){
        Assert.assertEquals(viewsController.getView(), "home");
    }
}
