package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.PedidoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PedidoService {
    public PedidoDTO crearPedido(PedidoDTO p);
    public ResponseEntity<PedidoDTO> actualizarPedido(PedidoDTO pedido, Integer idPedido);
    public ResponseEntity<PedidoDTO> borrarPedido(Integer id);
    public ResponseEntity<PedidoDTO> borrarDetalleDePedido(Integer id, Integer idDetalle);
    public PedidoDTO getPedidoPorId(Integer id);
    public PedidoDTO getPedidoPorIdObra(Integer id);
    public List<PedidoDTO> getAll();
    public DetallePedidoDTO getPedidoPorDetalle(Integer idPedido, Integer id);
    public PedidoDTO crearItem(DetallePedidoDTO nuevoItem, Integer idPedido);
}
