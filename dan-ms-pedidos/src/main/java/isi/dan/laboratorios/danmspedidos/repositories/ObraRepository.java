package isi.dan.laboratorios.danmspedidos.repositories;

import isi.dan.laboratorios.danmspedidos.domain.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer> {
}
