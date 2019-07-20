import {Server} from '../tool/Server'
import Config from './Config'
import ProtoRoot from "../generated/js/proto"

export default class Servers {
    static SERVER_A = new Server({
        base: Config.SERVER.SERVER_A,
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
    // never used
    static SERVER_B = new Server({
        base: Config.SERVER.SERVER_B,
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
}