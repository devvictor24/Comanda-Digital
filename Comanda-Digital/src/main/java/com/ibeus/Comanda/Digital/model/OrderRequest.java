package com.ibeus.Comanda.Digital.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderDishRequest> items;
    private PaymentMethod paymentMethod;

    public List<OrderDishRequest> getItems() {
        return items;
    }
    public void setItems(List<OrderDishRequest> items) {
        this.items = items;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
