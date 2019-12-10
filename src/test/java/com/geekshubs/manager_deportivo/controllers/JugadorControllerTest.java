package com.geekshubs.manager_deportivo.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geekshubs.manager_deportivo.models.Equipo;
import com.geekshubs.manager_deportivo.models.Jugador;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JugadorControllerTest {


    private String jsonMimeType = "application/json";

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
    public void listaJugadores() throws  IOException {

        HttpUriRequest request = new HttpGet("http://localhost:8082/jugador/lista");

        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();


        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());
        assertEquals(jsonMimeType , mimeType);


    }


    @Test
    public void jugadorDetalle () throws  IOException {


        //Preparación
        HttpUriRequest request = new HttpGet("http://localhost:8082/jugador/lista/" + String.valueOf(1));

        //Activación
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        Jugador jugador = retrieveResourceFromResponse(httpResponse, Jugador.class);

        //Verificación
        String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
        assertEquals(HttpStatus.SC_OK , httpResponse.getStatusLine().getStatusCode());
        assertEquals(jsonMimeType , mimeType);
        assertEquals( "Carlos" , jugador.getNombre());

    }

    @Test
    public void addJugador()  throws Exception {
        String content="{\"apellidos\": \"Torres\",\"edad\": \"20\",\"equipo_id\": \"1\",\"nombre\": \"Ferran\"}";
        MockHttpServletRequestBuilder builder =

                MockMvcRequestBuilders.post("/jugador/lista/add/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void updateJugador()  throws Exception {
        String content="{\"nombre\": \"Roger\",\"apellidos\": \"Martí\",\"edad\": \"28\"}";
        MockHttpServletRequestBuilder builder =

                MockMvcRequestBuilders.put("/jugador/lista/3/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteJugador()  throws Exception  {
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.delete("/jugador/lista/2");
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}