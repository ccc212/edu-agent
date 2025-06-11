package cn.ccc212.eduagent.utils;

import cn.ccc212.eduagent.context.CacheContext;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSenderImpl mailSender;

    private final CacheContext cacheContext;

    @Value("${spring.mail.username}")
    private String emailSender;

    @SneakyThrows
    public boolean mail(String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 生成随机验证码（6位）
        String code = UUID.randomUUID().toString().substring(0, 6);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // 设置一个html邮件信息
        String htmlContent = """
                <div style="background-color: #f6f6f6; padding: 20px;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                        <h2 style="color: #333; text-align: center; margin-bottom: 20px;">【教学智能体】账号注册验证码</h2>
                        <div style="border-top: 1px solid #eee; border-bottom: 1px solid #eee; padding: 20px 0; margin: 20px 0;">
                            <p style="font-size: 16px; color: #666; margin-bottom: 10px;">尊敬的用户：</p>
                            <p style="font-size: 14px; color: #666; line-height: 24px;">您好！您正在进行邮箱验证，验证码为：</p>
                            <p style="font-size: 24px; color: #2b85e4; font-weight: bold; text-align: center; margin: 20px 0;">%s</p>
                            <p style="font-size: 12px; color: #999;">验证码有效期为1小时，请尽快完成验证。</p>
                        </div>
                        <p style="font-size: 12px; color: #999; text-align: center;">本邮件由系统自动发送，请勿直接回复</p>
                    </div>
                </div>
                """.formatted(code);
        helper.setText(htmlContent, true);
        // 设置邮件主题名
        helper.setSubject("教学智能体-验证码");
        // 发给谁-》邮箱地址
        helper.setTo(email);
        // 谁发的-》发送人邮箱
        helper.setFrom(new InternetAddress(MimeUtility.encodeText("教学智能体") + "<" + emailSender + ">").toString());
        // 发送邮件
        try {
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            return false;
        }
        // 将邮箱验证码以邮件地址为key存入redis,1小时过期
        cacheContext.put("edu-agent:email:" + email, code, 1, TimeUnit.HOURS);
        return true;
    }
}
