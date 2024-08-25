package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    // Declare services for message and account management
    MessageService messageService;
    AccountService accountService;

    // Initialize sevices in the constructor
    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }

    // Method to start the API using Javalin framework
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // Define the endpoints and their handlers
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByIdHandler);
        return app;
    }

    // Handler for account registration
    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);

        // Check if the account was successfully added
        if (addedAccount == null) {
            ctx.status(400);
        } else if (addedAccount.getUsername() != null && 
            !addedAccount.getUsername().isEmpty() &&
            addedAccount.getPassword().length() >= 4) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    // Handler for user login
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginAccount = mapper.readValue(ctx.body(), Account.class);
        Account authenticatedAccount = accountService.authenticate(loginAccount.getUsername(), loginAccount.getPassword());

        if (authenticatedAccount != null) {
            ctx.json(mapper.writeValueAsString(authenticatedAccount));
            ctx.status(200);
        } else {
            ctx.status(401);
        }

    }

    // Handler for posting a new message
    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message createdMessage = mapper.readValue(ctx.body(), Message.class);
        if (
            !createdMessage.getMessage_text().isEmpty() && 
            createdMessage.getMessage_text().length() <= 255 &&
            accountService.isValidAccount(createdMessage.getPosted_by())
        ) {
            Message addedMessage = messageService.addMessage(createdMessage);
            ctx.json(mapper.writeValueAsString(addedMessage));
            ctx.status(200);
        } else {
            ctx.status(400);
        }
    }

    // Handler for retrieving all messages
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    // Handler for retrieving a message by ID
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageById = messageService.getMessageById(messageId);
        if (messageById.getMessage_id() == messageId) {
            ctx.json(messageById);
        } else {
            ctx.result("");
        }
    }

    // Handler for deleting a message by ID
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageById = messageService.deleteMessageById(account_id);
        if (messageById != null) {
            ctx.json(messageById);
        } else {
            ctx.result("");
        }
    }

    // Handler for updating a message by ID
    private void updateMessageById(Context ctx) throws JsonProcessingException {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        message.setMessage_id(messageId);
        Message updatedMessage = messageService.updateMessageById(message);
        if (updatedMessage != null && !message.getMessage_text().isEmpty() && message.getMessage_text().length() <= 255) {
            ctx.json(updatedMessage);
        } else {
            ctx.status(400);
        }

    }

    // Handler for retrieving all messages by account ID
    private void getAllMessagesByIdHandler(Context ctx) throws JsonProcessingException {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessages(accountId);
        ctx.json(messages);
    }
}