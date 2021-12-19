package entity;

/**
 */
public class MessageHeader {

    private String key;
    private String value;

    public MessageHeader() {
    }

    public MessageHeader(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static MessageHeader of(String key, String value) {
        return new MessageHeader(key, value);
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final String OFFSET = "offset";
    public static final String VERSION = "version";
    public static final String QUEUE_NAME = "queue";
    public static final String CREATED_TIME = "created";

}
