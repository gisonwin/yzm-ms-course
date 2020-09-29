package com.yzmedu.ms.yzmmsuserthriftservice.thrift;

import com.yzmedu.protocol.score.ScoreService;
import com.yzmedu.protocol.user.UserService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class ServiceProvider {
    @Value("${user.service.ip}")
    private String userServiceIp;
    @Value("${user.service.port}")
    private int userServicePort;


    @Value("${score.service.ip}")
    private String scoreServiceIp;
    @Value("${score.service.port}")
    private int scoreServicePort;

    /**
     * 构建用户服务的客户端,去连接user-thrift-service 的服务端,服务端监听端口9000
     *
     * @return
     */
    public UserService.Client getUserService() {

        TBinaryProtocol protocol = (TBinaryProtocol) getClient(userServiceIp, userServicePort);
        UserService.Client client = new UserService.Client(protocol);
        return client;
    }

    /**
     * 构建用户服务的客户端,去连接score-thrift-service 的服务端,服务端监听端口9090
     *
     * @return
     */
    public ScoreService.Client getScoreService() {
        TBinaryProtocol protocol = (TBinaryProtocol) getClient(scoreServiceIp, scoreServicePort);
        return new ScoreService.Client(protocol);
    }

    /**
     * 根据ip port得到请求的客户端实例.
     * @param ip
     * @param port
     * @return
     */
    private TProtocol getClient(String ip, int port) {
        TSocket socket = new TSocket(ip, port, 3000);
        TFramedTransport transport = new TFramedTransport(socket);
        try {
            transport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        return protocol;
    }
}
