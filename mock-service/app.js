/**
 * Mock SOAP Service Lambda Handler
 */
exports.handler = async (event) => {
  console.log("Received event:", JSON.stringify(event, null, 2));

  const soapResponse = `<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://example.com/service">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:VinculacionResponse>
         <ser:status>SUCCESS</ser:status>
         <ser:message>Empleado vinculado exitosamente en el sistema legacy</ser:message>
      </ser:VinculacionResponse>
   </soapenv:Body>
</soapenv:Envelope>`;

  return {
    statusCode: 200,
    headers: {
      "Content-Type": "text/xml; charset=utf-8"
    },
    body: soapResponse
  };
}
