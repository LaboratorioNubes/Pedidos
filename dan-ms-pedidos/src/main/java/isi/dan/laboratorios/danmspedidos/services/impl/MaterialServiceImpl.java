package isi.dan.laboratorios.danmspedidos.services.impl;

import isi.dan.laboratorios.danmspedidos.domain.Producto;
import isi.dan.laboratorios.danmspedidos.dtos.responses.ProductoResponseDTO;
import isi.dan.laboratorios.danmspedidos.exceptions.BadRequestException;
import isi.dan.laboratorios.danmspedidos.exceptions.DataNotFoundException;
import isi.dan.laboratorios.danmspedidos.repositories.ProductoRepository;
import isi.dan.laboratorios.danmspedidos.services.IMaterialService;
import isi.dan.laboratorios.danmspedidos.utils.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import isi.dan.laboratorios.danmspedidos.configuration.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements IMaterialService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ListMapper listMapper;

//    @Override
//    public ProductoResponseDTO stockDisponible(Integer idProducto, Integer cantidad) {
//
//        Optional<Producto> producto = productoRepository.findById(idProducto);
//
//        if(!producto.isPresent()){
//            throw new DataNotFoundException("El producto con id:" + idProducto + " no existe");
//        }
//
//        ResponseEntity<ProductoResponseDTO> response =
//                WebClient.create(Constantes.URL_API_PRODUCTOS + "/" + idProducto).get()
//                        .accept(MediaType.APPLICATION_JSON)
//                        .retrieve()
//                        .toEntity(ProductoResponseDTO.class)
//                        .block();
//
//        if (response.getBody().getStockActual()<cantidad){
//            throw new BadRequestException("No hay stock disponible para el producto con id:" + idProducto);
//        }
//
//        return response.getBody();
//    }

    @Override
    public ProductoResponseDTO stockDisponible(Integer idProducto, Integer cantidad) {
        return null;
    }

    @Override
    public List<ProductoResponseDTO> getProducts(List<String> idProductos) {

        List<Producto> list = new ArrayList<>();

        for(String id: idProductos){
            Optional<Producto> producto = Optional.ofNullable(productoRepository.findByNombre(id));

            if(!producto.isPresent()){
                throw new DataNotFoundException("El producto con id:" + id + " no existe");
            }else list.add(producto.get());
        }

        return listMapper.mapList(list, ProductoResponseDTO.class);
    }

}
