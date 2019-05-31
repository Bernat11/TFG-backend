package com.upf.etic.documentwithqr.dao;

import com.upf.etic.documentwithqr.dao.Impl.MarkerDaoImpl;
import com.upf.etic.documentwithqr.error.exception.RepositoryException;
import com.upf.etic.documentwithqr.model.entity.Marker;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MarkerDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private MarkerDaoImpl markerDao;

    @Test
    public void findAllMarkersSucessTest() {
        Assert.isInstanceOf(List.class, markerDao.findAll());
    }

    @Test
    public void saveMarkerSucessTest() {
        Marker marker = Mockito.mock(Marker.class);
        markerDao.save(marker);
    }

    @Test
    public void findMarkerByIdSuccessTest(){
        MarkerDaoImpl markerDaoMock = Mockito.mock(MarkerDaoImpl.class);
        Mockito.when(markerDaoMock.findById(new Long(1))).thenReturn(new Marker());
        Assert.isInstanceOf(Marker.class, markerDaoMock.findById(new Long(1)));
    }

    @Test
    public void deleteMarkerByIdSuccessTest(){
        markerDao.delete(new Marker());
    }

    @Test
    public void deleteAllMarkersSuccessTest(){
        markerDao.deleteAll();
    }

    @Test
    public void countAllMarkersSuccessTest(){
        Assert.isInstanceOf(Long.class, markerDao.count());
    }

    @Test
    public void countByTypeSucessTest() throws RepositoryException {
        Assert.isInstanceOf(Integer.class, markerDao.countByType("monumento"));
    }

    @Test(expectedExceptions = RepositoryException.class)
    public void countByTypeThrowRepositoryExceptionTest() throws RepositoryException {
        markerDao.countByType("no existe");
    }

    @Test(expectedExceptions = RepositoryException.class)
    public void findByTypeThrowRepositoryExceptionTest() throws RepositoryException {
        markerDao.findByType("no existe");
    }


}
