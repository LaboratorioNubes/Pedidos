package isi.dan.laboratorios.danmspedidos.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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

    static final String API_PEDIDO = "/api/pedido";
    
    private static final List<Pedido> listaPedidos = new ArrayList<Pedido>();
    private static Integer ID_GEN = 1;
    private static Integer indexPedido;

    @GetMapping(path = "/{idPedido}")
    @ApiOperation(value = "Retorna un pedido por id")
    public ResponseEntity<Pedido> pedidoPorId(@PathVariable Integer idPedido){

        Optional<Pedido> p =  listaPedidos
                .stream()
                .filter(unPe -> unPe.getId().equals(idPedido))
                .findFirst();
        return ResponseEntity.of(p);
    }

    @GetMapping
    @ApiOperation(value = "Retorna una lista de todos los pedidos")
    public ResponseEntity<List<Pedido>> todos(){
        return ResponseEntity.ok(listaPedidos);
    }

    //GET por id de obra
    @GetMapping(path = "/obra/{idObra}")
    @ApiOperation(value = "Busca una lista de pedidos por id de Obra")   //VER Y TERMINAR
    public ResponseEntity<Pedido> pedidoPorIdObra(@PathVariable Integer idObra){

        Optional<Pedido> p =  listaPedidos
                .stream()
                .filter(unPe -> unPe.getObra().getId().equals(idObra))
                .findFirst();
        return ResponseEntity.of(p);
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
    public ResponseEntity<DetallePedido> pedidoPorDetalle(@PathVariable Integer idPedido, @PathVariable Integer id){

        DetallePedido detallePedido = new DetallePedido();

        for(int i=0; i<listaPedidos.size(); i++){
            for(int j=0; j<listaPedidos.get(i).getDetallesPedido().size(); j++){
                if(listaPedidos.get(i).getId().equals(idPedido) && listaPedidos.get(i).getDetallesPedido().get(j).getId().equals(id)){
                    detallePedido = listaPedidos.get(i).getDetallesPedido().get(j);
                    break;
                }
            }
        }

        return ResponseEntity.ok(detallePedido);
       
    }

    @PostMapping
    @ApiOperation(value = "Da de alta un pedido")
    public ResponseEntity<Pedido> crear(@RequestBody Pedido nuevo){
    	System.out.println("Crear pedido "+ nuevo);
        nuevo.setId(ID_GEN++);
        listaPedidos.add(nuevo);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping(path = "/{idPedido}/detalle")
    @ApiOperation(value = "Da de alta un detallePedido y lo añade un pedido por idPedido")
    public ResponseEntity<Pedido> crearItem(@RequestBody DetallePedido nuevoItem, @PathVariable Integer idPedido){
    	System.out.println("Crear item " + nuevoItem + " a pedido de id: " + idPedido);
        Pedido pedido = null;
        for(int i=0; i<listaPedidos.size(); i++){
            
            if(listaPedidos.get(i).getId().equals(idPedido)){
                listaPedidos.get(i).setItemDetallePedido(nuevoItem);
                pedido = listaPedidos.get(i);
                break;
            }

        }
        return ResponseEntity.ok(pedido);
    }

    @PutMapping(path = "/{idPedido}")
    @ApiOperation(value = "Actualiza un pedido")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Actualizado correctamente"),
        @ApiResponse(code = 401, message = "No autorizado"),
        @ApiResponse(code = 403, message = "Prohibido"),
        @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Pedido> actualizar(@RequestBody Pedido nuevo,  @PathVariable Integer idPedido) {
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.set(indexOpt.getAsInt(), nuevo);
            return ResponseEntity.ok(nuevo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}")
    @ApiOperation(value = "Elimina un pedido")
    public ResponseEntity<Pedido> borrar(@PathVariable Integer idPedido){
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
        .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idPedido}/detalle/{id}")
    @ApiOperation(value = "Elimina un detallePedido perteneciente a un determinado pedido. example:http://localhost:8080/api/pedido/5/detalle/11")
    public ResponseEntity<Pedido> borrarDetalle(@PathVariable Integer idPedido, @PathVariable Integer id){
        //final int indexPedido = 0;
        for(int i=0; i<listaPedidos.size(); i++){
            
            if(listaPedidos.get(i).getId().equals(idPedido)){
                indexPedido = i;
                break;
            }
        } 
        
        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.get(indexPedido).getDetallesPedido().size())
        .filter(i -> listaPedidos.get(indexPedido).getDetallesPedido().get(i).getId().equals(id))
        .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.get(indexPedido).getDetallesPedido().remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}