package com.directv.broadbandBundles.customization.snmp;

import com.ireasoning.protocol.snmp.*;
import com.ireasoning.util.ParseArguments;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Sends an SNMP Trap.  This class is capable of a lot of options, but for DIRECTV
 * I've paired down it's usage. Included is a main program and if you pass in the
 * arguments below you can test other options.
 * <p/>
 * <pre>
 * Example:
 * java SendSnmpTrap -s 10333 -n .1.3.6.1.2.1.2.2.1.1 -g 2 localhost
 * java SendSnmpTrap -s 10333 -n .1.3.6.1.2.1.2.2.1.1 -g 6 -i 20 localhost
 * java SendSnmpTrap -t 2 -s 10333 -q .1.3.6.1.2.1.2.2.1.1 localhost
 * java SendSnmpTrap -t 2 -s 10333 -q .1.3.6.1.2.1.2.2.1.7 localhost 1.3.6.1.2.1.2.2.1.7.3 i 2 //with variable ifAdminStatus.3
 * java SendSnmpTrap -v 3 -u newUser -A abc12345 -X abc12345 -e 12345  -s 10333 -q .1.3.6.1.2.1.2.2.1.1 localhost
 * <p/>
 * </pre>
 */
public class SendSnmpTrap extends SnmpBase
{
    public static String SNMP_RESPONSE_FAILURE      = "922";
    public static String SNMP_APPLICATION_BAD       = "988";


    public static String snmpHost;
    public static int snmpPort;
    public static String snmpCommunity;
    public static String snmpOidGeneric;
    public static String snmpOidSpecific;
    public static int snmpTrapVersion;

    private static Log logger = LogFactory.getLog(SendSnmpTrap.class);

    static
    {
        try
        {
            ResourceBundle bundle = ResourceBundle.getBundle("customizer");
            snmpHost = bundle.getString("snmp.host");
            snmpPort = Integer.parseInt(bundle.getString("snmp.port"));
            snmpCommunity = bundle.getString("snmp.community");
            snmpOidGeneric = bundle.getString("snmp.oid.generic");
            snmpOidSpecific = bundle.getString("snmp.oid.specific");
            snmpTrapVersion = Integer.parseInt(bundle.getString("snmp.trap.version"));
            logger.info("snmpHost=" + snmpHost);
            logger.info("snmpPort=" + snmpPort);
            logger.info("snmpCommunity=" + snmpCommunity);
            logger.info("snmpOidGeneric=" + snmpOidGeneric);
            logger.info("snmpOidSpecific=" + snmpOidSpecific);
            logger.info("snmpTrapVersion=" + snmpTrapVersion);

        }
        catch (Exception e)
        {
            System.out.println("Fatal error loading SNMP Configuration. " + e.getMessage());
            logger.fatal("Fatal error loading SNMP Configuration. " + e.toString());
        }
    }

    byte[] _engineID;
    long _sysUpTime = 0;
    String _snmpTrapOID = ".1.3";
    String _enterprise = ".1.3";
    int _generic = 0;
    int _specific = 0;
    int _trapVersion = 1;
    SnmpVarBindList _vblist = new SnmpVarBindList();


    /**
     * Only constructor
     *
     * @param value SNMP OID value
     */

    public SendSnmpTrap(String value)
    {
        _community = snmpCommunity; //"public";
        _trapVersion = snmpTrapVersion; //2;
        _snmpTrapOID = snmpOidGeneric; //".1.3.6.1.2.1.2.2.1.7";
        _host = snmpHost; //"deninfprdmgr04";
        _port = snmpPort; //162;
        _oids = null;
        _isSnmpV3 = false;
        _authProtocol = null;
        _authPassword = null;
        _privProtocol = 0;
        _privPassword = null;
        _user = null;
        _mibFiles = null;
        _mibString = null;
        _engineID = null;
        _sysUpTime = 0;


        SnmpDataType t = translate("s", value);
        SnmpVarBind vb = new SnmpVarBind(snmpOidSpecific, t); //"1.3.6.1.2.1.2.2.1.7.3", t);
        _vblist.add(vb);
    }

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            logger.error("Usage: java -classpath .;snmptool.jar;ireasoningsnmp.jar;commons-logging-1.0.4.jar com.directv.oscar.snmp.SendSnmpTrap snmp_string");
            System.exit(-1);
        }
