package isi.dan.laboratorios.danmspedidos.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
}
