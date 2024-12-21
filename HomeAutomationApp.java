import java.io.*;
import java.text.*;
import java.util.*;

class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean checkPassword(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}

class Device {
    private String name;
    private boolean status;

    public Device(String name) {
        this.name = name;
        this.status = false;
    }

    public String getName() {
        return name;
    }

    public boolean getStatus() {
        return status;
    }

    public void toggle() {
        status = !status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getStatusString() {
        return status ? "On" : "Off";
    }
}

class Thermostat extends Device {
    private int temperature;

    public Thermostat(String name) {
        super(name);
        this.temperature = 22; // default temperature
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        setStatus(true);
    }

    @Override
    public String getStatusString() {
        return "On, Set to " + temperature + "°C";
    }
}

class Light extends Device {
    private int brightness;  // 0 to 100%

    public Light(String name) {
        super(name);
        this.brightness = 0;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
        setStatus(brightness > 0);
    }

    @Override
    public String getStatusString() {
        return "On, Brightness " + brightness + "%";
    }
}

class MotionSensor {
    private boolean motionDetected;

    public MotionSensor() {
        this.motionDetected = false;
    }

    public boolean isMotionDetected() {
        return motionDetected;
    }

    public void detectMotion() {
        this.motionDetected = true;
    }

    public void resetMotion() {
        this.motionDetected = false;
    }
}

class LightSensor {
    private int lightLevel;

    public LightSensor() {
        this.lightLevel = 100; // Default light level in percentage
    }

    public int getLightLevel() {
        return lightLevel;
    }

    public void adjustLightLevel(int level) {
        this.lightLevel = level;
    }
}

class DeviceGroup {
    private String groupName;
    private List<Device> devices;

    public DeviceGroup(String groupName) {
        this.groupName = groupName;
        this.devices = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void toggleAllDevices() {
        for (Device device : devices) {
            device.toggle();
        }
    }

    public void setAllDevicesStatus(boolean status) {
        for (Device device : devices) {
            device.setStatus(status);
        }
    }

    public void displayDevices() {
        System.out.println("Devices in " + groupName + ":");
        for (Device device : devices) {
            System.out.println(device.getName() + ": " + device.getStatusString());
        }
    }
}

class LogEntry {
    private String action;
    private String timestamp;

    public LogEntry(String action) {
        this.action = action;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getAction() {
        return action;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

class LogSystem {
    private List<LogEntry> logs;

    public LogSystem() {
        this.logs = new ArrayList<>();
    }

    public void addLog(String action) {
        logs.add(new LogEntry(action));
    }

    public void displayLogs() {
        System.out.println("System Logs:");
        for (LogEntry log : logs) {
            System.out.println(log.getTimestamp() + " - " + log.getAction());
        }
    }
}

class HomeAutomationSystem {
    private List<User> users;
    private Map<String, Device> devices;
    private Map<String, DeviceGroup> deviceGroups;
    private List<String> schedules;
    private Map<String, Integer> sensorData;
    private User authenticatedUser;
    private MotionSensor motionSensor;
    private LightSensor lightSensor;
    private Map<String, Integer> brightnessHistory;
    private LogSystem logSystem;
    private static final String SCHEDULES_FILE = "schedules.txt";

    public HomeAutomationSystem() {
        users = new ArrayList<>();
        devices = new HashMap<>();
        deviceGroups = new HashMap<>();
        schedules = new ArrayList<>();
        sensorData = new HashMap<>();
        motionSensor = new MotionSensor();
        lightSensor = new LightSensor();
        authenticatedUser = null;
        brightnessHistory = new HashMap<>();
        logSystem = new LogSystem();

        // Add some default devices
        devices.put("lights", new Light("Lights"));
        devices.put("fans", new Device("Fans"));
        devices.put("thermostat", new Thermostat("Thermostat"));

        // Create device groups
        DeviceGroup livingRoom = new DeviceGroup("Living Room");
        livingRoom.addDevice(devices.get("lights"));
        livingRoom.addDevice(devices.get("fans"));
        deviceGroups.put("Living Room", livingRoom);

        // Add default sensor data
        sensorData.put("motion", 0); // 0: No motion, 1: Motion detected
        sensorData.put("temperature", 22); // Default temperature in Celsius
        sensorData.put("humidity", 40); // Default humidity in percentage

        // Load previous schedules
        loadSchedules();
    }

    public void addUser(String username, String password, String role) {
        users.add(new User(username, password, role));
    }

    public boolean authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                authenticatedUser = user;
                logSystem.addLog(username + " logged in.");
                return true;
            }
        }
        return false;
    }

