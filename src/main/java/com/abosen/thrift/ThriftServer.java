package com.abosen.thrift;

import com.abosen.thrift.generated.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @author qiubaisen
 * @date 2019-10-20
 */
@Slf4j
public class ThriftServer {
    public static void main(String[] args) throws TTransportException {
        // 非阻塞socket对象
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        THsHaServer.Args arg = new THsHaServer.Args(socket).minWorkerThreads(2).maxWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor = new PersonService.Processor<>(new PersonServiceImpl());

        arg.protocolFactory(new TCompactProtocol.Factory());
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        // HsHa: half-sync half-async
        TServer server = new THsHaServer(arg);
        log.info("Server started");
        server.serve();
    }
}
