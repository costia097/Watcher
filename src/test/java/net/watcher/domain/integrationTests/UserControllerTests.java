package net.watcher.domain.integrationTests;

import net.watcher.domain.entities.Permission;
import net.watcher.domain.entities.Role;
import net.watcher.domain.entities.User;
import net.watcher.domain.repository.UserRepository;
import net.watcher.domain.requests.SignUpRequestModel;
import net.watcher.domain.responses.UserLoginResponseModel;
import net.watcher.domain.services.core.PermissionAndRoleService;
import net.watcher.domain.services.core.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Integrations tests
 *
 * Please see the {@link User} class for true identity
 * @author Kostia
 *
 */
@ActiveProfiles("qa")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTests {
    private static final String BAD_REQUEST_MESSADGE = "400 null";
    private static final String UNAUTHORIZED_MESSADGE = "401 null";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionAndRoleService permissionAndRoleService;
    private static ThreadLocal<UUID> threadLocal = new ThreadLocal<>();

    @Test
    @Transactional
    public void a_testUserSignUp_h_f() {
        SignUpRequestModel requestModel = new SignUpRequestModel();
        requestModel.setFirstName("name");
        requestModel.setLastName("lastName");
        requestModel.setLogin("login");
        requestModel.setPassword("123");
        requestModel.setEmail("ada@gmail.com");
        requestModel.setGender("M");
        requestModel.setAddress("address");
        requestModel.setCountry("UA");
        requestModel.setDateOfBirth("2018-08-06");
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:9090/signUp", requestModel, Void.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        User targetUser = userRepository.getUserByLogin(requestModel.getLogin(), true, true, true).orElse(null);
        assertThat(Objects.requireNonNull(targetUser).getPermissions().containsAll((permissionAndRoleService.resolvePermissionForNonActiveUser())), is(true));
        assertThat(targetUser.getRoles().containsAll(permissionAndRoleService.resolveRolesForNonActiveUser()), is(true));
        assertThat(targetUser.isActive(), is(false));
        assertThat(StringUtils.isEmpty(targetUser.getUuid()), is(false));
        assertThat(targetUser.getAddress() != null, is(true));
        threadLocal.set(targetUser.getUuid());
    }

    @Test
    public void aa_testUserSignUp_un_h_f_1() {
        SignUpRequestModel requestModel = new SignUpRequestModel();
        requestModel.setLastName("lastName");
        requestModel.setLogin("login");
        requestModel.setPassword("123");
        requestModel.setEmail("ada@gmail.com");
        requestModel.setGender("M");
        requestModel.setAddress("address");
        requestModel.setCountry("UA");
        requestModel.setDateOfBirth("2018-08-06");
        try {
            restTemplate.postForEntity("http://localhost:9090/signUp", requestModel, Void.class);
        } catch (Exception e) {
            assertThat(e.getMessage(), is(BAD_REQUEST_MESSADGE));
        }
    }

    @Test
    public void bb_testUserActivate_un_h_f_1() {
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:9090/confirm/" + UUID.randomUUID(), null, Void.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        User targetUserAfter = userService.findByLogin("login", true, true);
        assertThat(Objects.requireNonNull(targetUserAfter).getRoles().containsAll(permissionAndRoleService.resolveRolesForNonActiveUser()), is(true));
        assertThat(Objects.requireNonNull(targetUserAfter).getPermissions().containsAll(permissionAndRoleService.resolvePermissionForNonActiveUser()), is(true));
        assertThat(targetUserAfter.isActive(), is(false));
    }

    @Test
    public void b_testUserActivate_un_h_f(){
        try {
            restTemplate.postForEntity("http://localhost:9090/confirm/dasdsad", null, Void.class);
        } catch (Exception e) {
            assertThat(e.getMessage(), is(BAD_REQUEST_MESSADGE));
        }
    }

    @Test
    public void bbb_testUserActivate_h_f() {
        UUID uuid = threadLocal.get();
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:9090/confirm/" + uuid, null, Void.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        User targetUserAfter = userService.findByLogin("login", true, true);
        assertThat(Objects.requireNonNull(targetUserAfter).getRoles().containsAll(permissionAndRoleService.resolveRolesForActiveUser()), is(true));
        assertThat(Objects.requireNonNull(targetUserAfter).getPermissions().containsAll(permissionAndRoleService.resolvePermissionForActiveUser()), is(true));
        assertThat(targetUserAfter.isActive(), is(true));
    }

    @Test
    public void c_testUserSignIn_un_h_f() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic bG9naW46MTIzZGFzZA==");
        HttpEntity<?> requestEntity = new HttpEntity<>(null, httpHeaders);
        try {
            restTemplate.exchange("http://localhost:9090/login", HttpMethod.POST, requestEntity, UserLoginResponseModel.class);
        } catch (Exception e) {
            assertThat(e.getMessage(), is(UNAUTHORIZED_MESSADGE));
        }
    }

    @Test
    public void c_testUserSignIn_h_f() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic bG9naW46MTIz");
        HttpEntity<?> requestEntity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<UserLoginResponseModel> exchange = restTemplate.exchange("http://localhost:9090/login", HttpMethod.POST, requestEntity, UserLoginResponseModel.class);
        assertThat(exchange.getStatusCode(), is(HttpStatus.OK));
        UserLoginResponseModel body = exchange.getBody();
        assertThat(body.getRoles().containsAll(permissionAndRoleService.resolveRolesForActiveUser()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet())), is(true));
        assertThat(body.getPermissions().containsAll(permissionAndRoleService.resolvePermissionForActiveUser()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toSet())), is(true));
    }

    @Test
    public void d_testUserLogOut_h_f() {
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:9090/logOut", null, Void.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    }
}
