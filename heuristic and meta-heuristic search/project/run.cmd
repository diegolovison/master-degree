java -jar -server -Dcom.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump=false -Dfile.encoding=UTF-8 -Xms256m -Xmx1024m -XX:MaxPermSize=512M -XX:-HeapDumpOnOutOfMemoryError -Dsun.rmi.dgc.client.gcInterval=1800000 -Dsun.rmi.dgc.server.gcInterval=1800000 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+DisableExplicitGC target/project-1.0-SNAPSHOT.jar %1 %2