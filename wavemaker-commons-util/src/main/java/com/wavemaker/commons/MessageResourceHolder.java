package com.wavemaker.commons;

/**N
 * Created by prakashb on 27/7/18.
 */
public class MessageResourceHolder {

    private MessageResource messageResource;
    private Object[] args;
    private String message;

    public MessageResourceHolder() {
    }

    public MessageResourceHolder(MessageResource messageResource, Object[] args, String message) {
        this.messageResource = messageResource;
        this.args = args;
        this.message = message;
    }

    public void setMessageResource(MessageResource messageResource) {
        this.messageResource = messageResource;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageResource getMessageResource() {
        return messageResource;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getMessage() {
        return message;
    }
}
