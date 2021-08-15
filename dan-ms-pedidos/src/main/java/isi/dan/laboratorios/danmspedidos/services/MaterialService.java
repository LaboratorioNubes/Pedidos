package isi.dan.laboratorios.danmspedidos.services;


import isi.dan.laboratorios.danmspedidos.dtos.responses.ProductoResponseDTO;

public interface MaterialService {
    public ProductoResponseDTO stockDisponible(Integer idProducto, Integer cantidad);
}
