#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <ArduinoJson.h>
#include <Adafruit_NeoPixel.h>

const char *ssid = "mroczne_modrzewie";        // your wi-fi name
const char *password = "Palanienalanie2";    // your wi-fi password

#define PIN 12

ESP8266WebServer server ( 80 );
Adafruit_NeoPixel strip = Adafruit_NeoPixel(8, PIN, NEO_GRB + NEO_KHZ800);

void handleRoot() {
  server.send(200, "text/plain", "hello from esp8266!");
}

void turnOn() {
  for (int i = 0; i < 8; ++i){
    strip.setPixelColor(i, strip.Color(100, 100, 50));
  }
  strip.show();
  server.send ( 200, "text/html", "" );
}

void turnOff() {
  for (auto i = 0; i < 8; ++i){
    strip.setPixelColor(i, 0);
  }
  strip.show();
  server.send ( 200, "text/html", "" );
}

void setColor() {
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(server.arg("plain"));

  int r = root["red"];
  int g = root["green"];
  int b = root["blue"];

  for (auto i = 0; i < 8; ++i) {
    strip.setPixelColor(i, strip.Color(r, g, b));
  }
  strip.show();
  server.send ( 200, "text/html", "" );
}

void handleNotFound() {

  String message = "File Not Found\n\n";
  message += "URI: ";
  message += server.uri();
  message += "\nMethod: ";
  message += ( server.method() == HTTP_GET ) ? "GET" : "POST";
  message += "\nArguments: ";
  message += server.args();
  message += "\n";

  for ( uint8_t i = 0; i < server.args(); i++ ) {
    message += " " + server.argName ( i ) + ": " + server.arg ( i ) + "\n";
  }

  server.send ( 404, "text/plain", message );
}

void setup ( void ) {
  strip.begin();
  // turn off all the LEDs
  for (auto i = 0; i < 8; ++i){
    strip.setPixelColor(i, 0);
  }
  strip.show();
  
  Serial.begin ( 115200 );
  WiFi.begin ( ssid, password );
  Serial.println ( "" );

  // Wait for connection
  while ( WiFi.status() != WL_CONNECTED ) {
    delay ( 500 );
    Serial.print ( "." );
  }

  Serial.println ( "" );
  Serial.print ( "Connected to " );
  Serial.println ( ssid );
  Serial.print ( "IP address: " );
  Serial.println ( WiFi.localIP() );

  if ( MDNS.begin ( "esp8266" ) ) {
    Serial.println ( "MDNS responder started" );
  }

  server.on ( "/", HTTP_GET, handleRoot );
  server.on("/turnOn", HTTP_POST, turnOn);
  server.on("/turnOff", HTTP_POST, turnOff);
  server.on("/setColor", HTTP_POST, setColor);
  server.onNotFound ( handleNotFound );
  server.begin();
  Serial.println ( "HTTP server started" );
}

void loop ( void ) {
  server.handleClient();
}
