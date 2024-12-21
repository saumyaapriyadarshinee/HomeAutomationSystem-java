# HomeAutomationSystem-java
The Home Automation System is a Java-based application that simulates a smart home setup. It allows users to manage and control various devices in their home through a terminal-based interface. The system provides functionalities like controlling devices (e.g., lights, thermostat), and logging all activities for future reference.
Features
Device Control: Turn on/off devices (lights, fans, thermostat).
Device Groups: Group devices by room or category for easy management.
Scheduling: Schedule devices to activate at specific times.
Sensor Monitoring: Monitor motion and light levels through simulated sensors.
Logging: Keep track of all activities in the system (e.g., device toggles, schedules).
Brightness Control: Adjust the brightness of lights and track history.
Access Control: Manage users with different roles (admin, user).
Setup
Prerequisites
Java 8 or later.
A terminal/command prompt to run the application.
Running the Application
Clone the repository or copy the source code to your local machine.
Open a terminal and navigate to the directory containing the code.
Compile the Java files:
Copy code
javac HomeAutomationApp.java
Run the application:
Copy code
java HomeAutomationApp
Usage
Login: Use the provided admin and user credentials to log in.

Username: admin, Password: admin123
Username: user1, Password: password
Device Management: Once logged in, you can view devices, toggle them, or control device groups.

To toggle a device, enter its name (e.g., lights, fans).
You can also toggle all devices in a specific group (e.g., "Living Room").
Scheduling Devices: Schedule devices to activate at specific times by entering a device name and time.

Sensor Monitoring: Simulate sensor updates like motion detection, temperature, and humidity levels.

Log Management: View logs to track all system activities.

Example Commands
Login:

makefile
Copy code
Username: admin
Password: admin123
Control Devices:

vbnet
Copy code
Enter device name to toggle: lights
lights is now On
Schedule a Device:

mathematica
Copy code
Enter device name to schedule: lights
Enter time to schedule (e.g., 12:00 PM): 07:00 PM
Adjust Light Brightness:

mathematica
Copy code
Enter device name to adjust brightness: lights
Enter brightness percentage: 75
Display Logs:

yaml
Copy code
Display Logs:
2024-12-21 14:00:00 - Toggled lights
Contribution
Feel free to fork this repository and make improvements. Contributions are welcome for adding more devices, advanced scheduling, or even an actual GUI.

License
This project is open-source and available under the MIT License. See the LICENSE file for more information.
