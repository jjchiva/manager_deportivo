package com.geekshubs.manager_deportivo.controllers;


import com.geekshubs.manager_deportivo.models.Equipo;
import com.geekshubs.manager_deportivo.repository.EquipoRepository;
import com.geekshubs.manager_deportivo.service.EquipoErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice //Exception
@RestController //Devuelve los datos en JSON
@RequestMapping("/equipo")
public class EquipoController extends ResponseEntityExceptionHandler {



    @Autowired
    EquipoRepository equipoRepository;

    @GetMapping("/lista")
    public List<Equipo> listarEquipos(){
        System.out.println("LISTAR TODOS LOS EQUIPOS");
        return equipoRepository.findAll();
    }

    @GetMapping("/lista/{id}")
    public Equipo listarEquipoDetalle(@PathVariable String id){
        System.out.println("LISTAR UN EQUIPO");

        if (Integer.parseInt(id) < 0) throw new EquipoErrorException("Id negativo");
        return equipoRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @DeleteMapping("/lista/{id}")
    public List<Equipo> deleteEquipo(@PathVariable String id){
        System.out.println("DELETE UN EQUIPO");
        equipoRepository.deleteById(Long.parseLong(id));
        return equipoRepository.findAll();
    }

    @PostMapping ("/lista/add")
    public List<Equipo> addEquipo(@RequestBody Equipo equipo){
        System.out.println(equipo);
        System.out.println("SAVE EQUIPO");
        equipoRepository.save(equipo);
        return equipoRepository.findAll();
    }

    @PutMapping ("/lista/{id}")
    public List<Equipo> updateEquipo(@RequestBody Equipo equipo , @PathVariable String id){
        equipo.setId(Long.parseLong(id));
        System.out.println(equipo);
        System.out.println("UPDATE EQUIPO");
        equipoRepository.save(equipo);
        return equipoRepository.findAll();
    }


}
