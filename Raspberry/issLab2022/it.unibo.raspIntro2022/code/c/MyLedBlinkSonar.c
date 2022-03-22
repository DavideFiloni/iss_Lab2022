#include <iostream>
#include <wiringPi.h>
#include <fstream>
#include <cmath>

#include <pthread.h>


#define TRIG 0
#define ECHO 2

#define LED 6

#define CLOSE 18
#define MEDIUM 21
#define FAR 60

#define POS_LEFT 0.055
#define POS_RIGHT 0.24
#define POS_FORWARD 0.14

#define SOGLIA 10

using namespace std;
/*
g++  SonarAlone.c -l wiringPi -o  SonarAlone
 */

// VARIABILI GLOBALI
int isBlink = 0;

void setup() {
        //cout << "setUp " << endl;
        wiringPiSetup();
        pinMode(TRIG, OUTPUT);
        pinMode(ECHO, INPUT);
        //TRIG pin must start LOW
        digitalWrite(TRIG, LOW);
        // set LED
        pinMode(LED, OUTPUT);
        digitalWrite(LED, LOW); // led start switch off
        delay(30);
}

int getCM() {
        //Send trig pulse
        digitalWrite(TRIG, HIGH);
        delayMicroseconds(20);
        digitalWrite(TRIG, LOW);

        //Wait for echo start
        while(digitalRead(ECHO) == LOW);

        //Wait for echo end
        long startTime = micros();
        while(digitalRead(ECHO) == HIGH);
        long travelTime = micros() - startTime;

        //Get distance in cm
        int distance = travelTime / 58;

        return distance;
}

void *blink ( void* _intervalOfSleep) {
        int intervalOfSleep = (int) _intervalOfSleep;
		int result = 0;

        while ( isBlink ) {
                digitalWrite(LED, HIGH);
                delay(intervalOfSleep);
                digitalWrite(LED, LOW);
                delay(intervalOfSleep);
        }
		pthread_exit ((void*)result);
        return NULL;
}

void doBlink (int cm) {
        pthread_t th;
        void *return_val;
        int intervalOfSleep = 200;
        int esito;
		
		delay(300);
        if ( cm <= SOGLIA) {

                if ( isBlink == 1 ){
                        isBlink = 0;
                        esito = pthread_join (th, &return_val);

                        if(esito != 0) printf("Join fallito\n");

                        if (return_val == PTHREAD_CANCELED)
                            printf("Il thread è stato cancellato\n");
                }
                else { // isBlink == 0
                        isBlink = 1;
                        int rc = pthread_create (&th, NULL, blink, (void *) intervalOfSleep);
                        if (rc) {
                                printf("ERORRE durante la creazione del thread return code: %d\n", rc);
                                exit(EXIT_FAILURE);
                        }
                }
        }
        else
            digitalWrite(LED, LOW);

}

int main(void) {
        int cm ;
        setup();
        while(1) {
                cm = getCM();

                if (cm < 100) { // altrimenti blocca il flusso
                    doBlink(cm);
                    cout <<  cm   << endl ;  //flush after ending a new line
                    delay(30);
                }
                else
                    digitalWrite(LED, LOW);
		}
        return 0;
}