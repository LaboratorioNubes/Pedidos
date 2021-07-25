package isi.dan.laboratorios.danmspedidos.services.impl;

import isi.dan.laboratorios.danmspedidos.domain.EstadoPedido;
import isi.dan.laboratorios.danmspedidos.domain.Obra;
import isi.dan.laboratorios.danmspedidos.domain.Pedido;
import isi.dan.laboratorios.danmspedidos.domain.Producto;
import isi.dan.laboratorios.danmspedidos.repositories.PedidoRepository;
import isi.dan.laboratorios.danmspedidos.services.ClienteService;
import isi.dan.laboratorios.danmspedidos.services.MaterialService;
import isi.dan.laboratorios.danmspedidos.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    MaterialService materialSrv;

    @Autowired
    PedidoRepository repo;

    @Autowired
    ClienteService clienteSrv;


    @Override
    public Pedido crearPedido(Pedido p) {
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
                p.setEstado(new EstadoPedido(1,"ACEPTADO"));
            } else {
                throw new RuntimeException("No tiene aprobacion crediticia");
            }
        } else {
            p.setEstado(new EstadoPedido(2,"PENDIENTE"));
        }
        return this.repo.save(p);
    }


    private boolean verificarStock(Producto p, Integer cantidad) {
        return materialSrv.stockDisponible(p)>=cantidad;
    }

    private boolean esDeBajoRiesgo(Obra o, Double saldoNuevo) {
        Double maximoSaldoNegativo = clienteSrv.maximoSaldoNegativo(o);
        Boolean tieneSaldo = Math.abs(saldoNuevo) < maximoSaldoNegativo;
        return tieneSaldo;
    }


}
