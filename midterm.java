import java.util.*;

// Component Interface (Composite Pattern)
interface SmartDevice {
    void operate();
}

// Leaf Nodes (Individual Devices)
class Light implements SmartDevice {
    private String name;
    
    public Light(String name) {
        this.name = name;
    }
    
    @Override
    public void operate() {
        System.out.println(name + " light is ON");
    }
}

class Thermostat implements SmartDevice {
    private int temperature;
    
    public Thermostat(int temperature) {
        this.temperature = temperature;
    }
    
    @Override
    public void operate() {
        System.out.println("Thermostat set to " + temperature + " degrees");
    }
}

// Composite Node (Composite Pattern)
class Room implements SmartDevice {
    private String name;
    private List<SmartDevice> devices = new ArrayList<>();
    
    public Room(String name) {
        this.name = name;
    }
    
    public void addDevice(SmartDevice device) {
        devices.add(device);
    }
    
    @Override
    public void operate() {
        System.out.println("Operating devices in room: " + name);
        for (SmartDevice device : devices) {
            device.operate();
        }
    }
}

// Decorator Pattern
abstract class DeviceDecorator implements SmartDevice {
    protected SmartDevice decoratedDevice;
    
    public DeviceDecorator(SmartDevice device) {
        this.decoratedDevice = device;
    }
}

class ScheduledOperationDecorator extends DeviceDecorator {
    public ScheduledOperationDecorator(SmartDevice device) {
        super(device);
    }
    
    @Override
    public void operate() {
        System.out.println("Scheduled operation initiated");
        decoratedDevice.operate();
    }
}

// Abstract Factory Pattern
interface SmartHomeFactory {
    Light createLight(String name);
    Thermostat createThermostat(int temperature);
}

class BasicSmartHomeFactory implements SmartHomeFactory {
    public Light createLight(String name) {
        return new Light(name);
    }
    public Thermostat createThermostat(int temperature) {
        return new Thermostat(temperature);
    }
}

// Facade Pattern
class SmartHomeController {
    private List<SmartDevice> devices = new ArrayList<>();
    
    public void addDevice(SmartDevice device) {
        devices.add(device);
    }
    
    public void turnAllDevicesOn() {
        for (SmartDevice device : devices) {
            device.operate();
        }
    }
}

// Adapter Pattern
interface ModernLockSystem {
    void lock();
}

class LegacyDoorLock {
    public void oldLockMethod() {
        System.out.println("Legacy door locked.");
    }
}

class LockAdapter implements ModernLockSystem {
    private LegacyDoorLock legacyLock;
    
    public LockAdapter(LegacyDoorLock legacyLock) {
        this.legacyLock = legacyLock;
    }
    
    @Override
    public void lock() {
        legacyLock.oldLockMethod();
    }
}

// Main Demo
public class SmartHomeSystem {
    public static void main(String[] args) {
        SmartHomeFactory factory = new BasicSmartHomeFactory();
        
        Room livingRoom = new Room("Living Room");
        Light livingRoomLight = factory.createLight("Living Room");
        Thermostat livingRoomThermostat = factory.createThermostat(22);
        
        livingRoom.addDevice(livingRoomLight);
        livingRoom.addDevice(livingRoomThermostat);
        
        // Decorator Usage
        SmartDevice scheduledLight = new ScheduledOperationDecorator(livingRoomLight);
        
        // Facade Usage
        SmartHomeController controller = new SmartHomeController();
        controller.addDevice(livingRoom);
        controller.addDevice(scheduledLight);
        
        System.out.println("Turning on all devices:");
        controller.turnAllDevicesOn();
        
        // Adapter Usage
        LegacyDoorLock oldLock = new LegacyDoorLock();
        ModernLockSystem adaptedLock = new LockAdapter(oldLock);
        System.out.println("Using adapted lock:");
        adaptedLock.lock();
    }
}
