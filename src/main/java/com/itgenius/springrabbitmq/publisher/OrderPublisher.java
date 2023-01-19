package com.itgenius.springrabbitmq.publisher;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itgenius.springrabbitmq.Constants;
import com.itgenius.springrabbitmq.entity.Order;
import com.itgenius.springrabbitmq.entity.OrderStatus;
import com.itgenius.springrabbitmq.service.FileTransferService;

@RestController
@RequestMapping("/order")
public class OrderPublisher {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
	private FileTransferService fileTransferService;
	private Logger logger = LoggerFactory.getLogger(OrderPublisher.class);

    @PostMapping("/{shopName}")
    public String bookOrder(@RequestBody Order order, @PathVariable String shopName){
        
        order.setOrderId(UUID.randomUUID().toString());

        OrderStatus orderStatus = new OrderStatus(order, "PROCESS","Order Succcessfully " + shopName);

        // Sent Message to RabbitMQ
        rabbitTemplate.convertAndSend(Constants.EXCHANGE, Constants.ROUTING_KEY, orderStatus);

        return "Success!!";
        
    }

    @GetMapping("/callsftp")
    public void run(String... args) throws Exception {

		logger.info("Start download file");
        
		boolean isDownloaded = fileTransferService.downloadFile("/home/simplesolution/readme.txt", "/readme.txt");

		logger.info("Download result: " + String.valueOf(isDownloaded));
		
        logger.info("Start upload file");
		boolean isUploaded = fileTransferService.uploadFile("/home/simplesolution/readme.txt", "/readme2.txt");

		logger.info("Upload result: " + String.valueOf(isUploaded));

	}

    // public void callSftp(){

    //     SftpService sftpService = new SftpService();

    //     try {
    //         sftpService.connect();
    //         System.out.println("Connect Success");
    //     }catch(Exception e){
    //         System.out.println("Connect Fail");
    //     }
    
    // }


}

