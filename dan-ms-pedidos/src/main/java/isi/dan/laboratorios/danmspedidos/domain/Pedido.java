package isi.dan.laboratorios.danmspedidos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    
    private Integer id;
	private Instant fechaPedido;
    private List<DetallePedido> detallesPedido;
    private EstadoPedido estado;
    private Obra obra;

	public void setItemDetallePedido(DetallePedido detallePedido) {
		this.detallesPedido.add(detallePedido);
	}
}