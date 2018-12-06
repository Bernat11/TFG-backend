package com.upf.etic.documentwithqr.controller.controllerIT;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.Test;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewsControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void generateQRsuccess() throws Exception {
//        ResponseEntity<Client> responseEntity =
//                restTemplate.postForEntity("/clients", new CreateClientRequest("Foo"), Client.class);
//        Client client = responseEntity.getBody();
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals("Foo", client.getName());
    }

}
