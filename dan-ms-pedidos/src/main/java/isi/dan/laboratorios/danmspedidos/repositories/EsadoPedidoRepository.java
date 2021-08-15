package isi.dan.laboratorios.danmspedidos.repositories;

import isi.dan.laboratorios.danmspedidos.domain.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
}
