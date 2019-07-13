package com.monkeydp.demo.protobuf.protocol;

import com.monkeydp.demo.protobuf.protocol.PersonOuterClass.Person;
import lombok.SneakyThrows;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author iPotato
 * @date 2019/7/13
 */
public class MainClass {

    public static void main(String[] args) {
        new ProtobufTest().test();
    }

    private static class ProtobufTest {

        private static final String IGNORE_DIR_PATH =
                System.getProperty("user.dir") + "/protobuf/ignore";
        private static final File IGNORE_DIR = new File(IGNORE_DIR_PATH);

        private static final String PERSON_FILE_PATH =
                IGNORE_DIR_PATH + "/person.txt";
        private static final File PERSON_FILE = new File(PERSON_FILE_PATH);

        @SneakyThrows
        private ProtobufTest() {
            if (!IGNORE_DIR.exists()) {
                IGNORE_DIR.mkdirs();
            }
            if (!PERSON_FILE.exists()) {
                PERSON_FILE.createNewFile();
            }
        }

        public void test() {
            Person person = mockPerson();
            this.serializePerson(person);
            Person deserializedPerson = this.deserializePerson();
            Assert.isTrue(deserializedPerson.equals(person), "Deserialized person must equals to raw person");
        }

        private Person mockPerson() {
            return Person.newBuilder()
                         .setName("iPotato")
                         .setAge(24)
                         .build();
        }

        @SneakyThrows
        private void serializePerson(Person person) {
            try (FileOutputStream stream = new FileOutputStream(PERSON_FILE)) {
                person.writeTo(stream);
            }
        }

        @SneakyThrows
        private Person deserializePerson() {
            try (FileInputStream stream = new FileInputStream(PERSON_FILE);) {
                return Person.parseFrom(stream);
            }
        }
    }
}
