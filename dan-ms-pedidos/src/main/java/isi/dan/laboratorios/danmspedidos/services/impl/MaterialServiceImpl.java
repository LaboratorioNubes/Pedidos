package isi.dan.laboratorios.danmspedidos.services.impl;


import isi.dan.laboratorios.danmspedidos.domain.Producto;
import isi.dan.laboratorios.danmspedidos.dtos.responses.ProductoResponseDTO;
import isi.dan.laboratorios.danmspedidos.exceptions.BadRequestException;
import isi.dan.laboratorios.danmspedidos.exceptions.DataNotFoundException;
import isi.dan.laboratorios.danmspedidos.repositories.ProductoRepository;
import isi.dan.laboratorios.danmspedidos.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import isi.dan.laboratorios.danmspedidos.configuration.Constantes;

import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public ProductoResponseDTO stockDisponible(Integer idProducto, Integer cantidad) {

        Optional<Producto> producto = productoRepository.findById(idProducto);

        if(!producto.isPresent()){
            throw new DataNotFoundException("El producto con id:" + idProducto + " no existe");
        }

        ResponseEntity<ProductoResponseDTO> response =
                WebClient.create(Constantes.URL_API_PRODUCTOS + "/" + idProducto).get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntity(ProductoResponseDTO.class)
                        .block();

        if (response.getBody().getStockActual()<cantidad){
            throw new BadRequestException("No hay stock disponible para el producto con id:" + idProducto);
        }

        return response.getBody();
    }

}
