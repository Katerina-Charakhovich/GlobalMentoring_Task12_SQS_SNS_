package com.epam.sns.order.entity;

import com.epam.sns.order.dto.GoodsType;
import com.epam.sns.order.dto.OrderState;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "user")
        private String user;

        @Column(name = "type")
        @Enumerated(EnumType.STRING)
        private GoodsType type;

        @Column(name = "volume")
        private int volume;

        @Column(name = "number")
        private int number;

        @Column(name = "total")
        private int total;

        @Column(name = "state")
        @Enumerated(EnumType.STRING)
        private OrderState state;

        @Column(name = "comments")
        private String comments;
    }