    public void displayDevices() {
        System.out.println("Current Device Status:");
        for (Device device : devices.values()) {
            System.out.println(device.getName() + ": " + device.getStatusString());
        }
    }

    public void toggleDevice(String deviceName) {
        if (devices.containsKey(deviceName)) {
            devices.get(deviceName).toggle();
            logSystem.addLog("Toggled " + deviceName);
            System.out.println(deviceName + " is now " + devices.get(deviceName).getStatusString());
        } else {
            System.out.println("Device not found.");
        }
    }

    public void toggleDeviceGroup(String groupName) {
        if (deviceGroups.containsKey(groupName)) {
            deviceGroups.get(groupName).toggleAllDevices();
            logSystem.addLog("Toggled all devices in " + groupName);
            deviceGroups.get(groupName).displayDevices();
        } else {
            System.out.println("Group not found.");
        }
    }

    public void setTemperature(int temperature) {
        Thermostat thermostat = (Thermostat) devices.get("thermostat");
        if (thermostat != null) {
            thermostat.setTemperature(temperature);
            logSystem.addLog("Set thermostat to " + temperature + "°C.");
            System.out.println("Thermostat set to " + temperature + "°C.");
        } else {
            System.out.println("Thermostat not available.");
        }
    }

    public void scheduleDevice(String deviceName, String time) {
        String schedule = "Schedule " + deviceName + " at " + time;
        schedules.add(schedule);
        saveScheduleToFile(schedule);
        logSystem.addLog("Scheduled " + deviceName + " at " + time);
        System.out.println("Scheduled " + deviceName + " at " + time);
    }

    public void checkSchedules() {
        // Simulate checking schedules (for simplicity, we print out all schedules)
        System.out.println("Checking schedules:");
        for (String schedule : schedules) {
            System.out.println(schedule);
        }
    }

    public void updateSensorData() {
        // Simulate sensor data update
        sensorData.put("motion", new Random().nextInt(2)); // Random motion detection (0 or 1)
        sensorData.put("temperature", 18 + new Random().nextInt(10)); // Random temperature (18 to 28)
        sensorData.put("humidity", 30 + new Random().nextInt(30)); // Random humidity (30 to 60)
    }

    public void displaySensorData() {
        System.out.println("Sensor Data:");
        System.out.println("Motion Detected: " + (sensorData.get("motion") == 1 ? "Yes" : "No"));
        System.out.println("Temperature: " + sensorData.get("temperature") + "°C");
        System.out.println("Humidity: " + sensorData.get("humidity") + "%");
    }

    public void detectMotion() {
        motionSensor.detectMotion();
        logSystem.addLog("Motion detected.");
        System.out.println("Motion detected by the sensor.");
    }

    public void resetMotion() {
        motionSensor.resetMotion();
        logSystem.addLog("Motion sensor reset.");
        System.out.println("Motion sensor reset.");
    }

    public void setLightBrightness(String deviceName, int brightness) {
        if (devices.containsKey(deviceName) && devices.get(deviceName) instanceof Light) {
            Light light = (Light) devices.get(deviceName);
            light.setBrightness(brightness);
            brightnessHistory.put(deviceName, brightness);
            logSystem.addLog(deviceName + " brightness set to " + brightness + "%");
            System.out.println(deviceName + " brightness set to " + brightness + "%");
        } else {
            System.out.println("Light device not found.");
        }
    }

    public void displayBrightnessHistory() {
        System.out.println("Brightness History:");
        for (Map.Entry<String, Integer> entry : brightnessHistory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + "%");
        }
    }

