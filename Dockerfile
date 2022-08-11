FROM khipu/openjdk17-alpine

EXPOSE 8080

COPY target/TestBotTelegram-0.0.1-SNAPSHOT.jar telegram-bot.jar

CMD ["java", "-jar", "telegram-bot.jar"]