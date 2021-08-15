package isi.dan.laboratorios.danmspedidos.repositories;

import isi.dan.laboratorios.danmspedidos.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
