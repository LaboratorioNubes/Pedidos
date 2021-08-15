package isi.dan.laboratorios.danmspedidos.services;

import isi.dan.laboratorios.danmspedidos.dtos.DetallePedidoDTO;

public interface IDetallePedidoService {
    DetallePedidoDTO crearItems(DetallePedidoDTO detallePedido);

    DetallePedidoDTO actualizarItems(DetallePedidoDTO detallePedido);

    void eliminarItems(int id);
}
