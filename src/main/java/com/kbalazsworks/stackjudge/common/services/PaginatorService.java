package com.kbalazsworks.stackjudge.common.services;

import com.kbalazsworks.stackjudge.domain.review_module.enums.ItemTypeEnum;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.paginator_module.value_objects.PaginatorItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginatorService
{
    public List<PaginatorItem> generate(long elementsBeforeSeekId, long itemCount, int limit)
    {
        long elementsBeforeSeekIdWithSeekId = elementsBeforeSeekId + 1;
        long currentPage = (long) Math.ceil((double) elementsBeforeSeekIdWithSeekId / limit);
        long pages       = (long) Math.ceil((double) itemCount / limit);

        List<PaginatorItem> paginatorItems = new ArrayList<>();
        boolean             placerInserted = false;

        for (int i = 1; i <= pages; i++)
        {
            NavigationEnum page = getPageEnum(i, currentPage, pages);
            if (page != null)
            {
                placerInserted = false;
                paginatorItems.add(new PaginatorItem(ItemTypeEnum.PAGE, String.valueOf(i), page, currentPage == i));

                continue;
            }

            if (!placerInserted)
            {
                placerInserted = true;
                paginatorItems.add(new PaginatorItem(ItemTypeEnum.SPACER, "", null, currentPage == i));
            }
        }

        return paginatorItems;
    }

    private NavigationEnum getPageEnum(int iteratedPage, long currentPage, long pages)
    {
        if (iteratedPage == 1) {
            return NavigationEnum.FIRST;
        }

        if (iteratedPage == 2) {
            return NavigationEnum.SECOND;
        }

        if (iteratedPage == currentPage - 2) {
            return NavigationEnum.CURRENT_MINUS_2;
        }

        if (iteratedPage == currentPage - 1) {
            return NavigationEnum.CURRENT_MINUS_1;
        }

        if (iteratedPage == currentPage) {
            return NavigationEnum.CURRENT;
        }

        if (iteratedPage == currentPage + 1) {
            return NavigationEnum.CURRENT_PLUS_1;
        }

        if (iteratedPage == currentPage + 2) {
            return NavigationEnum.CURRENT_PLUS_2;
        }

        if (iteratedPage == pages - 1) {
            return NavigationEnum.LAST_MINUS_1;
        }

        if (iteratedPage == pages) {
            return NavigationEnum.LAST;
        }

        return null;
    }
}
