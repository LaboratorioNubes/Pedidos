package isi.dan.laboratorios.danmspedidos.services;


import isi.dan.laboratorios.danmspedidos.dtos.responses.ProductoResponseDTO;

import java.util.List;

public interface IMaterialService {
   ProductoResponseDTO stockDisponible(Integer idProducto, Integer cantidad);
   List<ProductoResponseDTO> getProducts(List<String> idProductos);
}
