package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    // Constructor for creating a new MessageService with a new MessageDAO
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    // Constructor for creating a new MessageService with a provided MessageDAO
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Use the messageDAO to add a new message to the database
    public Message addMessage(Message message) {
        message = messageDAO.insertMessage(message);
        return message;
    }

    // Use the messageDAO to retrieve all messages from the database
    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    // Use the messageDAO to retrieve a message by its ID
    public Message getMessageById(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        return message;
    }

    // Use the messageDAO to delete a message by its ID and return the deleted message
    public Message deleteMessageById(int messageId) {
        Message message = messageDAO.deleteMessageById(messageId);
        return message;
    }

    // Use the messageDAO to update a message by its ID
    public Message updateMessageById(Message message) {
        Message updatedMessage = messageDAO.updateMessageById(message);
        return updatedMessage;
    }

    // Use the messageDAO to retrieve all messages posted by a specific account
    public List<Message> getAllMessages(int accountId) {
        List<Message> messages = messageDAO.getAllMessages(accountId);
        return messages;
    }
}