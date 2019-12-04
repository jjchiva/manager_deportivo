package com.geekshubs.manager_deportivo.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekshubs.manager_deportivo.models.Equipo;
import com.geekshubs.manager_deportivo.service.EquipoErrorException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EquipoControllerTest {

    String jsonMimeType = "application/json";
    Random random = new Random();

    int id = random.nextInt(1) + 1;

    public static <T> T retrieveResourceFromResponse(final HttpResponse response, final Class<T> clazz) throws IOException {
        final String jsonFromResponse = EntityUtils.toString(response.getEntity());
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

    @Test
    void listarEquipos_existe() throws  IOException {

        HttpUriRequest request = new HttpGet("http://localhost:8082/equipo/lista");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    void listarEquipos_tipoJSON() throws IOException {

        HttpUriRequest request = new HttpGet("http://localhost:8082/equipo/lista");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals(jsonMimeType , mimeType);

    }


    @Test
    void listarEquipoDetalle_existe() throws  IOException {

        System.out.println(id);

        HttpUriRequest request = new HttpGet("http://localhost:8082/equipo/lista/" + String.valueOf(id));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals(jsonMimeType , mimeType);

    }

    @Test
    void listarEquipoDetalle_tipoJson() throws  IOException {


        HttpUriRequest request = new HttpGet("http://localhost:8082/equipo/lista/" + String.valueOf(id));

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());

    }

    @Test
    void listarEquipoDetalle_comprobarResultado() throws  IOException {

        //Preparación
        HttpUriRequest request = new HttpGet("http://localhost:8082/equipo/lista/" + String.valueOf(1));

        //Activación
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Equipo equipo = retrieveResourceFromResponse(httpResponse, Equipo.class);
        //Verificación
        assertEquals( "Valencia" , equipo.getNombre());
    }

//    @Test
//    void deleteEquipo_existe() throws  IOException {
//
//        //Preparación
//        HttpUriRequest request = new HttpDelete("http://localhost:8082/equipo/lista/" + String.valueOf(id));
//
//        //Activación
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
//
//        //Verificación
//        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
//        assertEquals(jsonMimeType , mimeType);
//
//    }
//
//    @Test
//    void deleteEquipo_tipoJson() throws  IOException {
//
//        //Preparación
//        HttpUriRequest request = new HttpDelete("http://localhost:8082/equipo/lista/" + String.valueOf(id));
//
//        //Activación
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
//
//        //Verificación
//        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());
//
//    }

//    @Test
//    void deleteEquipo_comprobarResultado() throws  IOException {
//
//        //Preparación
//        HttpUriRequest request = new HttpDelete("http://localhost:8082/equipo/lista/" + String.valueOf(2));
//
//        //Activación
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
////        Equipo equipo = retrieveResourceFromResponse(httpResponse, Equipo.class);
//
//        String jsonFromResponse = EntityUtils.toString(httpResponse.getEntity());
//        System.out.println(jsonFromResponse);
//
//        //Verificación
////        assertEquals( null , equipo.getNombre());
//    }

    @Test
    void addEquipo() {
    }

    @Test
    void update_existe() throws  IOException {

        //Preparación
        HttpUriRequest request = new HttpPut("http://localhost:8082/equipo/lista/" + String.valueOf(id));

        //Activación
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        //Verificación
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals(jsonMimeType , mimeType);

    }

//    @Test
//    void updateEquipo_tipoJson() throws  IOException {
//
//        //Preparación
//        HttpUriRequest request = new HttpPut("http://localhost:8082/equipo/lista/" + String.valueOf(1));
//
//
//        //Activación
//        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request , java.net.http.HttpResponse.BodyHandlers.discarding());
//
//        HttpResponse<String> response =
//                client.send(request, BodyHandlers.ofString());
//
//        System.out.println(response.body());
//
//        //Verificación
//        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());
//
//    }

    @Test
    void updateEquipo_comprobarResultado() throws  IOException {

        //Preparación
        HttpUriRequest request = new HttpDelete("http://localhost:8082/equipo/lista/" + String.valueOf(2));

        //Activación
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
//        Equipo equipo = retrieveResourceFromResponse(httpResponse, Equipo.class);

        String jsonFromResponse = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(jsonFromResponse);

        //Verificación
//        assertEquals( null , equipo.getNombre());
    }
}