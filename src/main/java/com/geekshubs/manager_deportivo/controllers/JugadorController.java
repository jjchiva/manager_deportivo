package com.geekshubs.manager_deportivo.controllers;

import com.geekshubs.manager_deportivo.models.Equipo;
import com.geekshubs.manager_deportivo.models.Jugador;
import com.geekshubs.manager_deportivo.repository.EquipoRepository;
import com.geekshubs.manager_deportivo.repository.JugadorRepository;
import com.geekshubs.manager_deportivo.service.EquipoErrorException;
import com.geekshubs.manager_deportivo.service.EquipoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jugador")
public class JugadorController {

    //-----EXCEPTION-----//
    @ExceptionHandler({EquipoErrorException.class , NumberFormatException.class})
    public final ResponseEntity<EquipoException> equipoError (Exception ex){

        EquipoException exceptionResponse = null;

        if (ex.getClass() == NumberFormatException.class) {
            exceptionResponse = new EquipoException("Id no numerico", "Por favor, introduzca un numero superior a 0");
        } else if (ex.getMessage() == "El nombre del jugador está vacio") {
            exceptionResponse = new EquipoException("Body incompleto", ex.getMessage());
        }else{
            exceptionResponse = new EquipoException("Id negativo", "Por favor, introduzca un numero superior a 0.");
        }

        return new ResponseEntity<EquipoException>(exceptionResponse , new HttpHeaders() , HttpStatus.NOT_FOUND);
    }


    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @CrossOrigin(origins = "http://localhost")
    @GetMapping("/lista")
    public List<Jugador> listaJugadores(){
        System.out.println("LISTA DE TODOS LOS JUGADORES");
        return jugadorRepository.findAll();
    }

    @GetMapping("/lista/{id}")
    public Jugador jugadoreDetalle(@PathVariable String id){
        System.out.println("DETALLE DE JUGADOR");
        if (Integer.parseInt(id) < 0) throw new EquipoErrorException("Id negativo");
        return jugadorRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @PostMapping("/lista/add/{id}")
    public List<Jugador> addJugador(@Valid @RequestBody Jugador jugador, BindingResult result, @PathVariable String id){
        System.out.println("ADD JUGADOR");


        if (result.hasErrors()) { //VALIDACION
            System.out.println("ERROR");
            throw new EquipoErrorException("El nombre del jugador está vacio");
        }else if(Long.parseLong(id)<0){
            throw new EquipoErrorException("Id negativo o no numerico");
        }else {
            Equipo equipo = equipoRepository.getOne(Long.parseLong(id));
            jugador.setEquipo(equipo); // Añade el equipo al jugador !!!
            equipo.addJugador(jugador); // Añade el jugador con el equipo en la lista de jugadores del equipo !!!
            equipoRepository.save(equipo); // Guarda el equipo con la lista de jugadores actualizada
        }

        return jugadorRepository.findAll();
    }

    @PutMapping("/lista/{id}")
    public List<Jugador> updateJugador(@RequestBody Jugador jugador , @PathVariable String id){
        System.out.println("UPDATE DE JUGADOR");
        Jugador jugadorRepositoryOne = jugadorRepository.getOne(Long.parseLong(id));

        return jugadorRepository.findAll();
    }

    @DeleteMapping("/lista/{id}")
    public List<Jugador> deleteJugador(@PathVariable String id){
        System.out.println("DELETE DE JUGADOR");
        if (Integer.parseInt(id) < 0) throw new EquipoErrorException("Id negativo o no numerico");
        jugadorRepository.deleteById(Long.parseLong(id));
        return jugadorRepository.findAll();
    }

}
