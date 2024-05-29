FROM quay.io/wildfly/wildfly:32.0.0.Final-jdk11
ADD ./target/sleeter.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080