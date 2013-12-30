
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.richfaces.cdi.push.Push;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class PushCdiBean {
    public static final String PUSH_CDI_TOPIC = "pushCdi";
 
    private String message;
 
    @Inject
    @Push(topic = PUSH_CDI_TOPIC)
    Event<String> pushEvent;
 
    /**
     * Sends message.
     *
     * @param message to send
     */
    public void sendMessage() {
        pushEvent.fire(message);
        message = "";
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
}
