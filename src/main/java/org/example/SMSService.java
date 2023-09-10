package org.example;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

public class SMSService {
    VonageClient client = VonageClient.builder().apiKey("f2122abc").apiSecret("tgpEWI9fFXCaMkV2").build();

    public Boolean send(SMS smsDto,String userId){
        TextMessage message = new TextMessage(smsDto.getName(),
                smsDto.getNumber(),
                smsDto.getText()+"\n\n\n\nUshbu SMS https://t.me/hazilsmsbot tomonidan jo'natildi! id:"+userId
        );

        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

        return response.getMessages().get(0).getStatus() == MessageStatus.OK;
    }
}
