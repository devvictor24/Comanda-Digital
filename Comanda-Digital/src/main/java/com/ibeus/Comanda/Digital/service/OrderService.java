package com.ibeus.Comanda.Digital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibeus.Comanda.Digital.model.*;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.DishRepository;

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

    public Order create(List<Long> dishIds, PaymentMethod paymentMethod) {
        List<Dish> dishes = dishRepository.findAllById(dishIds);

        double total = dishes.stream().mapToDouble(Dish::getPrice).sum();

        Order order = new Order();
        order.setDishes(dishes);
        order.setTotalPrice(total);
        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.PENDING);

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
}
