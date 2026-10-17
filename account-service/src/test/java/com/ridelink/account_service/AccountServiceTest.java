package com.ridelink.account_service;

import com.ridelink.account_service.dto.*;
import com.ridelink.account_service.exception.AccountNotFoundException;
import com.ridelink.account_service.exception.DuplicateEmailException;
import com.ridelink.account_service.model.Account;
import com.ridelink.account_service.model.AccountStatus;
import com.ridelink.account_service.model.Role;
import com.ridelink.account_service.repository.AccountRepository;
import com.ridelink.account_service.security.JwtUtil;
import com.ridelink.account_service.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account mockAccount;

    @BeforeEach
    void setUp() {
        mockAccount = Account.builder()
                .id("test-id-123")
                .name("Test User")
                .email("test@test.com")
                .password("encodedPassword")
                .role(Role.PASSENGER)
                .status(AccountStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // ─── REGISTER TESTS ───────────────────────────────────────

    @Test
    void register_ShouldReturnAccountResponse_WhenValidRequest() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@test.com");
        request.setPassword("123456");
        request.setRole(Role.PASSENGER);

        when(accountRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        // Act
        AccountResponse response = accountService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("Test User", response.getName());
        assertEquals("test@test.com", response.getEmail());
        assertEquals(Role.PASSENGER, response.getRole());
        assertEquals(AccountStatus.ACTIVE, response.getStatus());
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void register_ShouldThrowDuplicateEmailException_WhenEmailAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@test.com");
        request.setPassword("123456");
        request.setRole(Role.PASSENGER);

        when(accountRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateEmailException.class, () -> accountService.register(request));
        verify(accountRepository, never()).save(any(Account.class));
    }

    // ─── LOGIN TESTS ───────────────────────────────────────────

    @Test
    void login_ShouldReturnLoginResponse_WhenValidCredentials() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("123456");

        when(accountRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockAccount));
        when(passwordEncoder.matches(request.getPassword(), mockAccount.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(mockAccount)).thenReturn("mock-jwt-token");

        // Act
        LoginResponse response = accountService.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        assertEquals("test-id-123", response.getAccountId());
        assertEquals("PASSENGER", response.getRole());
    }

    @Test
    void login_ShouldThrowException_WhenAccountNotFound() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("notexist@test.com");
        request.setPassword("123456");

        when(accountRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> accountService.login(request));
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsWrong() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("wrongpassword");

        when(accountRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockAccount));
        when(passwordEncoder.matches(request.getPassword(), mockAccount.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> accountService.login(request));
    }

    @Test
    void login_ShouldThrowException_WhenAccountIsSuspended() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setEmail("test@test.com");
        request.setPassword("123456");

        mockAccount.setStatus(AccountStatus.SUSPENDED);

        when(accountRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(mockAccount));
        when(passwordEncoder.matches(request.getPassword(), mockAccount.getPassword())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> accountService.login(request));
    }

    // ─── GET ACCOUNT TESTS ─────────────────────────────────────

    @Test
    void getAccountById_ShouldReturnAccount_WhenExists() {
        // Arrange
        when(accountRepository.findById("test-id-123")).thenReturn(Optional.of(mockAccount));

        // Act
        AccountResponse response = accountService.getAccountById("test-id-123");

        // Assert
        assertNotNull(response);
        assertEquals("test-id-123", response.getId());
        assertEquals("Test User", response.getName());
    }

    @Test
    void getAccountById_ShouldThrowException_WhenNotFound() {
        // Arrange
        when(accountRepository.findById("invalid-id")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class,
                () -> accountService.getAccountById("invalid-id"));
    }

    // ─── UPDATE ACCOUNT TESTS ──────────────────────────────────

    @Test
    void updateAccount_ShouldUpdateName_WhenValidRequest() {
        // Arrange
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setName("Updated Name");

        when(accountRepository.findById("test-id-123")).thenReturn(Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        // Act
        AccountResponse response = accountService.updateAccount("test-id-123", request);

        // Assert
        assertNotNull(response);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    // ─── STATUS UPDATE TESTS ───────────────────────────────────

    @Test
    void updateAccountStatus_ShouldUpdateStatus_WhenValidRequest() {
        // Arrange
        when(accountRepository.findById("test-id-123")).thenReturn(Optional.of(mockAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

        // Act
        AccountResponse response = accountService.updateAccountStatus("test-id-123", AccountStatus.SUSPENDED);

        // Assert
        assertNotNull(response);
        verify(accountRepository, times(1)).save(any(Account.class));
    }
}