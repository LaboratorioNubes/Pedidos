package isi.dan.laboratorios.danmspedidos.dtos;

import isi.dan.laboratorios.danmspedidos.domain.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoDTO {
    private Integer id;
    private Integer cantidad;
    private Double precio;
    private Producto producto;
}