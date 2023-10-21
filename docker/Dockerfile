FROM openjdk:17
WORKDIR /
ADD ./exchange-rate-converter-*.jar exchange-rate-converter.jar
ADD ./offline_data/*.json /opt/exchange-rate-converter/offline_data/
ADD ./offline_data/rates/*.json /opt/exchange-rate-converter/offline_data/rates/
EXPOSE 8082
CMD java -jar exchange-rate-converter.jar