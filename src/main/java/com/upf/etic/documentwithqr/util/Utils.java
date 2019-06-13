package com.upf.etic.documentwithqr.util;

import com.upf.etic.documentwithqr.error.exception.JSONReaderException;
import com.upf.etic.documentwithqr.error.exception.QRcodeGenerationException;
import com.upf.etic.documentwithqr.error.exception.StorageServiceException;
import com.upf.etic.documentwithqr.service.QRgeneratorService;
import com.upf.etic.documentwithqr.service.impl.QRgeneratorServiceImpl;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import static com.upf.etic.documentwithqr.constants.ApplicationConstants.APPLICATION_HOST;
import static com.upf.etic.documentwithqr.constants.ApplicationConstants.APPLICATION_PORT;
import static io.restassured.RestAssured.given;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    public static JSONObject getJSONfromFile(String resource) throws JSONReaderException {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(new FileReader(resource));
        } catch (IOException | ParseException e) {
            throw new JSONReaderException("An error occurred reading JSON");
        }
    }

    //TODO: Deserializar el string para del JSON para pasarlo a Marker
    public static void createFullMarkers() throws JSONReaderException, MalformedURLException, QRcodeGenerationException, StorageServiceException {
        String pathToResoruces = "src\\test\\resources\\markers";
        HashMap<String, String> jsonMap = new HashMap();
        HashMap<String, String> imageMap = new HashMap();

        String[] markers = {"Arco", "Plata", "Camp", "Cascada", "Graffiti", "Palau", "Sagrada", "UPF", "La salle barceloneta",
        "Merlin", "Perruqueria Arman", "Pim pam burguer", "Restaurant del barri", "UPF ciutadella"};

        jsonMap.put("Arco", pathToResoruces + "\\create\\Arco de triumfo\\arco_triumfo.json");
        jsonMap.put("Plata", pathToResoruces + "\\create\\Bar la Plata\\bar la plata.json");
        jsonMap.put("Camp", pathToResoruces + "\\create\\Camp Nou\\camp nou.json");
        jsonMap.put("Cascada", pathToResoruces + "\\create\\Cascada Ciudadela\\cascada parque ciudadela.json");
        jsonMap.put("Graffiti", pathToResoruces + "\\create\\Graffiti dragón poblenou\\grafiti_dragon.json");
        jsonMap.put("Palau", pathToResoruces + "\\create\\Palau de la Música\\Palau de la Música.json");
        jsonMap.put("Sagrada", pathToResoruces + "\\create\\Sagrada familia\\sagrada_familia.json");
        jsonMap.put("UPF", pathToResoruces + "\\create\\UPF Guttenberg\\Guttenberg_upf.json");
        jsonMap.put("La salle barceloneta", pathToResoruces + "\\create\\La salle barceloneta\\salle.json");
        jsonMap.put("Merlin", pathToResoruces + "\\create\\Merlin\\merlin.json");
        jsonMap.put("Perruqueria Arman", pathToResoruces + "\\create\\Perruqueria Arman\\arman.json");
        jsonMap.put("Pim pam burguer", pathToResoruces + "\\create\\Pim pam burguer\\pim.json");
        jsonMap.put("Restaurant del barri", pathToResoruces + "\\create\\Restaurant del barri\\restaurant.json");
        jsonMap.put("UPF ciutadella", pathToResoruces + "\\create\\UPF ciutadella\\upf ciutadella.json");


        imageMap.put("Arco", pathToResoruces + "\\create\\Arco de triumfo\\arco_triumfo.jpg");
        imageMap.put("Plata", pathToResoruces + "\\create\\Bar la Plata\\la plata.jpg");
        imageMap.put("Camp", pathToResoruces + "\\create\\Camp Nou\\camp nou.jpeg");
        imageMap.put("Cascada", pathToResoruces + "\\create\\Cascada Ciudadela\\cascada_ciudadela.jpg");
        imageMap.put("Graffiti", pathToResoruces + "\\create\\Graffiti dragón poblenou\\dragon_grafiti_pb9.jpg");
        imageMap.put("Palau", pathToResoruces + "\\create\\Palau de la Música\\Palau-de-la-musica1.png");
        imageMap.put("Sagrada", pathToResoruces + "\\create\\Sagrada familia\\sagrada_familia.jpg");
        imageMap.put("UPF", pathToResoruces + "\\create\\UPF Guttenberg\\upf.jpg");
        imageMap.put("La salle barceloneta", pathToResoruces + "\\create\\La salle barceloneta\\la salle barceloneta.jpg");
        imageMap.put("Merlin", pathToResoruces + "\\create\\Merlin\\Discoteca merlin.jpg");
        imageMap.put("Perruqueria Arman", pathToResoruces + "\\create\\Perruqueria Arman\\perruqueria arman.jpg");
        imageMap.put("Pim pam burguer", pathToResoruces + "\\create\\Pim pam burguer\\pim pam burguer.jpg");
        imageMap.put("Restaurant del barri", pathToResoruces + "\\create\\Restaurant del barri\\restaurant del barri.jpg");
        imageMap.put("UPF ciutadella", pathToResoruces + "\\create\\UPF ciutadella\\upf ciutadella.jpg");

        int i=1;
        for(String marker: markers){
            JSONObject jsonObject = Utils.getJSONfromFile(jsonMap.get(marker));
            File imagen = new File(imageMap.get(marker));
            given().
                    multiPart("imagen", imagen).
                    multiPart("stringMarker", jsonObject.toJSONString()).
                    when().
                    post(APPLICATION_HOST + APPLICATION_PORT + "/api/markers").
                    then().
                    statusCode(201);
            QRgeneratorService qRgeneratorService = new QRgeneratorServiceImpl();
            qRgeneratorService.generateQRcode("http://example.com", 300, 300, i);
            i++;
        }

    }

    public static String markerType2enum(String type){
        if("Arte urbano".equalsIgnoreCase(type)){
            return "ArteUrbano";
        } else if("Otra localizacion".equalsIgnoreCase(type)){
            return "OtraLocalizacion";
        } return type;
    }

    public static String sanitizeLogs(Object userMessageToLog){
        return userMessageToLog.toString().replaceAll("[\n|\r|\t]", "_");
    }
}
