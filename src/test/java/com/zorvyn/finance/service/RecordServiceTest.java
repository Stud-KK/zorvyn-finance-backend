package com.zorvyn.finance.service;

import com.zorvyn.finance.dto.record.RecordRequest;
import com.zorvyn.finance.entity.Record;
import com.zorvyn.finance.entity.RecordType;
import com.zorvyn.finance.entity.User;
import com.zorvyn.finance.repository.RecordRepository;
import com.zorvyn.finance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecordServiceTest {

    @Test
    void testCreateRecord_basic() {

        // mock dependencies
        UserRepository userRepo = mock(UserRepository.class);
        RecordRepository recordRepo = mock(RecordRepository.class);

        RecordService service = new RecordService();
        service.userRepository = userRepo;
        service.recordRepository = recordRepo;

        // dummy data
        User user = User.builder().id(1L).email("test@gmail.com").build();

        RecordRequest req = new RecordRequest();
        req.setAmount(500.0);
        req.setType(RecordType.INCOME);
        req.setCategory("Salary");

        // mock behavior
        when(userRepo.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(recordRepo.save(any(Record.class)))
                .thenAnswer(i -> i.getArgument(0));

        // call method
        var result = service.createRecord(req, "test@gmail.com");

        // check result
        assertNotNull(result);
        assertEquals(500.0, result.getAmount());
    }
    @Test
    void testCreateRecord_userNotFound() {

        UserRepository userRepo = mock(UserRepository.class);
        RecordRepository recordRepo = mock(RecordRepository.class);

        RecordService service = new RecordService();
        service.userRepository = userRepo;
        service.recordRepository = recordRepo;

        when(userRepo.findByEmail("wrong@gmail.com"))
                .thenReturn(Optional.empty());

        RecordRequest req = new RecordRequest();
        req.setAmount(100.0);
        req.setType(RecordType.EXPENSE);
        req.setCategory("Food");

        assertThrows(RuntimeException.class, () ->
                service.createRecord(req, "wrong@gmail.com")
        );
    }
}