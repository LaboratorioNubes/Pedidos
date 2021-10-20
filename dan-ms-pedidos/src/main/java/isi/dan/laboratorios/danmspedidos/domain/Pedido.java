package isi.dan.laboratorios.danmspedidos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	private Date fechaPedido;

	@OneToMany
    private List<DetallePedido> detallesPedido;

	@OneToOne
    private EstadoPedido estado;

	@OneToOne
    private Obra obra;

	public void setItemDetallePedido(DetallePedido detallePedido) {
		this.detallesPedido.add(detallePedido);
	}
}