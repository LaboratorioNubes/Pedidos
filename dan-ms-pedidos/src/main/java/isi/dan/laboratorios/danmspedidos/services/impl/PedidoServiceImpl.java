package isi.dan.laboratorios.danmspedidos.services.impl;

import isi.dan.laboratorios.danmspedidos.domain.*;
import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.PedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.requests.PedidoRequestDTO;
import isi.dan.laboratorios.danmspedidos.dtos.responses.ProductoResponseDTO;
import isi.dan.laboratorios.danmspedidos.enums.Estado;
import isi.dan.laboratorios.danmspedidos.exceptions.DataNotFoundException;
import isi.dan.laboratorios.danmspedidos.repositories.PedidoRepository;
import isi.dan.laboratorios.danmspedidos.services.IClienteService;
import isi.dan.laboratorios.danmspedidos.services.IMaterialService;
import isi.dan.laboratorios.danmspedidos.services.IPedidoService;
import isi.dan.laboratorios.danmspedidos.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    IMaterialService materialSrv;

    @Autowired
    PedidoRepository repo;

    @Autowired
    IClienteService clienteSrv;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ListMapper listMapper;

    private static Integer indexPedido;

    @Override
    public PedidoDTO crearPedido(PedidoRequestDTO pedido) {
        List<ProductoResponseDTO> productList = pedido.getDetallesPedido()
                .stream()
                .map(dp -> verificarStock(dp.getIdProducto(),dp.getCantidad()))
                .collect(Collectors.toList());

        Double totalOrden = pedido.getDetallesPedido()
                .stream()
                .mapToDouble( dp -> dp.getCantidad() * (productList.stream()
                                                        .filter(p -> p.getId().equals(dp.getIdProducto()))
                                                        .findFirst()).get().getPrecio())
                .sum();

        Double saldoCliente = clienteSrv.deudaCliente(pedido.getIdObra());
        Double nuevoSaldo = saldoCliente - totalOrden;

        Pedido p = new Pedido();

        Boolean generaDeuda = nuevoSaldo<0;

        if(!productList.isEmpty()) {
            if(!generaDeuda || (generaDeuda && this.esDeBajoRiesgo(pedido.getIdObra(),nuevoSaldo)))  {
                p.setEstado(new EstadoPedido(1, Estado.ACEPTADO));
            } else {
                throw new RuntimeException("No tiene aprobacion crediticia");
            }
        } else {
            p.setEstado(new EstadoPedido(2,Estado.PENDIENTE));
        }

        return modelMapper.map(repo.save(p), PedidoDTO.class);
    }

    @Override
    public ResponseEntity<PedidoDTO> actualizarPedido(PedidoDTO pedido, Integer idPedido) {
        List<PedidoDTO> listaPedidos = getAll();

        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
                .filter(i -> listaPedidos.get(i).getId().equals(idPedido))
                .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.set(indexOpt.getAsInt(), pedido);
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<PedidoDTO> borrarPedido(Integer id) {
        List<PedidoDTO> listaPedidos = getAll();

        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.size())
                .filter(i -> listaPedidos.get(i).getId().equals(id))
                .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<PedidoDTO> borrarDetalleDePedido(Integer id, Integer idDetalle) {
        List<PedidoDTO> listaPedidos = getAll();

        for(int i=0; i<listaPedidos.size(); i++){

            if(listaPedidos.get(i).getId().equals(id)){
                indexPedido = i;
                break;
            }
        }

        OptionalInt indexOpt =   IntStream.range(0, listaPedidos.get(indexPedido).getDetallesPedido().size())
                .filter(i -> listaPedidos.get(indexPedido).getDetallesPedido().get(i).getId().equals(idDetalle))
                .findFirst();

        if(indexOpt.isPresent()){
            listaPedidos.get(indexPedido).getDetallesPedido().remove(indexOpt.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public PedidoDTO getPedidoPorId(Integer id) {

        Optional<Pedido> pedido = repo.findById(id);

        if(!pedido.isPresent()){
            throw new DataNotFoundException("El pedido con id:" + id + " no existe");
        }

        return modelMapper.map(pedido.get(), PedidoDTO.class);
    }

    @Override
    public PedidoDTO getPedidoPorIdObra(Integer id) {
        List<PedidoDTO> lisatPedidos = getAll();

        Optional<PedidoDTO> pedido =  lisatPedidos
                .stream()
                .filter(unPe -> unPe.getObra().getId().equals(id))
                .findFirst();

        if(!pedido.isPresent()){
            throw new DataNotFoundException("El pedidco con id de obra:" + id + " no existe");
        }

        return pedido.get();
    }

    @Override
    public List<PedidoDTO> getAll() {
        List<Pedido> pedidos = new ArrayList<>();

        Iterable<Pedido> iterable = repo.findAll();
        iterable.forEach(pedidos::add);

        return listMapper.mapList(pedidos, PedidoDTO.class);
    }

    @Override
    public DetallePedidoDTO getPedidoPorDetalle(Integer idPedido, Integer id) {

        List<PedidoDTO> listaPedidos = getAll();

        DetallePedidoDTO detallePedido = new DetallePedidoDTO();

        for(int i=0; i<listaPedidos.size(); i++){
            for(int j=0; j<listaPedidos.get(i).getDetallesPedido().size(); j++){
                if(listaPedidos.get(i).getId().equals(idPedido) && listaPedidos.get(i).getDetallesPedido().get(j).getId().equals(id)){
                    detallePedido = listaPedidos.get(i).getDetallesPedido().get(j);
                    break;
                }
            }
        }
//
        return detallePedido;
    }

    @Override
    public PedidoDTO crearItem(DetallePedidoDTO nuevoItem, Integer idPedido) {
        List<PedidoDTO> listaPedidos = getAll();

        System.out.println("Crear item " + nuevoItem + " a pedido de id: " + idPedido);

        PedidoDTO pedido = new PedidoDTO();

        for(int i=0; i<listaPedidos.size(); i++){

            if(listaPedidos.get(i).getId().equals(idPedido)){
                listaPedidos.get(i).setItemDetallePedido(nuevoItem);
                pedido = listaPedidos.get(i);
                break;
            }

        }

        return pedido;
    }


    private ProductoResponseDTO verificarStock(Integer idProducto, Integer cantidad) {
        return materialSrv.stockDisponible(idProducto, cantidad);
    }

    private boolean esDeBajoRiesgo(Integer idObra, Double saldoNuevo) {
        Double maximoSaldoNegativo = clienteSrv.maximoSaldoNegativo(idObra);
        Boolean tieneSaldo = Math.abs(saldoNuevo) < maximoSaldoNegativo;
        return tieneSaldo;
    }
}
