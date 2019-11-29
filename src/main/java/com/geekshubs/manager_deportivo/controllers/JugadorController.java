package com.geekshubs.manager_deportivo.controllers;

import com.geekshubs.manager_deportivo.models.Equipo;
import com.geekshubs.manager_deportivo.models.Jugador;
import com.geekshubs.manager_deportivo.repository.EquipoRepository;
import com.geekshubs.manager_deportivo.repository.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jugador")
public class JugadorController {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @GetMapping("/lista")
    public List<Jugador> listaJugadores(){
        System.out.println("LISTA DE TODOS LOS JUGADORES");
        return jugadorRepository.findAll();
    }

    @GetMapping("/lista/{id}")
    public Jugador jugadoreDetalle(@PathVariable String id){
        System.out.println("DETALLE DE JUGADOR");
        return jugadorRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @PostMapping("/lista/add/{id}")
    public List<Jugador> addJugador(@RequestBody Jugador jugador , @PathVariable String id){
        System.out.println("ADD DE JUGADOR");
        
        Equipo equipo = equipoRepository.getOne(Long.parseLong(id));
        jugador.setEquipo(equipo); // Añade el equipo al jugador !!!
        equipo.addJugador(jugador); // Añade el jugador con el equipo en la lista de jugadores del equipo !!!
        equipoRepository.save(equipo); // Guarda el equipo con la lista de jugadores actualizada

        return jugadorRepository.findAll();
    }

    @PutMapping("/lista/{id}")
    public List<Jugador> updateJugador(@RequestBody Jugador jugador , @PathVariable String id){
        System.out.println("UPDATE DE JUGADOR");
        jugador.setId(Long.parseLong(id));
        jugadorRepository.save(jugador);

        return jugadorRepository.findAll();
    }

    @DeleteMapping("/lista/{id}")
    public List<Jugador> deleteJugador(@PathVariable String id){
        System.out.println("DELETE DE JUGADOR");
        jugadorRepository.deleteById(Long.parseLong(id));
        return jugadorRepository.findAll();
    }

}
