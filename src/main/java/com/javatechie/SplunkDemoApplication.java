package com.javatechie;

import com.javatechie.dto.JobStatus;
import com.javatechie.dto.Order;
import com.javatechie.service.JobService;
import com.javatechie.service.OrderService;
import com.javatechie.util.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/orders")
public class SplunkDemoApplication {

    Logger logger = LogManager.getLogger(SplunkDemoApplication.class);

    @Autowired
    private OrderService service;

    @Autowired
    private JobService jobService;

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        logger.info("OrderController:placeOrder persist order request {}", Mapper.mapToJsonString(order));
        Order addOrder = service.addOrder(order);
        logger.info("OrderController:placeOrder response from service {}", Mapper.mapToJsonString(addOrder));
        return addOrder;
    }

    @GetMapping
    public List<Order> getOrders() {
        List<Order> orders = service.getOrders();
        logger.info("OrderController:getOrders response from service {}", Mapper.mapToJsonString(orders));
        return orders;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable int id) {
        logger.info("OrderController:getOrder fetch order by id {}", id);
        Order order = service.getOrder(id);
        logger.info("OrderController:getOrder fetch order response {}", Mapper.mapToJsonString(order));
        return order;
    }


    @PostMapping("/job")
    public JobStatus processOrder(@RequestBody Order order) {
        logger.info("OrderController:processOrder  order request {}", Mapper.mapToJsonString(order));
        JobStatus jobStatus = jobService.process(order);
        logger.info("current job status {}", jobStatus);
        return jobStatus;
    }

    public static void main(String[] args) {
        SpringApplication.run(SplunkDemoApplication.class, args);
    }

}
