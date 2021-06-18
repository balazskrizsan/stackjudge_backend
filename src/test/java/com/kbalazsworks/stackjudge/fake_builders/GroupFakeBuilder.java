package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class GroupFakeBuilder
{
    public static final Long defaultId1 = 101001L;
    public static final Long defaultId2 = 101002L;
    public static final Long defaultId3 = 101003L;
    public static final Long defaultId4 = 101004L;
    public static final Long defaultId5 = 101005L;
    public static final Long defaultId6 = 101006L;
    public static final Long defaultId7 = 101007L;
    public static final Long defaultId8 = 101008L;
    public static final Long defaultId9 = 101009L;
    public static final Long defaultId10 = 101010L;

    private long          id               = defaultId1;
    private long          companyId        = CompanyFakeBuilder.defaultId1;
    private Long          parentId         = null;
    private short         typeId           = 1;
    private String        name             = "name of group";
    private short         membersOnGroupId = 3;
    private LocalDateTime createdAt        = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
    private Long          createdBy        = 64897L;

    public Group build()
    {
        return new Group(id, parentId, companyId, name, typeId, membersOnGroupId, createdAt, createdBy);
    }
}
