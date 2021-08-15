package isi.dan.laboratorios.danmspedidos.repositories;

import isi.dan.laboratorios.danmspedidos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}