// Use or operation of this code is subject to acceptance of the license
// available in the code repository for this code.
//
// Defines a business operation to be used as part of the InterSystems IRIS 2018.1.1 
// First Look: Connecting Systems in InterSystems IRIS Using Java Business Hosts

package JavaHosts;

import com.intersystems.gateway.bh.Production;
import com.intersystems.gateway.bh.Production.Severity;
import com.intersystems.gateway.bh.BusinessOperation;


public class JavaHostsOperation implements BusinessOperation {

  Production production = null;
  @Override
  public boolean OnInit(Production p) throws Exception {
    production = p;
    return true;
  }

  @Override
  public boolean OnTearDown() throws Exception {
    return true;
  }

  @Override
  public boolean OnMessage(String message) throws Exception {
    return true;
  }

}