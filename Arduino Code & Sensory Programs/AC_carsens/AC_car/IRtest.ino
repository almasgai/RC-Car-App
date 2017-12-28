#define IR 2
int detection = HIGH;    // no obstacle
void setup() {
  Serial.begin(9600);
  pinMode(IR, INPUT);
}
void loop() {
  detection = digitalRead(IR);
  if(detection == LOW){
    Serial.print("There is an obstacle!\n");
  }
  else{
    Serial.print("No obstacle!\n");
  }
  delay(500);    // in ms
}