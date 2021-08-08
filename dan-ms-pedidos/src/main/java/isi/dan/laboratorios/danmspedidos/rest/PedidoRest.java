package isi.dan.laboratorios.danmspedidos.rest;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.PedidoDTO;
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

import isi.dan.laboratorios.danmspedidos.domain.DetallePedido;
import isi.dan.laboratorios.danmspedidos.domain.Pedido;

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

    //FALTA GET Por Cuit y/o ID de Cliente
    /**
     * Arreglar exchange
     */
    /*@GetMapping(path = "/cuitIdCliente")
    @ApiOperation(value = "Busca una lista de pedidos por cuit de cliente y/o ID de cliente. example:http://localhost:8080/api/pedido/cuitIdCliente?cuit=20406461272 ")
    @ResponseBody
    public ResponseEntity<List<Pedido>> pedidoPorCuitOIdCliente(@RequestParam(required = false) String cuit, @RequestParam(required = false)  Integer id) {
        Integer idCliente = null;
        if(cuit != null) {
            RestTemplate rest = new RestTemplate();
            String url = "http://localhost:8080/api/cliente/cuit/"+cuit;
            try {
                ResponseEntity<ClienteDTO> respuesta = rest.exchange(
                    url, HttpMethod.GET, null, ClienteDTO.class);
                ClienteDTO response = respuesta.getBody();
                idCliente = response.getId();
            } catch (Exception e){
                throw new RestClientException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
            }
        }

        if(id !=null || idCliente != null) {
            Integer cliente = (id!=null) ? id : idCliente;
            RestTemplate rest = new RestTemplate();
            String url = "http://localhost:8080/api/obra/obras?clienteId="+cliente;
            try {
                ResponseEntity<List<ObraDTO>> respuesta = rest.exchange(
                    url, HttpMethod.GET, null, ClienteDTO.class);
                List<ObraDTO> obras = respuesta.getBody();
                if(obras ==null || obras.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
                List<Integer> obrasId = obras.stream().map(ObraDTO::getId).collect(Collectors.toList());
                List<Pedido> o =  listaPedidos
                    .stream()
                    .filter(unPedido -> obrasId.contains(unPedido.getObra().getId()))
                    .collect(Collectors.toList());
                return ResponseEntity.ok(o);
            } catch (Exception e){
                throw new RestClientException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e);
            }
        
        } 
    }*/


    @GetMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Busca un detalle de pedido por id de pedido e id de detalle dados")
    public ResponseEntity<DetallePedidoDTO> getPedidoPorDetalle(@PathVariable Integer idPedido, @PathVariable Integer id){
        return ResponseEntity.ok(pedidoService.getPedidoPorDetalle(idPedido, id));
    }

    @PostMapping
    @ApiOperation(value = "Da de alta un pedido")
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO nuevo){
        return ResponseEntity.ok(pedidoService.crearPedido(nuevo));
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