package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.PedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.requests.PedidoRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPedidoService {
    void crearPedido(PedidoRequestDTO pedido);
    ResponseEntity<PedidoDTO> actualizarPedido(PedidoDTO pedido, Integer idPedido);
    ResponseEntity<PedidoDTO> borrarPedido(Integer id);
    ResponseEntity<PedidoDTO> borrarDetalleDePedido(Integer id, Integer idDetalle);
    PedidoDTO getPedidoPorId(Integer id);
    PedidoDTO getPedidoPorIdObra(Integer id);
    List<PedidoDTO> getAll();
    DetallePedidoDTO getPedidoPorDetalle(Integer idPedido, Integer id);
    PedidoDTO crearItem(DetallePedidoDTO nuevoItem, Integer idPedido);
}
