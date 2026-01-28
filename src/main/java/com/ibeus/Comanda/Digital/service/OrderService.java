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
        order.setTimestamp(LocalDateTime.now());

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

    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        OrderStatus current = order.getStatus();


        if (current == OrderStatus.DELIVERED || current == OrderStatus.CANCELED) {
            throw new RuntimeException("Pedido já finalizado");
        }


        switch (current) {

            case PENDING:
                if (newStatus != OrderStatus.IN_PREPARATION && newStatus != OrderStatus.CANCELED) {
                    throw new RuntimeException("Pedido PENDING só pode ir para IN_PREPARATION ou CANCELED");
                }
                break;

            case IN_PREPARATION:
                if (newStatus != OrderStatus.READY_FOR_DELIVERY && newStatus != OrderStatus.CANCELED) {
                    throw new RuntimeException("Pedido em preparo só pode ir para READY_FOR_DELIVERY ou CANCELED");
                }
                break;

            case READY_FOR_DELIVERY:
                if (newStatus != OrderStatus.OUT_FOR_DELIVERY) {
                    throw new RuntimeException("Pedido pronto só pode ir para OUT_FOR_DELIVERY");
                }
                break;

            case OUT_FOR_DELIVERY:
                if (newStatus != OrderStatus.DELIVERED) {
                    throw new RuntimeException("Pedido saiu para entrega só pode ir para DELIVERED");
                }
                break;

            default:
                throw new RuntimeException("Transição de status inválida");
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public Order cancel(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Pedido já foi entregue e não pode ser cancelado");
        }

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new RuntimeException("Pedido já está cancelado");
        }

        order.setStatus(OrderStatus.CANCELED);
        return orderRepository.save(order);
    }

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }

}