package com.abosen.thrift;

import com.abosen.thrift.generated.DataException;
import com.abosen.thrift.generated.Person;
import com.abosen.thrift.generated.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author qiubaisen
 * @date 2019-10-20
 */
@Slf4j
public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 8899));
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {
            transport.open();
            Person person = client.getPersonByUserName("张三");
            log.info("Client get person>> name:[{}], age:[{}], married[{}]", person.getName(), person.getAge(), person.isMarried());
            log.info("----");
            Person person2 = new Person();
            person2.setName("李四");
            person2.setAge(30);
            person2.setMarried(true);
            client.savePerson(person2);

        } catch (Exception e) {
            log.error("unexpected exception", e);
        }finally {
            transport.close();
        }

    }
}
