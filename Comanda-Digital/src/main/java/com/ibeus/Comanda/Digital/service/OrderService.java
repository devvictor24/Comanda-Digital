package com.ibeus.Comanda.Digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibeus.Comanda.Digital.model.*;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.DishRepository;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishRepository dishRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public Order create(List<OrderDishRequest> items, PaymentMethod paymentMethod) {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(paymentMethod);

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new java.util.ArrayList<>();
        for (OrderDishRequest item : items) {
            Dish dish = dishRepository.findById(item.getDishId())
                    .orElseThrow(() -> new RuntimeException("Prato não encontrado: " + item.getDishId()));
            OrderItem orderItem = new OrderItem();
            orderItem.setDish(dish);
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
            total = total.add(dish.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    public Order updateStatus(Long id, OrderStatus status) {
        Order order = findById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void cancel(Long id) {
        Order order = findById(id);
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
    public String getLocalDateTime() { // <-- MUDANÇA: Agora retorna String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}