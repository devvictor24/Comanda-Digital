package com.ibeus.Comanda.Digital.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<Long> dishIds;
    private PaymentMethod paymentMethod;


}
