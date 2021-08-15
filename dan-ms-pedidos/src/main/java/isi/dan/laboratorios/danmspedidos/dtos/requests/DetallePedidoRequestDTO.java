package isi.dan.laboratorios.danmspedidos.dtos.requests;

import lombok.Getter;

@Getter
public class DetallePedidoRequestDTO {
    private Integer cantidad;
    private Integer idProducto;
}
