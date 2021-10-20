package isi.dan.laboratorios.danmspedidos.dtos.requests;
import lombok.Getter;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
public class PedidoRequestDTO {
    private String obra;
    private Date fechaPedido;
    private List<ItemsRequestDTO> items;
    private Integer total;
}
