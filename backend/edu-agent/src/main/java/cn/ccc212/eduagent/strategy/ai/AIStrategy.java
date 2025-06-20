package cn.ccc212.eduagent.strategy.ai;

import cn.ccc212.eduagent.pojo.dto.ai.ChatDTO;
import cn.ccc212.eduagent.pojo.vo.ai.ChatVO;

public interface AIStrategy {

    ChatVO chat(ChatDTO chatDTO, String systemPrompt);

    ChatVO chatWithRAG(ChatDTO chatDTO, String systemPrompt);
}
