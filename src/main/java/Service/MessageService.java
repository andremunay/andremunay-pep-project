package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        message = messageDAO.insertMessage(message);
        return message;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    public Message getMessageById(int messageId) {
        Message message = messageDAO.getMessageById(messageId);
        return message;
    }

    public Message deleteMessageById(int messageId) {
        Message message = messageDAO.deleteMessageById(messageId);
        return message;
    }

    public Message updateMessageById(Message message) {
        Message updatedMessage = messageDAO.updateMessageById(message);
        return updatedMessage;
    }
}