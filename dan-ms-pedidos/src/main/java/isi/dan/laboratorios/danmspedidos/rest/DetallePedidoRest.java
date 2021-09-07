package isi.dan.laboratorios.danmspedidos.rest;

import isi.dan.laboratorios.danmspedidos.configuration.Constantes;
import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.services.IDetallePedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.API_DETALLE_PEDIDO)
public class DetallePedidoRest {

    @Autowired
    IDetallePedidoService detallePedidoService;

    @PostMapping
    public DetallePedidoDTO crearItems(@RequestBody DetallePedidoDTO detallePedido){
        return detallePedidoService.crearItems(detallePedido);
    }

    @PutMapping
    public DetallePedidoDTO actualizarItems(@RequestBody DetallePedidoDTO detallePedido){
        return detallePedidoService.actualizarItems(detallePedido);
    }


    @DeleteMapping
    public String eliminarItems(@PathVariable(value = "id") int id){
        detallePedidoService.eliminarItems(id);
        return "Eliminación exitosa";
    }

}