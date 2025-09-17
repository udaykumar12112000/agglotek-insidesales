package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.dao.entity.Payment;

public interface IPaymentService {
    Payment createOrUpdatePayment(Payment request);
}
