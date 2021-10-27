package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.common.services.PaginatorService;
import com.kbalazsworks.stackjudge.domain.paginator_module.value_objects.PaginatorItem;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import java.util.List;

import static org.mockito.Mockito.when;

public class PaginatorServiceMocker extends MockCreator
{
    public static PaginatorService generate_(
        long whenElementsBeforeSeekId,
        long whenItemCount,
        int whenLimit,
        List<PaginatorItem> thanPaginatorItems
    )
    {
        PaginatorService mock = getPaginatorServiceMock();
        when(mock.generate(whenElementsBeforeSeekId, whenItemCount, whenLimit)).thenReturn(thanPaginatorItems);

        return mock;
    }
}
