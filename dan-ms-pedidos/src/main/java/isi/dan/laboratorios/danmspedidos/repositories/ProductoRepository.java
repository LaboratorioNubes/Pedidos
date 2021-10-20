package isi.dan.laboratorios.danmspedidos.repositories;

import isi.dan.laboratorios.danmspedidos.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    Producto findByNombre(@Param("nombre") String nombre);
}
