package com.contact.tracing.service;


import com.contact.tracing.model.responsemodel.loginresponse.QueryLoginModel;
import com.contact.tracing.model.responsemodel.registrationresponse.QueryResponse;

//
//import com.contact.tracing.model.ResponseModel;




public interface ResponseBodyService {
    QueryResponse getServerBodyResponse();


    QueryLoginModel getLoginServerBodyResponse();



}
