package isi.dan.laboratorios.danmspedidos.dtos.requests;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
public class PedidoRequestDTO {
    private Instant fechaPedido;
    private List<DetallePedidoRequestDTO> detallesPedido;
    private Integer idObra;
}
