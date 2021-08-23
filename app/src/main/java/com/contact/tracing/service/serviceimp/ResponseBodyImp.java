package com.contact.tracing.service.serviceimp;

import com.contact.tracing.model.responsemodel.loginresponse.LoginResponseModel;
import com.contact.tracing.model.responsemodel.loginresponse.QueryLoginModel;
import com.contact.tracing.model.responsemodel.loginresponse.TokenBodyResponse;
import com.contact.tracing.model.responsemodel.registrationresponse.BodyResponse;
import com.contact.tracing.model.responsemodel.registrationresponse.QueryResponse;
import com.contact.tracing.model.responsemodel.registrationresponse.ResponseDataModel;
import com.contact.tracing.service.ResponseBodyService;


public class ResponseBodyImp implements ResponseBodyService {


    @Override
    public QueryResponse getServerBodyResponse() {

        BodyResponse bodyResponse = new BodyResponse();
        ResponseDataModel responseDataModel = new ResponseDataModel(bodyResponse);
        QueryResponse queryResponse = new QueryResponse(responseDataModel);
        return queryResponse;
    }


    @Override
    public QueryLoginModel getLoginServerBodyResponse() {
        TokenBodyResponse tokenResponse  = new TokenBodyResponse();
        LoginResponseModel loginResponseModel = new LoginResponseModel(tokenResponse);
        QueryLoginModel queryLoginModel = new QueryLoginModel(loginResponseModel);

        return queryLoginModel;
    }

}
