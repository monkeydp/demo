import {Server} from '../js/Server'
import ProtoRoot from "../generated/js/proto"

export default class Servers {
    static SERVER_A = new Server({
        base: {
            host: 'localhost',
            port: '8080',
        },
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
    // never used
    static SERVER_B = new Server({
        base: {
            host: 'localhost',
            port: '8111',
        },
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
}