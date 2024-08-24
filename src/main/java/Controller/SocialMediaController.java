package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        return app;
    }

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount.getUsername() != null && addedAccount.getPassword().length() >= 4) {
            ctx.json(mapper.writeValueAsString(account));
        } else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        accountService.authenticate(account);
        if (account != null) {
            ctx.json(mapper.writeValueAsString(account));
            ctx.status(200);
        } else {
            ctx.status(401);
        }

    }

    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        if (message.getMessage_text() != null && message.getMessage_text().length() <= 255) {
            messageService.addMessage(message);
            ctx.json(mapper.writeValueAsString(message));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }
}