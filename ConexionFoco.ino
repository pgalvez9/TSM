#include <ESP8266WiFi.h>     
#include <ESP8266HTTPClient.h>              
 
const char* ssid = "INFINITUM24Q5_2.4";       
const char* password = "X6V7HzbWwN";                  
 
int PinLED = 0;                  
int estado = LOW;     
void setup() 
{
  delay(10);
  Serial.begin(115200);
  WiFi.begin(ssid,password);
  pinMode(PinLED, OUTPUT); 

  while(WiFi.status() != WL_CONNECTED)
  {
    delay(500);
  }
  //Llegado este punto debería ya haberse conectado

}

void loop() 
{
  if(WiFi.status()==WL_CONNECTED)
  {
    HTTPClient http;
    
    http.begin("https://tsmpjgv9.000webhostapp.com/luz.php");
    http.addHeader("Content-Type", "application/x-www-form-urlencoded"); //Dudo de si es necesaria esta línea

    String respuesta = http.getString();
    Serial.print("Respuesta: "+respuesta);
    if (respuesta [12]=='1')
    {
      digitalWrite(PinLED, HIGH);
    }
    else
    {
      digitalWrite(PinLED, LOW);
    }
    http.end();
    delay(6000);
  }
}
