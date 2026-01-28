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

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://*.onrender.com",
        "https://*.vercel.app",
        "https://*.netlify.app"
})@Tag(name = "Order Controller", description = "Gerencia as comandas digitais (pedidos dos clientes)")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findById(id);
    }

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

    @PutMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        return orderService.cancel(id);
    }
}
