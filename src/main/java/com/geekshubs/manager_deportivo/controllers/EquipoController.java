package com.geekshubs.manager_deportivo.controllers;


import com.geekshubs.manager_deportivo.models.Equipo;
import com.geekshubs.manager_deportivo.repository.EquipoRepository;
import com.geekshubs.manager_deportivo.service.EquipoErrorException;
import com.geekshubs.manager_deportivo.service.EquipoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost")
@ControllerAdvice //Exception
@RestController //Devuelve los datos en JSON
@RequestMapping("/equipo")
public class EquipoController extends ResponseEntityExceptionHandler {

    //-----EXCEPTION-----//
    @ExceptionHandler({EquipoErrorException.class , NumberFormatException.class})
    public final ResponseEntity<EquipoException> equipoError (Exception ex){

        EquipoException exceptionResponse = null;

        if (ex.getClass() == NumberFormatException.class) {
            exceptionResponse = new EquipoException("Id no numerico", "Por favor, introduzca un numero superior a 0");
        } else if (ex.getMessage() == "El nombre del equipo está vacio") {
            exceptionResponse = new EquipoException("Body incompleto", ex.getMessage());
        }else{
            exceptionResponse = new EquipoException("Id negativo", "Por favor, introduzca un numero superior a 0.");
        }

        return new ResponseEntity<EquipoException>(exceptionResponse , new HttpHeaders() , HttpStatus.NOT_FOUND);
    }





    @Autowired
    EquipoRepository equipoRepository;

    @GetMapping("/lista")
    public List<Equipo> listarEquipos(){
        System.out.println("LISTAR TODOS LOS EQUIPOS");
        return equipoRepository.findAll();
    }

    @GetMapping("/lista/{id}")
    public Equipo listarEquipoDetalle(@PathVariable String id ){
        System.out.println("LISTAR UN EQUIPO");

        if (Integer.parseInt(id) < 0) throw new EquipoErrorException("Id negativo");
        return equipoRepository.findById(Long.parseLong(id)).orElse(null);
    }

    @DeleteMapping("/lista/{id}")
    public List<Equipo> deleteEquipo(@PathVariable String id){
        System.out.println("DELETE UN EQUIPO");
        if (Integer.parseInt(id) < 0) throw new EquipoErrorException("Id negativo"); //EXCEPTION
        equipoRepository.deleteById(Long.parseLong(id));
        return equipoRepository.findAll();
    }

    @PostMapping ("/lista/add" )
    public List<Equipo> addEquipo(@Valid @RequestBody Equipo equipo , BindingResult result) {

        equipo.setId(null);

        if (result.hasErrors()) { //VALIDACION
            System.out.println("ERROR");
            throw new EquipoErrorException("El nombre del equipo está vacio");
        }else {
            System.out.println("SAVE EQUIPO");
            equipoRepository.save(equipo);
        }

        return equipoRepository.findAll();
    }

    @PutMapping ("/lista/{id}")
    public List<Equipo> updateEquipo(@Valid @RequestBody Equipo equipo , BindingResult result , @PathVariable String id){
        equipo.setId(Long.parseLong(id));
        System.out.println("UPDATE EQUIPO");
        if (result.hasErrors()) {
            System.out.println("ERROR");
            throw new EquipoErrorException("El nombre del equipo está vacio");
        } else if (Integer.parseInt(id) < 0){
            throw new EquipoErrorException("Id negativo");
        } else{
            equipoRepository.save(equipo);
        }
        return equipoRepository.findAll();
    }

}
