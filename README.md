# How to run
camel run database.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21

camel run database.camel.yaml --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=src/main/resources/application.properties

#old
camel run src/main/resources/routes/artemis.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.activemq:artemis-jms-client:2.35.0

camel run ftp.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

camel run ftp.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

camel run workflow.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

#Hot reloading & use of custom variables from application.properties
camel run src/main/resources/routes/workflow.camel.yaml --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=src/main/resources/application.properties

camel run src/main/resources/routes/internal.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=src/main/resources/application.properties

camel run src/main/resources/routes/internal2rest.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=src/main/resources/application.properties

camel run src/main/resources/routes/processXMLwithXPath.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=src/main/resources/application.properties

camel run src/main/resources/routes/artemisCF.camel.yaml  --dep=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.qpid:qpid-jms-client:2.5.0 --reload --properties=src/main/resources/application.properties

camel run src/main/resources/routes/restPets.camel.yaml --dep=org.apache.groovy:groovy-all:4.0.23,org.apache.httpcomponents.client5:httpclient5:5.3.1,org.apache.groovy:groovy-json:4.0.23 --reload --properties=src/main/resources/application.properties

#Export
jbang "-Dcamel.jbang.version=4.6.0" camel@apache/camel export --runtime=quarkus --gav=cfl.eua:eisdl:1.0.0 --directory=/tmp/quarkus-sample/