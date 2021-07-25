package com.afk.testtechnique.supervision;

import com.afk.testtechnique.TestTechniqueApplication;
import com.afk.testtechnique.controller.UserController;
import com.afk.testtechnique.dto.UserDTO;
import com.afk.testtechnique.model.User;
import com.afk.testtechnique.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static com.afk.testtechnique.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogSupervisionTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @SpyBean
    private LogSupervision logSupervision;

    @Test
    public void shouldBeExecutedWhenCreatingUser() throws Throwable {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        when(userService.createUser(any(User.class))).thenReturn(user);
        userController.createUser(new UserDTO());
        verify(logSupervision, times(1)).apiSupervision(any(), any());
    }

    @Test
    public void shouldBeExecutedWhenFindingUser() throws Throwable {
        User user = new User(USERNAME, FIRST_NAME, LAST_NAME, EMAIL);
        when(userService.findByUsername(anyString())).thenReturn(user);
        userController.findUserByUsername(USERNAME);
        verify(logSupervision, times(1)).apiSupervision(any(), any());
    }
}
