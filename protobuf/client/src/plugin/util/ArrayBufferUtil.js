export default class ArrayBufferUtil {
    static toProtobufMessage(arrayBuffer, protobufMessageClass) {
        let uint8Array = new Uint8Array(arrayBuffer)
        let message = protobufMessageClass.decode(uint8Array)
        return message
    }
}