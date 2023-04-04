package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        boolean on = true;
        String name = update.getMessage().getFrom().getFirstName();
        String text = update.getMessage().getText() != null ? update.getMessage().getText() : "/start";
        Long chatId = update.getMessage().getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        if (text.equals("/start")){
            text =  "Hurmatli " +
                    name +
                    ", Iltimos suhbat mavzusini tanlang \uD83D\uDC47\uD83D\uDC47";
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("Kursga ro'yxatdan o'tish \uD83C\uDF93");
            row1.add("Men o'qituvchiman \uD83E\uDDD1\u200D\uD83C\uDFEB");
            KeyboardRow row2 = new KeyboardRow();
            row2.add("Brooklyn haqida ko'proq \uD83D\uDC81");
            row2.add("Boshqa \uD83E\uDD14");
            keyboardRowList.add(row1);
            keyboardRowList.add(row2);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
        }else {
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow row1 = new KeyboardRow();
            KeyboardRow row2 = new KeyboardRow();
            switch (text){
                case "Kursga ro'yxatdan o'tish \uD83C\uDF93":
                    text = "Bizning akademiyada o'qishni istayotganingizdan xursandmiz \uD83D\uDE0A \nIsmingiz va telfon raqamingizni qoldirsangiz admistratorlarimiz siz bilan bir kun ichida bog'lanishadi \n\nEx: Dehqonov Xolmatjon +9998912223355";
                    break;
                case "Tasdiqlash ✅":
                    text = "Ma'lumotlar tasdiqlandi ✅\nEtiboringiz uchun rahmat \uD83D\uDE0A";
                    break;
                case "Boshqa \uD83E\uDD14":
                    text = "Bizning akademiyaga bo'lgan qiziqishingizdan xursandmiz \uD83D\uDE0A \nSavolingizni yoki taklifingizni qoldirsangiz sizga bir kun ichida aloqaga chiqamiz!";
                    break;
                case "Men o'qituvchiman \uD83E\uDDD1\u200D\uD83C\uDFEB":
                    text ="Bizning akademiyada ishlashni istayotganingizdan xursandmiz \uD83D\uDE0A \nIsmingiz, telfon raqamiz va o'ziz haqizda qisqa ma'lumot qoldirsangiz admistratorlarimiz siz bilan bir kun ichida bog'lanishadi \n\nEx: Dehqonov Xolmatjon +9998912223355 IELTS 7";
                    break;
                case "Brooklyn haqida ko'proq \uD83D\uDC81":
                    text = "Brooklyn academy bu to'laqonli Ingiz tili o'rgatishga ihtisoslashgan, tajribali ustozlardan tashkil topgan IELTS academy \uD83D\uDE0A \n\nBiz haqimizda ko'proq ma'lumotni bizning rasmiy veb saytimizdan topishingiz mumkin \uD83D\uDC47\uD83D\uDC47 \nhttps://brooklyn-academy.netlify.app/";
                    break;
                default:
                    text = "'" + text +"'\n\nTasdiqlaysizmi ?";
                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                    row1.add("Tasdiqlash ✅");
                    keyboardRowList.add(row1);
                    replyKeyboardMarkup.setKeyboard(keyboardRowList);
                    break;
            }
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
        return "brooklynadminbot";
    }

    @Override
    public String getBotToken() {
        return "6132805585:AAESY7qfIYNBVm1jyitgLHq3jvmcgHJvCZk";
    }
}
