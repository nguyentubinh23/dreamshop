package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.enums.OrderStatus;
import com.dailycodework.dreamshops.model.Order;
import com.dailycodework.dreamshops.model.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDto> items;
}
