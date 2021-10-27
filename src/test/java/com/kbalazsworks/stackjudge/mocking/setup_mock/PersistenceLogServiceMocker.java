package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.TypedPersistenceLog;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.enums.PersistenceLogTypeEnum;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.services.PersistenceLogService;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

public class PersistenceLogServiceMocker
{
    public static <T> void create_verifier(
        PersistenceLogService persistenceLogServiceMock,
        Long testedId,
        PersistenceLogTypeEnum testedPersistenceLogTypeEnum,
        T testedData,
        LocalDateTime testedCreatedAt
    )
    {
        ArgumentCaptor<TypedPersistenceLog<T>> argument = ArgumentCaptor.forClass(TypedPersistenceLog.class);

        verify(persistenceLogServiceMock, only()).create(argument.capture());

        assertAll(
            () -> assertThat(testedId).isEqualTo(argument.getValue().getId()),
            () -> assertThat(testedPersistenceLogTypeEnum).isEqualTo(argument.getValue().getType()),
            () -> assertThat(testedData).usingRecursiveComparison().isEqualTo(argument.getValue().getData()),
            () -> assertThat(testedCreatedAt).isEqualTo(argument.getValue().getCreatedAt())
        );
    }
}
