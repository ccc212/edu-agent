package cn.ccc212.eduagent.strategy.log.impl;

import cn.ccc212.eduagent.constant.RabbitMQConstant;
import cn.ccc212.eduagent.pojo.entity.OperLog;
import cn.ccc212.eduagent.strategy.log.LogStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqLogStrategyImpl implements LogStrategy {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void log(OperLog operLog) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.convertAndSend(RabbitMQConstant.LOG_QUEUE, operLog);
    }
}
