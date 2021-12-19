package entity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 */
public final class MessageHeaders extends CopyOnWriteArrayList<MessageHeader> {

    public MessageHeaders() {
    }

    public MessageHeaders(Collection<MessageHeader> collection) {
        if (collection == null)
            throw new NullPointerException();

        addAll(collection);
    }


    public static class Builder {

        private final List<MessageHeader> headers = new LinkedList<>();

        public Builder set(String key, String value) {
            headers.add(MessageHeader.of(key, value));
            return this;
        }

        public MessageHeaders build() {
            return new MessageHeaders(headers);
        }

    }


    public Collection<String> getHeaderKeys() {
        return this.stream().map(
                MessageHeader::getKey
        ).collect(Collectors.toList());
    }

    public String getValue(final String key) {
        return this.stream().filter(
                header -> {
                    return header.getKey().equals(key);
                }
        ).findFirst()
                .map(MessageHeader::getValue)
                .orElse(null);
    }

}
