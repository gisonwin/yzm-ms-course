package com.yzmedu.ms.yzmmsuserthriftservice.thrift;

import com.yzmedu.protocol.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class UserThriftServer {
    @Value("${service.port}")
    private int serverPort;
    @Autowired(required = false)
    private UserService.Iface userService;

    @PostConstruct
    public void startUserServer(){
        TNonblockingServerSocket socket = null;
        UserService.Processor<UserService.Iface> processor = new UserService.Processor<>(userService);
        try {
            socket = new TNonblockingServerSocket(serverPort);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        TNonblockingServer.Args args = new TNonblockingServer.Args(socket);
        args.processor(processor);
        args.transportFactory(new TFramedTransport.Factory());
        args.protocolFactory(new TBinaryProtocol.Factory());
        TNonblockingServer server = new TNonblockingServer(args);

        log.info("user thrift server start at port :" + serverPort);

        server.serve();
    }
}
