namespace * com.monkeydp.demo.thrift.protocol.greeting

enum TStatus {
    MR,
    MISS,
    MRS,
    MS
}

struct TName {
    1: required string firstName;
    2: required string secondName;
    3: optional TStatus status;
}

service TGreetingService {
    string greet(1: TName name);
}