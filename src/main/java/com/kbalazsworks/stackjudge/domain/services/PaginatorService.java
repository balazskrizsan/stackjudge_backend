package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.enums.paginator.ItemTypeEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.PaginatorItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginatorService
{
    public List<PaginatorItem> generate(long elementsBeforeSeekId, long numRows, short limit)
    {
        long currentPage = (long) Math.ceil((double) elementsBeforeSeekId / limit);
        long pages       = (long) Math.ceil((double) numRows / limit);

        List<PaginatorItem> paginatorItems = new ArrayList<>();
        boolean             placerInserted = false;

        for (int i = 1; i <= pages; i++)
        {
            if (isVisiblePageNumber(i, currentPage, pages))
            {
                placerInserted = false;
                paginatorItems.add(new PaginatorItem(ItemTypeEnum.PAGE, String.valueOf(i), currentPage == i));

                continue;
            }

            if (!placerInserted)
            {
                placerInserted = true;
                paginatorItems.add(new PaginatorItem(ItemTypeEnum.SPACER, "", currentPage == i));
            }
        }

        return paginatorItems;
    }

    private boolean isVisiblePageNumber(int iteratedPage, long currentPage, long pages)
    {
        return iteratedPage == 1 || iteratedPage == 2
            || iteratedPage == currentPage - 2 || iteratedPage == currentPage - 1
            || iteratedPage == currentPage
            || iteratedPage == currentPage + 2 || iteratedPage == currentPage + 1
            || iteratedPage == pages || iteratedPage == pages - 1;
    }
}
