package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class StaticMapResponseFakeBuilder
{
    // hash: e50a8dc67455ab503741cb940a67a9dc
    private String          location1_1 = "file-name-1.jpg";
    private MapPositionEnum position1_1 = MapPositionEnum.COMPANY_HEADER;
    // hash: 3789441e232d55c9dc9e78abf724a895
    private String          location1_2 = "file-name-2.jpg";
    private MapPositionEnum position1_2 = MapPositionEnum.COMPANY_LEFT;

    public StaticMapResponse build()
    {
        return new StaticMapResponse(location1_1, position1_1);
    }

    public StaticMapResponse build1_2()
    {
        return new StaticMapResponse(location1_2, position1_2);
    }
}
