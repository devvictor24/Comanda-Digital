package com.ibeus.Comanda.Digital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ibeus.Comanda.Digital.model.*;
import com.ibeus.Comanda.Digital.model.OrderRequest;
import com.ibeus.Comanda.Digital.model.OrderStatus;
import com.ibeus.Comanda.Digital.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/comanda-digital")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;


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
    public Order updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        return orderService.updateStatus(id, status);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}