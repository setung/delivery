package setung.delivery.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    //@Transactional
    public void redisListTest() {
        ListOperations listOperations = redisTemplate.opsForList();
        Long beforeSize = listOperations.size("key");

        listOperations.rightPush("key", 1);
        listOperations.rightPush("key", 2);
        listOperations.rightPush("key", 3);
        listOperations.rightPush("key2", "dd");
        listOperations.rightPush("key3", new Person("jsh", 10));

        Object findByIndex = listOperations.index("key3", 0);
        Object findPerson = listOperations.leftPop("key3");
        Object nulll = listOperations.leftPop("null");
        List<Integer> range = listOperations.range("key", 0, listOperations.size("key") - 1);

        assertThat(nulll).isNull();
        assertThat(findPerson).isInstanceOf(Person.class);
        assertThat(findByIndex).isInstanceOf(Person.class);
        assertThat(((Person) findPerson).getName()).isEqualTo("jsh");
        assertThat(((Person) findByIndex).getName()).isEqualTo("jsh");
        assertThat(range.size()).isEqualTo(beforeSize + 3);

    }

    static class Person {
        String name;
        int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
