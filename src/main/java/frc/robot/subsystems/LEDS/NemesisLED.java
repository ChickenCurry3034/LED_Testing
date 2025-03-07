package frc.robot.subsystems.LEDS;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LEDConstants;

public class NemesisLED extends SubsystemBase {
  private final AddressableLED led;
  private final AddressableLEDBuffer ledBuffer;
  private final int length;
  private int hue = 0;

  /**
   * New NemesisLED object
   *
   * @param port - PWM port the LEDs are connected to
   * @param bufferLength - number of LEDs
   */
  public NemesisLED(int port, int bufferLength) {
    led = new AddressableLED(port);
    ledBuffer = new AddressableLEDBuffer(bufferLength);
    length = ledBuffer.getLength();
    led.setLength(length);
    led.setData(ledBuffer);
    led.start();
  }

  public Command setRGB(int r, int g, int b) {
    return Commands.run(
      () -> {
        for (int i = 0; i < length; i++) {
          ledBuffer.setRGB(i, g, r, b);
        }
        led.setData(ledBuffer);
      });
  }

  public Command setRainbow() {
    return Commands.run(
      () -> {
        int firstPixelHue = 0;
        for (var i = 0; i < ledBuffer.getLength(); i++) {
          final var rainbowHue = (firstPixelHue + (i * 180 / ledBuffer.getLength())) % 180;
          ledBuffer.setHSV(i, rainbowHue, 255, 128);
        }
        firstPixelHue += 3;
        firstPixelHue %= 180;
        led.setData(ledBuffer);
      });
  }

  public Command setBreathing() {
    return Commands.run(
      () -> {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
          ledBuffer.setHSV(i, hue, 255, 128);
        }
        hue += 1;
        led.setData(ledBuffer);
      });
  }

  public Command setFlow() {
    return Commands.run(
      () -> {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
          final var rainbowHue = (hue + (i * 180 / ledBuffer.getLength())) % 180;
          ledBuffer.setHSV(i, rainbowHue, 255, 128);
        }
        hue += 3;
        hue %= 180;
        led.setData(ledBuffer);
      });
  }

  public Command setCandyCane() {
    return Commands.run(
      () -> {
        for (var i = 0; i < ledBuffer.getLength(); i++) {
          if (i % 2 == 0) {
            ledBuffer.setRGB(i, 0, 255, 0);
          } else {
            ledBuffer.setRGB(i, 255, 255, 255);
          }
        }
        led.setData(ledBuffer);
      });
  }

  public Command setColor(LEDConstants.Colors color) {
    return Commands.run(() -> setRGB(color.r, color.g, color.b));
  }

  private int candyCaneFlowTime = 0;
  private final int buffer = 10;

  public Command setCandyCaneFlow() {
    return Commands.run(
      () -> {
        candyCaneFlowTime += 0.2;
        for (var i = 0; i < ledBuffer.getLength(); i++) {
          if ((i + candyCaneFlowTime) % buffer > buffer / 2) {
            ledBuffer.setRGB(i, 0, 255, 0);
          } else {
            ledBuffer.setRGB(i, 255, 255, 255);
          }
        }
        led.setData(ledBuffer);
      });
  }

  private int blinkingTime = 0;

  public Command setBlinking(LEDConstants.Colors color) {
    return Commands.run(
      () -> {
        if (blinkingTime % 5 == 0) {
          setColor(color);
        } else {
          off();
        }
        blinkingTime += 1;
      });
  }

  public Command off() {
    return Commands.run(
    	() -> {
        for (int i = 0; i < length; i++) {
          ledBuffer.setRGB(i, 0, 0, 0);
        }
        led.setData(ledBuffer);
      });
  }
}