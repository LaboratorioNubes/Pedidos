package isi.dan.laboratorios.danmspedidos.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoPedidoDTO {
    private Integer id;
    private String estado;
}