package com.geekshubs.manager_deportivo.models;

import com.geekshubs.manager_deportivo.controllers.EquipoController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import javax.persistence.*;

import java.io.Serializable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Entity
@Table(name = "equipos")
public class Equipo implements Serializable  {//implements Serializable es una interface que permite enviar datos web convirtiendolos en bytes y luego recuperandolos
    //extends EntityModel es como Serializable pero nos muestra tambien los links

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Auto incremento
    Long id;

    private String nombre;
    private String estadio;
    private int aforo;
    private float presupuesto;

    public Equipo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

//    @PostLoad//Otro metodo para añadir link a factura pero este es globar
//    public void addLink() {
////        Link link = linkTo(methodOn(EquipoController.class).detalle(String.valueOf(this.id))).withSelfRel(); //link devuelve FacturaControlles/detalle = Localhost:8080/v1/facturaDetalle/id
//        Link link2 = linkTo(methodOn(EquipoController.class).listarEquipos()).withRel("todos");
////        this.add(link);
//        this.add(link2);
//        //add aparece porque extends EntityModel
//        //Esto añadira siempre el link al JSON
//    }

    @Override
    public String toString() {
        return "nombre: " + nombre + " estadio: " + estadio + " aforo: " + aforo + " presupuesto: " + presupuesto;
    }
}
