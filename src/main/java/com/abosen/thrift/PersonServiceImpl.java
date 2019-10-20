package com.abosen.thrift;

import com.abosen.thrift.generated.DataException;
import com.abosen.thrift.generated.Person;
import com.abosen.thrift.generated.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

/**
 * @author qiubaisen
 * @date 2019-10-20
 */

@Slf4j
public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByUserName(String username) throws DataException, TException {
        log.info("Got client Param: [{}]", username);
        Person person = new Person();
        person.setName(username);
        person.setAge(20);
        person.setMarried(false);

        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        log.info("Save person name:[{}], age:[{}], married[{}]", person.getName(), person.getAge(), person.isMarried());
    }
}
