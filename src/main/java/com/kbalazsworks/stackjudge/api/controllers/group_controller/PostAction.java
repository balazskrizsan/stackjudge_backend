package com.kbalazsworks.stackjudge.api.controllers.group_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.group_request.GroupCreateRequest;
import com.kbalazsworks.stackjudge.api.services.JavaxValidatorService;
import com.kbalazsworks.stackjudge.api.services.RequestMapperService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.group_module.services.GroupService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("GroupPostAction")
@RequestMapping(GroupConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class PostAction
{
    private final GroupService groupService;
    private final StateService stateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData<String>> action(GroupCreateRequest request) throws Exception
    {
        new JavaxValidatorService<GroupCreateRequest>().validate(request);

        groupService.create(RequestMapperService.mapToRecord(request, stateService.getState()));

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
