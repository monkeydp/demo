import {Server} from '../js/Server'
import ProtoRoot from "../generated/js/proto"

export default class Servers {
    static serverA = new Server({
        base: {
            host: 'localhost',
            port: '8080',
        },
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
    // never used
    static serverB = new Server({
        base: {
            host: 'localhost',
            port: '8111',
        },
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
}