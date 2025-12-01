package com.ibeus.Comanda.Digital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ibeus.Comanda.Digital.model.*;
import com.ibeus.Comanda.Digital.model.OrderRequest;
import com.ibeus.Comanda.Digital.model.OrderStatus;
import com.ibeus.Comanda.Digital.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/comanda-digital")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Order Controller", description = "Gerencia as comandas digitais (pedidos dos clientes)")
public class OrderController {



    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @Operation(summary = "Listar todas as comandas", description = "Retorna todas as comandas criadas no sistema.")
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @Operation(summary = "Buscar comanda por ID", description = "Retorna os detalhes de uma comanda específica pelo seu ID.")
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @Operation(summary = "Obter data e hora atual do servidor", description = "Retorna a data e hora local do servidor que executa o sistema.")
    @GetMapping("/data-hora")
    public ResponseEntity<String> getLocalDateTime() {
        String dateTime = orderService.getLocalDateTime();
        return ResponseEntity.ok(dateTime);
    }

    @Operation(summary = "Criar nova comanda", description = "Cria uma nova comanda digital com os itens e método de pagamento informados.")
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.create(request.getItems(), request.getPaymentMethod());
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(
            @PathVariable Long id,
            @RequestParam("status") OrderStatus status) {
      return orderService.updateStatus(id, status);
    }

    @Operation(summary = "Cancelar comanda", description = "Remove uma comanda digital existente do sistema pelo ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}