FROM quay.io/wildfly/wildfly:32.0.0.Final-jdk11
ADD ./build/libs/web3-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8080