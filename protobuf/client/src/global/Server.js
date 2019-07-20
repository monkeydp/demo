import {Server as SingleServer} from '../plugin/main/Server'
import Config from './Config'
import ProtoRoot from "../generated/js/proto"

export default class Server {
    static SERVER_A = new SingleServer({
        base: Config.SERVER.SERVER_A,
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
    // never used
    static SERVER_B = new SingleServer({
        base: Config.SERVER.SERVER_B,
        dataDecoder: {
            protoRoot: ProtoRoot,
        }
    })
}