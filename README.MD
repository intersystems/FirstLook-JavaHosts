Use or operation of this code is subject to acceptance of the license available in the code repository for this code.

Download this sample to your system. 

You can find instructions on setting up the production for this sample in: <a href="http://docs.intersystems.com/iris201811/csp/docbook/DocBook.UI.Page.cls?KEY=AFL_javabusinesshosts">First Look: Connecting Systems in InterSystems IRIS Using Java Business Hosts</a>.
   
The jar files in the repository Releases were created using the following jdk commands:

mkdir JavaHostsOperationOut
mkdir JavaHostsServiceOut

javac -classpath C:\InterSystems\IRIS\dev\java\lib\JDK18\intersystems-gateway-3.0.0.jar -d JavaHostsOperationOut src\JavaHosts\JavaHostsOperation.java

javac -classpath C:\InterSystems\IRIS\dev\java\lib\JDK18\intersystems-gateway-3.0.0.jar -d JavaHostsServiceOut src\JavaHosts\JavaHostsService.java

cd JavaHostsOperationOut

jar cf ..\Releases\JavaHostsOperation.jar JavaHosts

cd ..\JavaHostsServiceOut

jar cf ..\Releases\JavaHostsService.jar JavaHosts

You should modify the classpath to reflect the directory where InterSystems IRIS is installed.
   