package org.example;

import org.glassfish.grizzly.nio.transport.DefaultStreamReader;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main extends TelegramLongPollingBot {
    // name: AMF_Bot
    // username: AMF_new_Bot
    // Use this token to access the HTTP API:
    //5833023447:AAFF_JdHU7BHl0TTCq8IefhTOGHNLESktUo

    private Map<Long, Integer> levels = new HashMap<>();
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new Main());
    }

    @Override
    public String getBotUsername() {
        return "AMF_new_Bot";
    }

    @Override
    public String getBotToken() {
        return "5833023447:AAFF_JdHU7BHl0TTCq8IefhTOGHNLESktUo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            sendImage("level-1", chatId);
            SendMessage message = createMessage("Ґа-ґа-ґа!\n" +
                    "Вітаємо у боті біолабораторії «Батько наш Бандера».\n" +
                    "\n" +
                    "Ти отримуєш гусака №71\n" +
                    "\n" +
                    "Цей бот ми створили для того, щоб твій гусак прокачався з рівня звичайної свійської худоби до рівня біологічної зброї, здатної нищити ворога. \n" +
                    "\n" +
                    "Щоб звичайний гусак перетворився на бандерогусака, тобі необхідно:\n" +
                    "✔️виконувати завдання\n" +
                    "✔️переходити на наступні рівні\n" +
                    "✔️заробити достатню кількість монет, щоб придбати Джавеліну і зробити свєрхтра-та-та.\n" +
                    "\n" +
                    "*Гусак звичайний.* Стартовий рівень.\n" +
                    "Бонус: 5 монет.\n" +
                    "Обери завдання, щоб перейти на наступний рівень\n");
            message.setChatId(chatId);

            attachButtons(message, Map.of(
                    "Сплести маскувальну сітку (+15 монет)", "level_1_task",
                    "Зібрати кошти патріотичними піснями (+15 монет)", "level_1_task",
                    "Вступити в Міністерство Мемів України (+15 монет)", "level_1_task"

            ));

            sendApiMethodAsync(message);
        }
//        Long chatId = getChatId(update);



//        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
//            SendMessage message = createMessage("Привіт!");
//            message.setChatId(chatId);
//            attachButtons(message, Map.of(
//                    "Слава Україні!", "glory_for_ukraine"
////                    "Слава Нації 1", "glory_to_the_nation1",
////                    "Слава Нації 2", "glory_to_the_nation2"
//
//            ));
//            sendApiMethodAsync(message);
//        }

//        if (update.hasCallbackQuery()) {
//            if (update.getCallbackQuery().getData().equals("glory_for_ukraine")) {
//                SendMessage message = createMessage("Героям Слава!");
//                message.setChatId(chatId);
//                attachButtons(message, Map.of(
//                        "Слава Нації", "glory_to_the_nation"
//                ));
//                sendApiMethodAsync(message);
//            }
//
//            if (update.getCallbackQuery().getData().equals("glory_to_the_nation")) {
//                SendMessage message = createMessage("Смерть ворогам!");
//                message.setChatId(chatId);
//                sendApiMethodAsync(message);
//            }
//        }

        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("level_1_task") && getLevel(chatId) == 1) {
                setLevel(chatId, 2);

                sendImage("level-2", chatId);

                SendMessage message = createMessage("*Вітаємо на другому рівні! Твій гусак - біогусак.*\n" +
                        "Баланс: 20 монет. \n" +
                        "Обери завдання, щоб перейти на наступний рівень\n");
                message.setChatId(chatId);
                attachButtons(message, Map.of(
                        "Зібрати комарів для нової біологічної зброї (+15 монет)", "level_2_task",
                        "Пройти курс молодого бійця (+15 монет)", "level_2_task",
                        "Задонатити на ЗСУ (+15 монет)", "level_2_task"
                ));
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("level_2_task") && getLevel(chatId) == 2) {
                setLevel(chatId, 3);

                sendImage("level-3", chatId);

                SendMessage message = createMessage("*Вітаємо на третьому рівні! Твій гусак - бандеростажер.*\n" +
                        "Баланс: 35 монет. \n" +
                        "Обери завдання, щоб перейти на наступний рівень\n");
                message.setChatId(chatId);
                attachButtons(message, Map.of(
                        "Злітати на тестовий рейд по чотирьох позиціях (+15 монет) ", "level_3_task",
                        "Відвезти гуманітарку на передок (+15 монет) ", "level_3_task",
                        "Знайти зрадника та здати в СБУ (+15 монет) ", "level_3_task"
                ));
                sendApiMethodAsync(message);
            }

        }



    }
    public Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        return message;
    }
    public void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8));
            button.setCallbackData(buttonValue);
            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
    public void sendImage(String name, Long chatId) {
        SendAnimation animation = new SendAnimation();
        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("images/" + name + ".gif"));
        animation.setAnimation(inputFile);
        animation.setChatId(chatId);
        executeAsync(animation);
    }
    public int getLevel(Long chatID) {
        return levels.getOrDefault(chatID, 1);
    }
    public void setLevel(Long chatId, int level) {
        levels.put(chatId, level);
    }

}