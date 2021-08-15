package isi.dan.laboratorios.danmspedidos.rest;

import java.util.List;
import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.PedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.requests.PedidoRequestDTO;
import isi.dan.laboratorios.danmspedidos.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(PedidoRest.API_PEDIDO)
@Api(value = "PedidoRest", description = "Permite gestionar los pedidos de la empresa")
public class PedidoRest {

    @Autowired
    PedidoService pedidoService;

    static final String API_PEDIDO = "/api/pedido";

    @GetMapping(path = "/{idPedido}")
    @ApiOperation(value = "Retorna un pedido por id")
    public ResponseEntity<PedidoDTO> getPedidoPorId(@PathVariable Integer idPedido){
        return ResponseEntity.ok(pedidoService.getPedidoPorId(idPedido));
    }


    @GetMapping
    @ApiOperation(value = "Retorna una lista de todos los pedidos")
    public ResponseEntity<List<PedidoDTO>> todos(){
        return ResponseEntity.ok(pedidoService.getAll());
    }


    @GetMapping(path = "/obra/{idObra}")
    @ApiOperation(value = "Busca una lista de pedidos por id de Obra")   //VER Y TERMINAR
    public ResponseEntity<PedidoDTO> getPedidoPorIdObra(@PathVariable Integer idObra){
        return ResponseEntity.ok(pedidoService.getPedidoPorIdObra(idObra));
    }

    @GetMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Busca un detalle de pedido por id de pedido e id de detalle dados")
    public ResponseEntity<DetallePedidoDTO> getPedidoPorDetalle(@PathVariable Integer idPedido, @PathVariable Integer id){
        return ResponseEntity.ok(pedidoService.getPedidoPorDetalle(idPedido, id));
    }

    @PostMapping
    @ApiOperation(value = "Da de alta un pedido")
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoRequestDTO pedido){
        return ResponseEntity.ok(pedidoService.crearPedido(pedido));
    }

    @PostMapping(path = "/{idPedido}/detalle")
    @ApiOperation(value = "Da de alta un detallePedido y lo añade un pedido por idPedido")
    public ResponseEntity<PedidoDTO> crearItem(@RequestBody DetallePedidoDTO nuevoItem, @PathVariable Integer idPedido){
        return ResponseEntity.ok(pedidoService.crearItem(nuevoItem, idPedido));
    }

    @PutMapping(path = "/{idPedido}")
    @ApiOperation(value = "Actualiza un pedido")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<PedidoDTO> actualizar(@RequestBody PedidoDTO nuevo,  @PathVariable Integer idPedido) {
        return pedidoService.actualizarPedido(nuevo, idPedido);
    }

    @DeleteMapping(path = "/{idPedido}")
    @ApiOperation(value = "Elimina un pedido")
    public ResponseEntity<PedidoDTO> borrar(@PathVariable Integer idPedido){
        return pedidoService.borrarPedido(idPedido);
    }

    @DeleteMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Elimina un detallePedido perteneciente a un determinado pedido. example:http://localhost:8080/api/pedido/5/detalle/11")
    public ResponseEntity<PedidoDTO> borrarDetalle(@PathVariable Integer idPedido, @PathVariable Integer id){
        return pedidoService.borrarDetalleDePedido(idPedido, id);
    }



}