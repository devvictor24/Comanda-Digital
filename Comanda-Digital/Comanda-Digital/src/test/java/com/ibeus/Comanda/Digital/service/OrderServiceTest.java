package com.ibeus.Comanda.Digital.service;

import com.ibeus.Comanda.Digital.model.*;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import com.ibeus.Comanda.Digital.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DishRepository dishRepository;
    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Order> orders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findAll()).thenReturn(orders);
        List<Order> result = orderService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindById_Success() {
        Order order = new Order();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order result = orderService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.findById(1L));
        assertTrue(ex.getMessage().contains("Pedido não encontrado"));
    }

    @Test
    void testCreate_Success() {
        OrderDishRequest req = new OrderDishRequest();
        req.setDishId(10L);
        req.setQuantity(2);
        Dish dish = new Dish();
        dish.setId(10L);
        dish.setPrice(new BigDecimal("5.00"));
        when(dishRepository.findById(10L)).thenReturn(Optional.of(dish));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        List<OrderDishRequest> items = Collections.singletonList(req);
        Order order = orderService.create(items, PaymentMethod.CASH);
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(PaymentMethod.CASH, order.getPaymentMethod());
        assertEquals(new BigDecimal("10.00"), order.getTotalPrice());
        assertEquals(1, order.getOrderItems().size());
    }

    @Test
    void testCreate_DishNotFound() {
        OrderDishRequest req = new OrderDishRequest();
        req.setDishId(99L);
        req.setQuantity(1);
        when(dishRepository.findById(99L)).thenReturn(Optional.empty());
        List<OrderDishRequest> items = Collections.singletonList(req);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.create(items, PaymentMethod.CREDIT_CARD));
        assertTrue(ex.getMessage().contains("Prato não encontrado"));
    }

    @Test
    void testUpdateStatus() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        Order updated = orderService.updateStatus(1L, OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, updated.getStatus());
    }

    @Test
    void testCancel() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        orderService.cancel(1L);
        assertEquals(OrderStatus.CANCELED, order.getStatus());
        verify(orderRepository).save(order);
    }
}