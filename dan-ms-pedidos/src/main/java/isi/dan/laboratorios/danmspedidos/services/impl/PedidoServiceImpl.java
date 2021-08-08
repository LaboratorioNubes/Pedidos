package isi.dan.laboratorios.danmspedidos.services.impl;

import isi.dan.laboratorios.danmspedidos.domain.*;
import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.EstadoPedidoDTO;
import isi.dan.laboratorios.danmspedidos.dtos.ObraDTO;
import isi.dan.laboratorios.danmspedidos.dtos.PedidoDTO;
import isi.dan.laboratorios.danmspedidos.exceptions.DataNotFoundException;
import isi.dan.laboratorios.danmspedidos.repositories.PedidoRepository;
import isi.dan.laboratorios.danmspedidos.services.ClienteService;
import isi.dan.laboratorios.danmspedidos.services.MaterialService;
import isi.dan.laboratorios.danmspedidos.services.PedidoService;
import isi.dan.laboratorios.danmspedidos.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    MaterialService materialSrv;

    @Autowired
    PedidoRepository repo;

    @Autowired
    ClienteService clienteSrv;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ListMapper listMapper;

    private static Integer indexPedido;

    @Override
    public PedidoDTO crearPedido(PedidoDTO p) {
        System.out.println("HOLA PEDIDO "+p);
        boolean hayStock = p.getDetallesPedido()
                .stream()
                .allMatch(dp -> verificarStock(dp.getProducto(),dp.getCantidad()));

        Double totalOrden = p.getDetallesPedido()
                .stream()
                .mapToDouble( dp -> dp.getCantidad() * dp.getPrecio())
                .sum();


        Double saldoCliente = clienteSrv.deudaCliente(p.getObra());
        Double nuevoSaldo = saldoCliente - totalOrden;

        Boolean generaDeuda= nuevoSaldo<0;
        if(hayStock ) {
            if(!generaDeuda || (generaDeuda && this.esDeBajoRiesgo(p.getObra(),nuevoSaldo) ))  {
                p.setEstado(new EstadoPedidoDTO(1,"ACEPTADO"));
            } else {
                throw new RuntimeException("No tiene aprobacion crediticia");
            }
        } else {
            p.setEstado(new EstadoPedidoDTO(2,"PENDIENTE"));
        }

        repo.save(modelMapper.map(p, Pedido.class));

        return p;
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


    private boolean verificarStock(Producto p, Integer cantidad) {
        return materialSrv.stockDisponible(p)>=cantidad;
    }

    private boolean esDeBajoRiesgo(ObraDTO o, Double saldoNuevo) {
        Double maximoSaldoNegativo = clienteSrv.maximoSaldoNegativo(o);
        Boolean tieneSaldo = Math.abs(saldoNuevo) < maximoSaldoNegativo;
        return tieneSaldo;
    }


}
