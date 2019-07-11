namespace * com.monkeydp.demo.thrift.protocol

enum TEventType {
    UPDATE,
    NOTIFY,
}

struct TEvent {
    1: required TEventType type;
    2: required string message;
}

service TEventService {
    string publish(1: TEvent event);
}