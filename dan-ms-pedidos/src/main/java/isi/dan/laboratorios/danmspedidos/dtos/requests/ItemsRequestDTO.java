package isi.dan.laboratorios.danmspedidos.dtos.requests;

import lombok.Getter;

@Getter
public class ItemsRequestDTO {
    private Integer cantidad;
    private String producto;
    private Double monto;
    private Double precio;
}
