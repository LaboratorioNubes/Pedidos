package isi.dan.laboratorios.danmspedidos.dtos;

import isi.dan.laboratorios.danmspedidos.domain.EstadoPedido;
import isi.dan.laboratorios.danmspedidos.domain.Obra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Integer id;
    private Instant fechaPedido;
    private List<DetallePedidoDTO> detallesPedido;
    private EstadoPedidoDTO estado;
    private ObraDTO obra;

    public void setItemDetallePedido(DetallePedidoDTO detallePedido) {
        this.detallesPedido.add(detallePedido);
    }
}