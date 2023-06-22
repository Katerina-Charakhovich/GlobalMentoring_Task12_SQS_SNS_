package com.epam.sns.order.mapper;

import com.epam.sns.order.dto.OrderDto;
import com.epam.sns.order.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public static OrderDto toOrderDto(OrderEntity orderEntity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderEntity.getId());
        orderDto.setType(orderEntity.getType());
        orderDto.setUser(orderEntity.getUser());
        orderDto.setType(orderEntity.getType());
        orderDto.setNumber(orderEntity.getNumber());
        orderDto.setVolume(orderEntity.getVolume());
        orderDto.setTotal(orderEntity.getTotal());
        orderDto.setComments(orderEntity.getComments());
        return orderDto;
    }

    public OrderEntity toOrderEntity(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        if (orderDto.getId() != null) {
            orderEntity.setId(orderDto.getId());
        }
        if (orderDto.getState() != null) {
            orderEntity.setState(orderDto.getState());
        }
        if (orderDto.getComments() != null) {
            orderEntity.setComments(orderDto.getComments());
        }
        orderEntity.setUser(orderDto.getUser());
        orderEntity.setType(orderDto.getType());
        orderEntity.setNumber(orderDto.getNumber());
        orderEntity.setVolume(orderDto.getVolume());
        orderEntity.setTotal(orderDto.getTotal());
        return orderEntity;
    }
}
