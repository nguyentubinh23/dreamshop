package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.model.CartItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
public class CartDto {
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItemDto> items;
}
