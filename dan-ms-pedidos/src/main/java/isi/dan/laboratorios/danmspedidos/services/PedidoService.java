package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.PedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.requests.PedidoRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PedidoService {
    public PedidoDTO crearPedido(PedidoRequestDTO pedido);
    public ResponseEntity<PedidoDTO> actualizarPedido(PedidoDTO pedido, Integer idPedido);
    public ResponseEntity<PedidoDTO> borrarPedido(Integer id);
    public ResponseEntity<PedidoDTO> borrarDetalleDePedido(Integer id, Integer idDetalle);
    public PedidoDTO getPedidoPorId(Integer id);
    public PedidoDTO getPedidoPorIdObra(Integer id);
    public List<PedidoDTO> getAll();
    public DetallePedidoDTO getPedidoPorDetalle(Integer idPedido, Integer id);
    public PedidoDTO crearItem(DetallePedidoDTO nuevoItem, Integer idPedido);
}
