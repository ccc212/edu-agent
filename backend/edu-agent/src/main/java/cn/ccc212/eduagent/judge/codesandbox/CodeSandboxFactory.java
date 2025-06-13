package cn.ccc212.eduagent.judge.codesandbox;

import cn.ccc212.eduagent.judge.codesandbox.impl.ExampleCodeSandbox;
import cn.ccc212.eduagent.judge.codesandbox.impl.RemoteCodeSandbox;
import cn.ccc212.eduagent.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂 (根据字符串参数创建制定的代码沙箱实例)
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱示例
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            case "example":
            default:
                return new ExampleCodeSandbox();
        }
    }
}
