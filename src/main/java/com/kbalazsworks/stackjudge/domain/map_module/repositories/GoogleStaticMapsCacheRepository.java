package com.kbalazsworks.stackjudge.domain.map_module.repositories;

import com.kbalazsworks.stackjudge.domain.common_module.repositories.AbstractRepository;
import com.kbalazsworks.stackjudge.domain.map_module.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.ExceptionResponseInfo;
import com.kbalazsworks.stackjudge.domain.common_module.exceptions.RepositoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class GoogleStaticMapsCacheRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.GoogleStaticMapsCache googleStaticMapsCacheTable
        = com.kbalazsworks.stackjudge.db.tables.GoogleStaticMapsCache.GOOGLE_STATIC_MAPS_CACHE;

    public GoogleStaticMapsCache getByHash(String hash)
    {
        GoogleStaticMapsCache googleStaticMapsCache =  getQueryBuilder()
            .selectFrom(googleStaticMapsCacheTable)
            .where(googleStaticMapsCacheTable.HASH.eq(hash))
            .fetchOneInto(GoogleStaticMapsCache.class);

        if (null == googleStaticMapsCache)
        {
            throw new RepositoryNotFoundException(ExceptionResponseInfo.MapNotFoundMsg)
                .withErrorCode(ExceptionResponseInfo.MapNotFoundErrorCode)
                .withStatusCode(HttpStatus.NOT_FOUND);
        }

        return googleStaticMapsCache;
    }

    public void create(GoogleStaticMapsCache googleStaticMapsCache)
    {
        getQueryBuilder()
            .insertInto(
                googleStaticMapsCacheTable,
                googleStaticMapsCacheTable.HASH,
                googleStaticMapsCacheTable.FILE_NAME,
                googleStaticMapsCacheTable.UPDATED_AT
            )
            .values(
                googleStaticMapsCache.hash(),
                googleStaticMapsCache.fileName(),
                googleStaticMapsCache.updatedAt()
            )
            .execute();
    }
}
