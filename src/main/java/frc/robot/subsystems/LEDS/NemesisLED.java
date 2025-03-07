package frc.robot.subsystems.LEDS;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;

/** Control REV Robotics Blinkin LED controller */
public class NemesisLED extends SubsystemBase {
  private AddressableLED led;
  private AddressableLEDBuffer buffer;

  public NemesisLED(int port, int length) {
    led = new AddressableLED(port);
    buffer = new AddressableLEDBuffer(length);
    led.setLength(length);
    led.setData(buffer);
    led.start();

  }

  @Override
  public void periodic() {
    // LEDPattern.solid(Color.kBlue).applyTo(buffer);
    led.setData(buffer);
  }

  /**
   * Set LED pattern
   *
   * @param pattern Desired LED light pattern
   */

  public Command start() {
    return runOnce(led::start);
  }

  public Command setColor(Color color){
    return runOnce(() -> LEDPattern.solid(color).applyTo(buffer));
  }

  public void setColorVoid(Color color){
    LEDPattern.solid(color).applyTo(buffer);
  }

  // public Command setColor(Color color) {
  //   LEDPattern pattern = LEDPattern.solid(color);
  //   pattern.applyTo(buffer);
  //   return runOnce(() -> led.setData(buffer));
  // }
}