    public void adjustLightSensor(int lightLevel) {
        lightSensor.adjustLightLevel(lightLevel);
        logSystem.addLog("Light sensor adjusted to " + lightLevel + "%.");
        System.out.println("Light sensor adjusted to " + lightLevel + "%.");
    }

    private void loadSchedules() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(SCHEDULES_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                schedules.add(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("No previous schedules found.");
        }
    }

    private void saveScheduleToFile(String schedule) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULES_FILE, true));
            writer.write(schedule);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving schedule.");
        }
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public MotionSensor getMotionSensor() {
        return motionSensor;
    }

    public LightSensor getLightSensor() {
        return lightSensor;
    }

    public LogSystem getLogSystem() {
        return logSystem;
    }
}

public class HomeAutomationApp {
    private static Scanner scanner = new Scanner(System.in);
    private static HomeAutomationSystem system = new HomeAutomationSystem();

    public static void main(String[] args) {
        system.addUser("admin", "admin123", "admin");
        system.addUser("user1", "password", "user");

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                login();
            } else if (option == 2) {
                break;
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (system.authenticateUser(username, password)) {
            System.out.println("Welcome " + username + "!");
            userMenu();
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n1. Display Devices");
            System.out.println("2. Toggle Device");
            System.out.println("3. Toggle Device Group");
            System.out.println("4. Set Temperature");
            System.out.println("5. Schedule Device");
            System.out.println("6. Check Schedules");
            System.out.println("7. Update Sensor Data");
            System.out.println("8. Display Sensor Data");
            System.out.println("9. Detect Motion");
            System.out.println("10. Reset Motion Sensor");
            System.out.println("11. Set Light Brightness");
            System.out.println("12. Display Brightness History");
            System.out.println("13. Adjust Light Sensor");
            System.out.println("14. Display Logs");
            System.out.println("15. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    system.displayDevices();
                    break;
                case 2:
                    toggleDevice();
                    break;
                case 3:
                    toggleDeviceGroup();
                    break;
                case 4:
                    setTemperature();
                    break;
                case 5:
                    scheduleDevice();
                    break;
                case 6:
                    system.checkSchedules();
                    break;
                case 7:
                    system.updateSensorData();
                    break;
                case 8:
                    system.displaySensorData();
                    break;
                case 9:
                    system.detectMotion();
                    break;
                case 10:
                    system.resetMotion();
                    break;
                case 11:
                    setLightBrightness();
                    break;
                case 12:
                    system.displayBrightnessHistory();
                    break;
                case 13:
                    adjustLightSensor();
                    break;
                case 14:
                    system.getLogSystem().displayLogs();
                    break;
                case 15:
                    system.getLogSystem().addLog(system.getAuthenticatedUser().getUsername() + " logged out.");
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void toggleDevice() {
        System.out.print("Enter device name to toggle: ");
        String deviceName = scanner.nextLine();
        system.toggleDevice(deviceName);
    }

    private static void toggleDeviceGroup() {
        System.out.print("Enter device group name to toggle: ");
        String groupName = scanner.nextLine();
        system.toggleDeviceGroup(groupName);
    }

    private static void setTemperature() {
        System.out.print("Enter temperature to set: ");
        int temperature = scanner.nextInt();
        system.setTemperature(temperature);
    }

    private static void scheduleDevice() {
        System.out.print("Enter device name to schedule: ");
        String deviceName = scanner.nextLine();
        System.out.print("Enter time to schedule (e.g., 12:00 PM): ");
        String time = scanner.nextLine();
        system.scheduleDevice(deviceName, time);
    }

    private static void setLightBrightness() {
        System.out.print("Enter device name to adjust brightness: ");
        String deviceName = scanner.nextLine();
        System.out.print("Enter brightness percentage: ");
        int brightness = scanner.nextInt();
        system.setLightBrightness(deviceName, brightness);
    }

    private static void adjustLightSensor() {
        System.out.print("Enter light level to adjust sensor: ");
        int lightLevel = scanner.nextInt();
        system.adjustLightSensor(lightLevel);
    }
}
