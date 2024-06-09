# How to run
camel run database.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21

camel run database.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

camel run artemis.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

camel run ftp.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

camel run ftp.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

camel run workflow.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21

#Hot reloading & use of custom variables from application.properties
camel run src/main/resources/routes/internal.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=application.properties

camel run src/main/resources/routes/internal2rest.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=application.properties

camel run src/main/resources/routes/processXMLwithXPath.camel.yaml  --deps=org.apache.camel:camel-netty-http:4.5.0,org.apache.groovy:groovy-xml:4.0.21,org.apache.groovy:groovy-json:4.0.21,org.apache.groovy:groovy-sql:4.0.21,org.postgresql:postgresql:42.7.3 --reload --properties=application.properties