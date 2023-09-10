package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class Bot extends TelegramLongPollingBot {
    public String assistBot = "@Hazil_sms_bot";
    public boolean on = true;
    Admin admin = new Admin();
    Checker checker = new Checker();
    Hashtable<String,SMS> sms = new Hashtable<>();
    SMSService smsService = new SMSService();
    private int users = 0;
    Set<String> promoCodes = new HashSet<>();

    public ApiResponse adminService(String input) {
        Set<String> response = new HashSet<>();
        String text = "Admin panel";

        switch (input){
            case "/start" :
                response.add("Promo code ⚙\uFE0F");
                response.add("Admin ➕");
                response.add("Users \uD83D\uDC65");
                break;
            case "Users \uD83D\uDC65" :
                text = "Starts: "+users+
                    "\nUsers: "+sms.size()+
                    "\nPromo codes: "+promoCodes.size();
                break;

            case "Admin ➕" :
                text = "Enter the admin id \uD83D\uDC47\uD83D\uDC47";
                break;

            case "/refresh" :
                users=0;
                sms.clear();
                text = "Bot is refreshed!";
                break;

            case "/switch" :
                text = on?"Bot is off ":"Bot is on";
                on = !on;
                break;

            case "Promo code ⚙\uFE0F" :
                Random random = new Random();
                int code = random.nextInt(999999);
                promoCodes.add(String.valueOf(code));
                text = "Promo code: " + code;
                break;

            default:
                if (input.matches("[0-9]+")) {
                    text = "New admin is successfully added ✅";
                    admin.addAdmin(input);
                    break;
                }
                if (input.startsWith("@")){
                    text = "Assist bot is changed from "+ assistBot +" to "+ input;
                    assistBot=input;
                    break;
                }
                else {
                    text = "\uD83E\uDD37\u200D♂\uFE0F `"+input+"`";
                    break;
                }
        }
        return new ApiResponse(text, response);
    }

    public ApiResponse userService(String input, String userId){
        Set<String> response = new HashSet<>();
        String text = "Hazil SMS botiga hush kelibsiz, quyida bot shartlari bilan tanishib chiqing va shartlarga rozi ekanligizni tasdiqlang \uD83D\uDC47\uD83D\uDC47"+
                "\n\n✅ Bot dan O'zbekiston davlat qonunlariga qarshi xolatda foydalanish taqiqlanadi xususan fuqorolarni aldash yo'li bilan kartadan noqonuniy pul yechish."+
                "\n\n✅ Bot dan har qanday turdagi havola(link) jo'natish taqiqlanadi."+
                "\n\n✅ SMS nomi sifatida davlat organlari nomidan foydalanish taqiqlanadi.";

        switch (input) {
            case "/start":
                users++;
                response.add("Shartlarga roziman ✅");
                break;

            case "/id":
                text=userId;
                break;

            case "Shartlarga roziman ✅":
                sms.put(userId,new SMS(false, input, null, null, null));
                text = "Promo kod kiriting ✉\uFE0F\n\n Yoki sotib oling \uD83D\uDC47 \n"+assistBot;
                response.clear();
                break;

            default:
                if (input.matches("[0-9]+") && input.length() == 6){
                    if (promoCodes.contains(input)){
                        sms.get(userId).setAllowed(true);
                        promoCodes.remove(input);
                        text = "\uD83D\uDCF1 SMS jo'natmoqchi bo'lgan raqamizni kiriting! \nex: +998912223344";
                    }else text ="🤦 Bu kod ishlatilgan";
                    break;
                }
                if (input.startsWith("+") && sms.get(userId).isAllowed()){
                    boolean checkerResponse = checker.numberChecker(input);
                    if (!checkerResponse){
                        text = "Raqam xato kiritildi. \nQayta uruning \uD83D\uDC47";
                        break;
                    }
                     sms.get(userId).setNumber(input);
                     text = "\uD83D\uDE4D\u200D♂\uFE0F SMS qanday nom bilan borishini hohlaysiz? \nex: Qodirali, MCHS";
                     break;
                }
                if (sms.get(userId).getNumber()!=null && sms.get(userId).getName()==null && sms.get(userId).isAllowed()){
                    CheckerResponse checkerResponse = checker.messageChecker(input);
                    if (!checkerResponse.isSuccess()){
                        text = "Ta'qiqlangan so'z ishlatdingiz `"+ checkerResponse.getMessage() +"`. \nQayta uruning \uD83D\uDC47";
                        break;
                    }
                    sms.get(userId).setName(input);
                     text = "\uD83D\uDE4D\u200D♂\uFE0F To'liq SMS habarni kiriting \nex: Salom...";
                    break;
                }
                if (sms.get(userId).getName()!=null && sms.get(userId).getNumber()!=null && sms.get(userId).isAllowed()){
                    CheckerResponse checkerResponse = checker.messageChecker(input);
                    if (!checkerResponse.isSuccess()){
                        text = "Ta'qiqlangan so'z ishlatdingiz `"+ checkerResponse.getMessage() +"`. \nQayta uruning \uD83D\uDC47";
                        break;
                    }
                    sms.get(userId).setText(input);
                    boolean send = smsService.send(sms.get(userId), userId);
                    if (send) {
                        text = "\uD83C\uDF89 SMS muvaffaqiyatli jo'natildi\nKeyingi SMS uchun promo kod kiriting \uD83D\uDC47";
                    }else {
                        text = "\uD83E\uDD26\u200D♂\uFE0F Texnik xato, keyinroq urunib ko'ring!\nPromo kodni qayta kiriting \uD83D\uDC47";
                        promoCodes.add(sms.get(userId).getCode());
                    }
                    sms.get(userId).setAllowed(false);
                    break;
                }
                else {
                    text = "\uD83E\uDD37\u200D♂\uFE0F `"+input+"`";
                    break;
                }
        }

        return new ApiResponse(text, response);
    }


    @Override
    public void onUpdateReceived(Update update) {
        admin.addAdmin("5866090182");
        Boolean isBot = update.getMessage().getFrom().getIsBot();
        String userId = String.valueOf(update.getMessage().getFrom().getId());
        String text = update.getMessage().getText() != null ? update.getMessage().getText() : "/start";
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);

        //FOR ADMIN
        if (isBot) return;
        if (admin.getAdminList().contains(userId)){
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            ApiResponse service = adminService(text);
            text = service.getText();
            for (String word : service.getSet()) {
                row1.add(word);
            }
            keyboardRowList.add(row1);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);

        //FOR USER
        }else {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            ApiResponse service = userService(text, userId);
            text = service.getText();
            for (String word : service.getSet()) {
                row1.add(word);
            }
            keyboardRowList.add(row1);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
        }
        sendMessage.setText(text);


        try {
            if (on){
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "hazilsmsbot";
    }

    @Override
    public String getBotToken() {
        return "6477541750:AAHv5W1BJrNxnpZdNl7Y5JDkfA9FzgDDwqk";
    }
}
