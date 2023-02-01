package com.linklegel.apiContact.Controller.TrafficController;

import com.linklegel.apiContact.Entities.Dto.request.DynamicRequestApiBody;
import com.linklegel.apiContact.ProjectUtils.ProjectUtils;
import com.linklegel.apiContact.Services.Traffic.ITraffic;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/request")
public class TrafficController {

    private final ITraffic traffic;

    public TrafficController(ITraffic traffic) {
        this.traffic = traffic;
    }

    @GetMapping("/sendGetRequest")
    public ResponseEntity<?> sendGetRequest(@Valid @RequestBody DynamicRequestApiBody parameters , @NotNull BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return new ResponseEntity<>(ProjectUtils.validateBindBody(result), HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<?> tmp = traffic.sendGetRequestWithParameters(parameters);
        return tmp;
    }


    @PostMapping("/sendPostRequest")
    public void sendPostRequest(HttpServletRequest request,@RequestBody DynamicRequestApiBody parameters ) {
        System.out.println(parameters);
        System.out.println(request);
        return;
    }


}
