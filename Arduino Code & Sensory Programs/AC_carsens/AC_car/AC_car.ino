char data;                //Variable for storing received data

#include <NewPing.h>
//ultrasound
#define TRIGGER_PIN  4
#define ECHO_PIN     3
#define MAX_DISTANCE 200
NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

//left side motor
#define RVL 8
#define FWDL  9
#define LCNTRL 6
//ride side motor
#define FWDR 10
#define RVR  11
#define RCNTRL 13

int pw = 80;

static boolean STOP = false;
static boolean left = false;
static boolean right = false;

//IR sensor
#define IRr 2
int detectionR = HIGH; //no obstacle

void setup()
{
  Serial.begin(9600);         //Sets the data rate in bits per second (baud) for serial data transmission
  pinMode(IRr, INPUT);

  pinMode(LCNTRL , OUTPUT);
  pinMode(FWDL, OUTPUT);
  pinMode(RVL, OUTPUT);

}
void loop()
{
  if(Serial.available() > 0)  // Send data only when you receive data:
  {
    data = Serial.read();      //Read the incoming data and store it into variable data
    Serial.println(data);
    if(data == 'd')            //Checks whether value of data is equal to
      forward();
    else if(data == 'b')       //Checks whether value of data is equal to
      reverse();
    else if (data == 'l')
      goLeft();
    else if (data == 'r')
      goRight();
    else if (data == 'a')
      sensor();
    else if (data == 's')
      danger();
  }

}

void sensor() {
  int sonarD = sonar.ping_cm();
  forward();
  do {
    sonarD = sonar.ping_cm();
    delay(100);
  }while(sonarD >= 15 || sonarD == 0);

  danger();

  detectionR = digitalRead(IRr);

  if (detectionR == HIGH){
    right = true;
  }
  else {
    left = true;
  }

  if (STOP){
    reverse();
    danger();
  }

  if (left){
    goLeft();
    danger();
  }
  else if (right){
    goRight();
    danger();
  }
}

void danger() {
  digitalWrite(FWDL, LOW);
  digitalWrite(RVL, LOW);
  analogWrite(LCNTRL, 0);
  digitalWrite(FWDR, LOW);
  digitalWrite(RVR, LOW);
  analogWrite(RCNTRL, 0);
  delay(100);
  STOP = true;
  delay(1000);
}

void reverse(){
  digitalWrite(FWDL, LOW);
  digitalWrite(RVL, HIGH);
  analogWrite(LCNTRL, pw);
  digitalWrite(FWDR, LOW);
  digitalWrite(RVR, HIGH);
  analogWrite(RCNTRL, pw);
  STOP = false;
  delay(500);
}

void forward(){
  digitalWrite(RVL, LOW);
  digitalWrite(FWDL, HIGH);
  analogWrite(LCNTRL, pw);
  digitalWrite(RVR, LOW);
  digitalWrite(FWDR, HIGH);
  analogWrite(RCNTRL, pw);
  STOP = false;
}

void goLeft(){
  digitalWrite(RVL, LOW);
  digitalWrite(RVR, LOW);
  digitalWrite(FWDR, LOW);
  analogWrite(RCNTRL, 0);

  digitalWrite(FWDL, HIGH);
  analogWrite(LCNTRL, pw);
  left = false;
  delay(750);
}

void goRight(){
  digitalWrite(FWDL, LOW);
  digitalWrite(RVL, LOW);
  digitalWrite(RVR, LOW);

  digitalWrite(FWDR, HIGH);
  analogWrite(RCNTRL, pw);
  right = false;
  delay(750);
}
void finish(){
  digitalWrite(FWDL, LOW);
  digitalWrite(RVL, LOW);
  analogWrite(LCNTRL, 0);
  digitalWrite(FWDR, LOW);
  digitalWrite(RVR, LOW);
  analogWrite(RCNTRL, 0);
  delay(10000);
}


