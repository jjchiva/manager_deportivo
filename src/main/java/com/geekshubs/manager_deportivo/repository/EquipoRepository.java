package com.geekshubs.manager_deportivo.repository;

import com.geekshubs.manager_deportivo.models.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipoRepository extends JpaRepository<Equipo ,Long> {//JpaRepository tiene querys predefinidas
    // Equipo es el objeto con el que se trabajará y Long es el tipo de id

}
