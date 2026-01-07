package com.ibeus.Comanda.Digital.controller;

import com.ibeus.Comanda.Digital.model.*;
import com.ibeus.Comanda.Digital.repository.DishRepository;
import com.ibeus.Comanda.Digital.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateOrderWithQuantity2() throws Exception {
        // Cria e salva um prato
        Dish dish = new Dish();
        dish.setName("Pizza");
        dish.setDescription("Pizza de queijo");
        dish.setPrice(new BigDecimal("30.00"));
        dish.setImageUrl("img.jpg");
        dish.setQuantity(10);
        dish = dishRepository.save(dish);

        // Monta o pedido com quantidade 2
        OrderDishRequest item = new OrderDishRequest();
        item.setDishId(dish.getId());
        item.setQuantity(2);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(item));
        orderRequest.setPaymentMethod(PaymentMethod.CASH);

        // Envia requisição para criar pedido
        MvcResult result = mockMvc.perform(post("/api/comanda-digital")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andReturn();

        // Valida o retorno
        String response = result.getResponse().getContentAsString();
        Order order = objectMapper.readValue(response, Order.class);
        Assertions.assertNotNull(order.getId());
        Assertions.assertEquals(1, order.getOrderItems().size());
        Assertions.assertEquals(2, order.getOrderItems().get(0).getQuantity());
        Assertions.assertEquals(new BigDecimal("60.00"), order.getTotalPrice());
    }
}
