package cn.ccc212.eduagent.consumer;

import cn.ccc212.eduagent.constant.RabbitMQConstant;
import cn.ccc212.eduagent.pojo.entity.OperLog;
import cn.ccc212.eduagent.service.IOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogConsumer {

    private final IOperLogService operLogService;

    @RabbitListener(queues = RabbitMQConstant.LOG_QUEUE)
    public void receiveLog(OperLog operLog) {
        operLogService.save(operLog);
    }
}