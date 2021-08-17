package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.services.PaginatorService;
import com.kbalazsworks.stackjudge.domain.value_objects.PaginatorItem;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaginatorServiceMocks extends MockCreator
{
    public static PaginatorService generate_(
        long whenElementsBeforeSeekId,
        long whenItemCount,
        int whenLimit,
        List<PaginatorItem> thanPaginatorItems
    )
    {
        PaginatorService paginatorServiceMock = mock(PaginatorService.class);
        when(paginatorServiceMock.generate(whenElementsBeforeSeekId, whenItemCount, whenLimit))
            .thenReturn(thanPaginatorItems);

        return paginatorServiceMock;
    }
}