//        String snmp = "|first name=peter|last name=li|request id=12345678|phone=3031234567|timestamp=2007-02-08 at 12:30:45";
        String snmp = args[0];
        new SendSnmpTrap(SendSnmpTrap.SNMP_RESPONSE_FAILURE + "|error msg=response slow" + snmp).sendTrap();
        new SendSnmpTrap(SendSnmpTrap.SNMP_APPLICATION_BAD + "|error msg=oscar bad" + snmp).sendTrap();
    }

    /**
     * Actually sends the trap
     */
    public void sendTrap()
    {
        try
        {

            if (_isSnmpV3)
            {
                _trapVersion = 2;
            }

            if (_trapVersion == 1)
            {//send SNMPV1 trap
                SnmpV1Trap trap = new SnmpV1Trap(_enterprise);
                trap.setTimestamp(_sysUpTime);
                trap.setGeneric(_generic);
                trap.setSpecific(_specific);
                trap.addVarBinds(_vblist);
                SnmpTrapSender.sendTrap(trap, _host, _port, _community);
            }
            else
            {//Send SNMPV2 or V3 traps
                if (_isSnmpV3)
                {//sets SNMPV3 parameters
                    SnmpTrapSender.addV3Params(_user, _authProtocol, _authPassword, _privProtocol,
                            _privPassword, _engineID, _host, _port);
                }
                SnmpTrap trap = new SnmpTrap(_sysUpTime, new SnmpOID(_snmpTrapOID));
                trap.addVarBinds(_vblist);
                SnmpTrapSender.sendTrap(_host, _port, trap, true, _community);
//                log.error("Sent Trap:\r\n " + trap.toString());
                logger.error("Sent Trap:\r\n " + trap.toString());
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
            e.printStackTrace();
        }
        catch (SnmpEncodingException ee)
        {
            System.out.println(ee);
            ee.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------
    // Parsing command line options
    // ----------------------------------------------------------------------

    /**
     * Parses non-option arguments
     */
    protected void parseArgs()
    {
        String[] as = _parseArgs.getArguments();
        if (as.length > 0)
        {
            _host = as[0];
        }

        int start = 1;
        while (start < as.length)
        {
            String oid = as[start];
            String type = as[start + 1];
            String value = as[start + 2];
            SnmpDataType t = translate(type, value);
            SnmpVarBind vb = new SnmpVarBind(oid, t);
            _vblist.add(vb);
            start += 3;
        }
    }

    /**
     * Prints usage lines without explanation lines
     */
    protected void usage(String programName, boolean allowMulitpleOIDs)
    {
        System.out.println("Usage: java " + programName +
                " [options...] <hostname> [<OID> <type> <value> ...]\n");
        System.out.println("<OID>\tobject identifier");
        System.out.println("<type>\tdata type of the value, one of i, u, t, a, o, s." +
                " i: INTEGER, u: unsigned INTEGER, t: TIMETICKS, " +
                "a: IPADDRESS, o: OID, s: STRING");
        System.out.println("<value>\tvalue of this object identifier");
    }

    protected void moreOptions()
    {
        System.out.println("-e <e>\ttrap sender's engine ID");
        System.out.println("-t <1|2>\ttrap version, possible values are 1 or 2");
        System.out.println("-s <s>\tsysUpTime or timestamp");
        System.out.println("-q <o>\tsnmpTrapOID");
        System.out.println("-n <n>\tenterprise OID");
        System.out.println("-g <g>\tSNMPv1 generic code");
        System.out.println("-i <g>\tSNMPv1 enterprise specific code");
    }

    protected void printExample(String programName)
    {
        System.out.println("java " + programName +
                " -s 10333 -n .1.3.6.1.2.1.2.2.1.1 -g 2 localhost");
        System.out.println("java " + programName +
                " -s 10333 -n .1.3.6.1.2.1.2.2.1.1 -g 6 -i 20 localhost");
        System.out.println("java " + programName +
                " -t 2 -s 10333 -q .1.3.6.1.2.1.2.2.1.1 " + "localhost");
        System.out.println("java " + programName +
                " -t 2 -s 10333 -q .1.3.6.1.2.1.2.2.1.7 " + "localhost 1.3.6.1.2.1.2.2.1.7.3 i 1");
        System.out.println("java " + programName +
                " -v 3 -u newUser -A abc12345 -X abc12345 -e 12345  -s 10333 -q .1.3.6.1.2.1.2.2.1.1 " +
                "localhost");
    }

    protected void parseOptions(String[] args, String program)
    {
        System.out.println("parseOptions");
        super.parseOptions(args, program);
        String engineID = _parseArgs.getOptionValue('e');
        if (engineID != null)
        {
            _engineID = SnmpBase.getHexString(engineID);
        }
        _trapVersion = Integer.parseInt(_parseArgs.getOptionValue('t', "1"));
        _snmpTrapOID = _parseArgs.getOptionValue('q');
        _enterprise = _parseArgs.getOptionValue('n');
        _generic = Integer.parseInt(_parseArgs.getOptionValue('g', "0"));
        _specific = Integer.parseInt(_parseArgs.getOptionValue('i', "0"));
        _sysUpTime = Long.parseLong(_parseArgs.getOptionValue('s', "0"));
        _port = Integer.parseInt(_parseArgs.getOptionValue('p', "162"));
    }

    protected void printMoreOptions()
    {
        System.out.println("engine ID =\t\t" + _parseArgs.getOptionValue('e'));
        System.out.println("trap version =\t\t" + _trapVersion);
        System.out.println("sysUpTime =\t\t" + _sysUpTime);
        System.out.println("snmpTrapOID =\t\t" + _snmpTrapOID);
        System.out.println("enterpriseOID = \t" + _enterprise);
        System.out.println("SNMPv1 generic =\t\t" + _generic);
        System.out.println("SNMPv1 specific =\t\t" + _specific);
    }

    /**
     * Creates a new instance of ParseArguments
     */
    protected ParseArguments newParseArgumentsInstance(String[] args)
    {
        return new ParseArguments(args, "?ho", "uvaAXxcpmetsqngi");
    }


    public static String getSnmpDetails(long visitId, String errorMessage)
       {
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
           Date now = new Date();
           StringBuffer buf = new StringBuffer();
           buf.append("|application=NVC")
                   .append("|visitId=").append(visitId)
                   .append("|timestamp=")
                   .append(df.format(now))
                   .append("|error msg=")
                   .append(errorMessage);

           return buf.toString();
       }

}// end of class 

