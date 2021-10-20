package isi.dan.laboratorios.danmspedidos.services.impl;

import isi.dan.laboratorios.danmspedidos.dtos.ObraDTO;
import isi.dan.laboratorios.danmspedidos.services.IClienteService;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Override
    public Double deudaCliente(String idObra) {
        return 100000.0;
    }

    @Override
    public Integer situacionCrediticiaBCRA(ObraDTO id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Double maximoSaldoNegativo(String id) {
        return 100.0;
    }

}