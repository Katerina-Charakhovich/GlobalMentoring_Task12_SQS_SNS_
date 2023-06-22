package com.epam.sns.order.service;

import com.epam.sns.order.dao.OrderRepository;
import com.epam.sns.order.dto.OrderDto;
import com.epam.sns.order.dto.OrderState;
import com.epam.sns.order.entity.OrderEntity;
import com.epam.sns.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private NotificationService notificationService;

    @Transactional
    public ResponseEntity<?> create(OrderDto orderDto) {
        OrderEntity entityModel = orderMapper.toOrderEntity(orderDto);
        try {
            orderRepository.save(entityModel);
            long id = entityModel.getId();
            orderDto.setId(id);
            orderDto.setState(OrderState.NEW);
            notificationService.sendMessageToTopic(orderMapper.toOrderDto(entityModel));
            return new ResponseEntity<>(Map.of("id", id), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<?> get(Long id) {
        Optional<OrderEntity> entityModel = orderRepository.findById(id);
        OrderDto dto = entityModel.isPresent() ? orderMapper.toOrderDto(entityModel.get()) : null;
        return new ResponseEntity<>(dto == null ? "Not exist" : dto, HttpStatus.OK);
    }

    @Transactional
    public void update(OrderDto orderDto) {
        OrderEntity entityModel = orderMapper.toOrderEntity(orderDto);
        orderRepository.save(entityModel);
    }
}
