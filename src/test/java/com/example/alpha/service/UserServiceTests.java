package com.example.alpha.service;

import com.example.alpha.Entity.User;
import com.example.alpha.Repository.UserRepository;
import com.example.alpha.Service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// include because if main app not start how it check the componenets in it as applicationContext have null
@SpringBootTest
public class UserServiceTests {

//    @Test
//    public void testAdd(){
////        assertEquals(4,2+2);
//    }
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled // if we not want to run when run app not one func
    @Test
    public void testSaveNewUser(){
//        assertNotNull(userRepository.findByUserName("Ram"));
        User user = userRepository.findByUserName("ram");
        assertTrue(user.getJournalEntries().isEmpty());
    }

    @Disabled
    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void TestFindByUserName(User user){
    assertTrue(userService.saveNewUser(user));
    }
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "ram",
//            "shyam",
//            "vipul"
//    })
//    public void TestFindByUserName(String name){
//        assertNotNull(userRepository.findByUserName(name));
//
//    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }
}
