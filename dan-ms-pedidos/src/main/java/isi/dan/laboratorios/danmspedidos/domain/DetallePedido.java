package isi.dan.laboratorios.danmspedidos.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido {
    private Integer id;
	private Integer cantidad;
	private Double precio;
    private Producto producto;
}
