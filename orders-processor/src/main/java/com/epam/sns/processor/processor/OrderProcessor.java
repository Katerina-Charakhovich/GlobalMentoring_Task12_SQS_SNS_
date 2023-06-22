package com.epam.sns.processor.processor;

import com.epam.sns.processor.dto.OrderDto;
import com.epam.sns.processor.dto.OrderState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor {

    @Value("${order.threshold}")
    private int threshold;

    @Value("${order.liquids}")
    private int maxOderLiquids;

    private static int volumeLiquidsGoods = 0;

    private static final String REJECT_MORE_THRESHOLD ="The order total is greater than threshold";
    private static final String REJECT_MORE_MAX_LIQUIDS ="N liters have already been ordered The order amount is greater than threshold";

    public void processCountableOrder(OrderDto orderDto) {
        if (isMoreThreshold(orderDto.getTotal())){
            orderDto.setState(OrderState.REJECT);
            orderDto.setComment(REJECT_MORE_THRESHOLD);
        }
        else {
            orderDto.setState(OrderState.ACCEPTED);
        }
    }

    public void processLiquidsOrder(OrderDto orderDto) {
        if (isMoreThreshold(orderDto.getTotal())){
            orderDto.setState(OrderState.REJECT);
            orderDto.setComment(REJECT_MORE_THRESHOLD);
        }
        else {
            if (volumeLiquidsGoods + orderDto.getVolume() > maxOderLiquids) {
                orderDto.setState(OrderState.REJECT);
                orderDto.setComment(REJECT_MORE_MAX_LIQUIDS);

            }
            else {
                orderDto.setState(OrderState.ACCEPTED);
                volumeLiquidsGoods = volumeLiquidsGoods + orderDto.getVolume();
            }
        }
    }

    private boolean isMoreThreshold(int total) {
        return total-threshold> 0;
    }

}
