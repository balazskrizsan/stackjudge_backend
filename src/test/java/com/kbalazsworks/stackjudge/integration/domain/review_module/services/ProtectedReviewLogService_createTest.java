//package com.kbalazsworks.stackjudge.integration.domain.review_module.services;
//
//import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
//import com.kbalazsworks.stackjudge.ServiceFactory;
//import com.kbalazsworks.stackjudge.db.tables.records.ProtectedReviewLogRecord;
//import com.kbalazsworks.stackjudge.domain.notification_module.entities.TypedNotification;
//import com.kbalazsworks.stackjudge.domain.notification_module.services.CrudNotificationService;
//import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.ProtectedReviewLog;
//import com.kbalazsworks.stackjudge.domain.review_module.entities.DataProtectedReview;
//import com.kbalazsworks.stackjudge.fake_builders.ProtectedReviewLogFakeBuilder;
//import com.kbalazsworks.stackjudge.fake_builders.TypedNotificationFakeBuilder;
//import com.kbalazsworks.stackjudge.mocking.MockCreator;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.SqlConfig;
//import org.springframework.test.context.jdbc.SqlGroup;
//
//import static com.kbalazsworks.stackjudge.MockFactory.TEST_STATE;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.Mockito.only;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
//import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
//import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
//
//public class ProtectedReviewLogService_createTest extends AbstractIntegrationTest
//{
//    @Autowired
//    private ServiceFactory serviceFactory;
//
//    @Test
//    @SqlGroup(
//        {
//            @Sql(
//                executionPhase = BEFORE_TEST_METHOD,
//                config = @SqlConfig(transactionMode = ISOLATED),
//                scripts = {
//                    "classpath:test/sqls/_truncate_tables.sql",
//                    "classpath:test/sqls/preset_add_1_user.sql",
//                    "classpath:test/sqls/preset_add_1_company.sql",
//                    "classpath:test/sqls/preset_add_1_address.sql",
//                    "classpath:test/sqls/preset_add_1_group.sql",
//                    "classpath:test/sqls/preset_add_1_review.sql",
//                }
//            ),
//            @Sql(
//                executionPhase = AFTER_TEST_METHOD,
//                config = @SqlConfig(transactionMode = ISOLATED),
//                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
//            )
//        }
//    )
//    public void saveToDb_willSaveAndCreateNotification()
//    {
//        // Arrange
//        ProtectedReviewLog      testedProtectedReviewLog    = new ProtectedReviewLogFakeBuilder().build();
//        ProtectedReviewLog      expectedProtectedReviewLog  = new ProtectedReviewLogFakeBuilder().build();
//        CrudNotificationService crudNotificationServiceMock = MockCreator.getCrudNotificationService();
//        TypedNotification<DataProtectedReview> expectedCreateCall
//            = new TypedNotificationFakeBuilder<DataProtectedReview>()
//            .id(null)
//            .viewedAt(null)
//            .build();
//
//        // Act
//        serviceFactory
//            .getProtectedReviewLogService(null, crudNotificationServiceMock)
//            .create(testedProtectedReviewLog, TEST_STATE);
//
//        // Assert
//        ProtectedReviewLogRecord actualLog = getQueryBuilder()
//            .selectFrom(protectedReviewLogTable)
//            .where(protectedReviewLogTable.VIEWER_USER_ID.eq(TEST_STATE.currentIdsUser().getIdsUserId()))
//            .fetchOne();
//        actualLog.setId(ProtectedReviewLogFakeBuilder.defaultId1);
//
//        assertAll(
//            () -> verify(crudNotificationServiceMock, only())
//                .create(argThat(args -> argThatObject(args, expectedCreateCall))),
//            () -> assertThat(actualLog.into(ProtectedReviewLog.class)).isEqualTo(expectedProtectedReviewLog)
//        );
//    }
//}
