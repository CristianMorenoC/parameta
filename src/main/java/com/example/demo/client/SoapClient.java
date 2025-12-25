package com.example.demo.client;

import com.example.demo.dto.soap.VinculacionSoapRequest;
import com.example.demo.dto.soap.VinculacionSoapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SoapClient {

  private final WebServiceTemplate webServiceTemplate;

  @Value("${SOAP_MOCK_URL:http://localhost:3000/mock/soap}")
  private String soapUrl;

  public VinculacionSoapResponse callLegacySystem(VinculacionSoapRequest request) {
    log.info("Sending SOAP request to: {}", soapUrl);
    log.debug("Request details: {}", request);

    try {
      VinculacionSoapResponse response = (VinculacionSoapResponse) webServiceTemplate
          .marshalSendAndReceive(soapUrl, request);

      log.info("Received SOAP response: status={}, message={}",
          response.getStatus(), response.getMessage());
      return response;
    } catch (Exception e) {
      log.error("Error communicating with legacy SOAP system: {}", e.getMessage(), e);
      throw e;
    }
  }
}
