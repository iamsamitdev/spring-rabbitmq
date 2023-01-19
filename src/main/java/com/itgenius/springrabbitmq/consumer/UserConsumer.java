package com.itgenius.springrabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.itgenius.springrabbitmq.Constants;
import com.itgenius.springrabbitmq.entity.OrderStatus;;

@Component
public class UserConsumer {
    
    @RabbitListener(queues = Constants.QUEUE)
    public void consumerMessageFromQueue(OrderStatus orderStatus){
        System.out.println("Message Recieve from queue "+ orderStatus);
    }
}
