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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EquipoControllerTest {

    public String jsonMimeType = "application/json";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        DefaultMockMvcBuilder defaultBuilder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = defaultBuilder.build();
    }

    public static <T> T retrieveResourceFromResponse(final HttpResponse response, final Class<T> clazz) throws IOException {
        final String jsonFromResponse = EntityUtils.toString(response.getEntity());
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }

    @Test
    public void listarEquipos() throws  IOException {

        HttpUriRequest request = new HttpGet("http://localhost:8082/equipo/lista");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();


        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());
        assertEquals(jsonMimeType , mimeType);


    }


    @Test
    public void listarEquipoDetalle() throws  IOException {


        //Preparación
        HttpUriRequest request = new HttpGet("http://localhost:8082/equipo/lista/" + String.valueOf(1));

        //Activación
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Equipo equipo = retrieveResourceFromResponse(httpResponse, Equipo.class);

        //Verificación
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());
        assertEquals(jsonMimeType , mimeType);
        assertEquals( "Valencia" , equipo.getNombre());

    }

    @Test
    public void addEquipo()  throws Exception {
        String content="{\"nombre\": \"Malaga\",\"estadio\": \"Rosaleda\",\"aforo\": \"20000\",\"presupuesto\": \"3\",\"jugadores\": []}";
        MockHttpServletRequestBuilder builder =

                MockMvcRequestBuilders.post("/equipo/lista/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void updateEquipo()  throws Exception {
        String content="{\"nombre\": \"Athelitc de Bilbao\",\"estadio\": \"San Mamés\",\"aforo\": \"20000\",\"presupuesto\": \"3\",\"jugadores\": []}";
        MockHttpServletRequestBuilder builder =

                MockMvcRequestBuilders.put("/equipo/lista/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteEquipo()  throws Exception  {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/equipo/lista/4");
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }
//
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
//
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
//
//    @Test
//    void addEquipo() {
//    }
//
//    @Test
//    void update_existe() throws  IOException {
//
//        //Preparación
//        HttpUriRequest request = new HttpPut("http://localhost:8082/equipo/lista/" + String.valueOf(id));
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
////    }
//
//    @Test
//    void updateEquipo_comprobarResultado() throws  IOException {
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
}