package cn.ccc212.eduagent.pojo.vo.ai;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ChatVO {

    private String response;

    private String chatId;

}
