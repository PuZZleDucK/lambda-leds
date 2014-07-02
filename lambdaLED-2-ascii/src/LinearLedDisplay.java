// Raspberry Pi Lambda LEDs.
// By: PuZZleDucK
// Prerequisites:
//      -Java8.
//      -Pi4J & WiringPi.
//      -A Raspberry Pi to run it on.
//      -A LED strip.
//
package org.puzzleduck.rpi;

import com.pi4j.io.gpio.*;
import java.util.*;

public class LinearLedDisplay{ //pin mappings.
  private AbstractDisplay ad;
  private AbstractDisplay adRev;
  private int pulseDuration = 270;
  private int pulseDelay = 55;
  private int endDelay = 255;

  public static void main(String args[]) throws Exception{
    System.out.println(" :: Linear Display Test :: ");

    LinearLedDisplay lld = new LinearLedDisplay(21);
    Thread.sleep(2000); // suspense...
    lld.demo();
  }

  public void demo() throws Exception {
    ad.updateDisplay();
    

//two streams
    for(int loopCount = 0; loopCount < 5; loopCount++) {  //LOOP
      ad.getLEDs().stream().forEach(p -> p.pulsePin(pulseDuration, pulseDelay));
      Thread.sleep(endDelay);
      adRev.getLEDs().stream().forEach(p -> p.pulsePin(pulseDuration, pulseDelay));
      Thread.sleep(endDelay);
    }


    ad.shutdown();
  }

  public LinearLedDisplay(int count) {
    System.out.print(" :: Virtual LedDisplay Init [");
    ad = new AbstractDisplay();

    ad.addPins(new GpioLED(RaspiPin.GPIO_17), new GpioLED(RaspiPin.GPIO_18),
               new GpioLED(RaspiPin.GPIO_20), new GpioLED(RaspiPin.GPIO_19),
               new GpioLED(RaspiPin.GPIO_11), new GpioLED(RaspiPin.GPIO_10),
               new GpioLED(RaspiPin.GPIO_14), new GpioLED(RaspiPin.GPIO_06),
               new GpioLED(RaspiPin.GPIO_13), new GpioLED(RaspiPin.GPIO_12),
               new GpioLED(RaspiPin.GPIO_05), new GpioLED(RaspiPin.GPIO_04),
               new GpioLED(RaspiPin.GPIO_03), new GpioLED(RaspiPin.GPIO_02),
               new GpioLED(RaspiPin.GPIO_01), new GpioLED(RaspiPin.GPIO_00),
               new GpioLED(RaspiPin.GPIO_16), new GpioLED(RaspiPin.GPIO_15),
               new GpioLED(RaspiPin.GPIO_07), new GpioLED(RaspiPin.GPIO_09),
               new GpioLED(RaspiPin.GPIO_08));

    for(int x = 0; x < count; x++) {
      ad.addPin(new AsciiLED());
      System.out.print(x+".");
    }
    System.out.print("\b] :: \n");
    adRev = new AbstractDisplay();
    for(AbstractPin thisPin : ad.getLEDs()) {
      adRev.addPinRev(thisPin);
    }
  }

}

