package com.yali.message;

import com.yali.api.dto.sms.SmsInfoDTO;
import com.yali.common.autoconfigure.mq.RabbitMqHelper;
import com.yali.common.constants.MqConstants;
import com.yali.common.utils.CollUtils;
import com.yali.common.utils.RandomUtils;
import com.yali.message.domain.enums.SmsTemplate;
import com.yali.message.service.ISmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yali.api.constants.SmsConstants.VERIFY_CODE_PARAM_NAME;

/**
 * @author fuqianlin
 * @date 2023-11-22 15:48
 **/
@SpringBootTest
public class ISmsHandlerTest {
    @Autowired
    private ISmsService smsService;
    @Autowired
    private RabbitMqHelper mqHelper;


    @Test
    void send() {
        SmsInfoDTO dto = new SmsInfoDTO();
        dto.setPhones(List.of("13901517624", "15162153483"));
        dto.setTemplateCode(SmsTemplate.VERIFY_CODE.name());
        HashMap<String, String> params = new HashMap<>(1);
        params.put("code", "518518");
        dto.setTemplateParams(params);
        smsService.sendMessage(dto);
    }

    @Test
    void sendCode() {
        com.yali.message.domain.dto.SmsInfoDTO info = new com.yali.message.domain.dto.SmsInfoDTO();
        String phone = "13901517624";
        info.setPhones(CollUtils.singletonList(phone));
        info.setTemplateCode(SmsTemplate.VERIFY_CODE.toString());
        Map<String, String> params = new HashMap<>(1);
        String code = RandomUtils.randomNumbers(4);
        params.put(VERIFY_CODE_PARAM_NAME, code);
        info.setTemplateParams(params);
        mqHelper.send(MqConstants.Exchange.SMS_EXCHANGE, MqConstants.Key.SMS_MESSAGE, info);
    }
}
