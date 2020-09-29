package com.yzmedu.ms.yzmmsscorethriftservice.thrift;

import com.yzmedu.protocol.score.ScoreService;
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

@Slf4j
@Configuration
public class ScoreThriftServer {

    @Value("${service.port}")
    private int serverPort;
    @Autowired(required = false)
    private ScoreService.Iface scoreService;

    @PostConstruct
    public void startScoreServer(){
        TNonblockingServerSocket socket = null;
        ScoreService.Processor<ScoreService.Iface> processor = new ScoreService.Processor<>(scoreService);
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

        log.info("score thrift server start at port :" + serverPort);

        server.serve();
    }
}
