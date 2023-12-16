FROM openjdk:20-jdk-oracle

EXPOSE 5500

ADD target/MoneyTransferServiceApp-0.0.1-SNAPSHOT.jar mtsapp.jar

CMD ["java", "-jar", "mtsapp.jar"]