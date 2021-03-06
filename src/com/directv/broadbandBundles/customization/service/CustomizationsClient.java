package com.directv.broadbandBundles.customization.service;

import com.directv.broadbandBundles.customization.snmp.SendSnmpTrap;
import com.directv.broadbandBundles.initialize.CustomizerProperites;
import com.directv.ei.schemas.envelope.v3_0.*;
import com.directv.ei.schemas.envelope.v3_0.holders.EIHeaderTypeHolder;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ProcessNonVideoCustomizationsRequestEntity;
import com.directv.ei.schemas.wsdl.nonvideoservices.v3_0.processnonvideocustomizations.ProcessNonVideoCustomizationsResponseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.rpc.Stub;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: 00U3073
 * Date: 5/3/11
 * Time: 9:17 AM
 * Sends a request and receives a response from the ProcessNonVideoCustomizations EI service.
 */
public class CustomizationsClient
{
    private static final Log logger = LogFactory.getLog("com.directv.broadbandBundles.customization.service.CustomizationsClient");

    private static final String PNVC_WS_URL = "pnvc.ws.url";
    private static final String TRANSPORT_TIMEOUT = "weblogic.wsee.transport.read.timeout";

    public static ProcessNonVideoCustomizationsResponseEntity
            getResponse(ProcessNonVideoCustomizationsRequestEntity request, long visitId)
            throws Exception
    {
        // prepare the service call
        ProcessNonVideoCustomizationsService service = new ProcessNonVideoCustomizationsService_Impl();
        ProcessNonVideoCustomizationsPortType port = service.getProcessNonVideoCustomizationsServicePort();

        //set the service address
        Stub stub = (Stub) port;
        stub._setProperty("javax.xml.rpc.service.endpoint.address", CustomizerProperites.getProperty(PNVC_WS_URL));
        //set timeout
        //** make sure longer than EI's timeout
        stub._setProperty("weblogic.wsee.transport.read.timeout", Integer.valueOf(CustomizerProperites.getProperty(TRANSPORT_TIMEOUT)));

        //populate header with EI crap
        EIHeaderType header = new EIHeaderType();
        header.setBusinessProcessName("OfferMgmnt_Punchin_ProcessNonVideoCustomizations");
        header.setOriginator("NVC");
        ServiceIdentifierType serviceIdentifierType = new ServiceIdentifierType();
        serviceIdentifierType.setName("processNonVideoCustomizations");
        serviceIdentifierType.setVersion("3.0");
        header.setService(serviceIdentifierType);

        header.setRequestDateTime(new GregorianCalendar());
        header.setType(MessageType.fromString("request"));

        UserIdType userIdType = new UserIdType();
        userIdType.setGroup("NVC");
        userIdType.setId("NVC");
        header.setUser(userIdType);
        EIHeaderTypeHolder headerHolder = new EIHeaderTypeHolder(header);

        ProcessNonVideoCustomizationsResponseEntity response = null;
        // make the service call
        try
        {
            response = port.processNonVideoCustomizations(request, headerHolder);
            if (response == null)
            {
                logger.fatal("Null Response received, EI Middleware Failure.");
            }
        }
        catch (Exception e)
        {
            logger.fatal("Exception when calling processNonVideoCustomizations, [visitID ["
                                 + visitId + "]" + e);
            StackTraceElement[] steArray = e.getStackTrace();
            if (steArray != null)
            {
                for (StackTraceElement ste : steArray)
                {
                    logger.fatal("at " + ste.toString());
                }
            }

            String snmp = SendSnmpTrap.SNMP_RESPONSE_FAILURE + SendSnmpTrap.getSnmpDetails(visitId, e.toString());
            new SendSnmpTrap(snmp).sendTrap();

        }

        return response;

    }

}
