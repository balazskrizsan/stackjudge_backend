package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
@Setter
public class RecursiveGroupFakeBuilder
{
    private Long   id1        = GroupFakeBuilder.defaultId1;
    private String name1      = "QQQ";
    private short  typeId1    = (short) 2; // teams
    private Long   companyId1 = CompanyFakeBuilder.defaultId1;
    private Long   addressId1 = AddressFakeBuilder.defaultId1;
    private Long   parentId1  = null;
    private int    depth1     = 1;
    private String path1      = "101001";

    private Long   id2        = GroupFakeBuilder.defaultId2;
    private String name2      = "WWW";
    private short  typeId2    = (short) 3; // teams
    private Long   companyId2 = CompanyFakeBuilder.defaultId1;
    private Long   addressId2 = AddressFakeBuilder.defaultId1;
    private Long   parentId2  = GroupFakeBuilder.defaultId1;
    private int    depth2     = 2;
    private String path2      = "101001>101002";

    private Long   id3        = GroupFakeBuilder.defaultId3;
    private String name3      = "EEE";
    private short  typeId3    = (short) 3; // teams
    private Long   companyId3 = CompanyFakeBuilder.defaultId1;
    private Long   addressId3 = AddressFakeBuilder.defaultId1;
    private Long   parentId3  = GroupFakeBuilder.defaultId1;
    private int    depth3     = 2;
    private String path3      = "101001>101003";

    public RecursiveGroup build()
    {
        return new RecursiveGroup(id1, name1, typeId1, companyId1, addressId1, parentId1, depth1, path1);
    }

    public RecursiveGroup build2()
    {
        return new RecursiveGroup(id2, name2, typeId2, companyId2, addressId2, parentId2, depth2, path2);
    }

    public RecursiveGroup build3()
    {
        return new RecursiveGroup(id3, name3, typeId3, companyId3, addressId3, parentId3, depth3, path3);
    }

    public List<RecursiveGroup> buildAsList()
    {
        return List.of(build(), build2(), build3());
    }
}
