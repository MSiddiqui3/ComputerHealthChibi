# Computer Health Chibi

**Computer Health Chibi** is an interactive application designed to monitor various system information, such as CPU temperature, GPU temperature, storage usage, and more. It features a reactive **chibi avatar** that visually represents the current state of your computer systems, making monitoring both functional and engaging.

---

## Table of Contents

- [Features](#features)
- [How It Works](#how-it-works)
- [Installation](#installation)
- [Contributors](#contributors)

---

## Features

- **Real-Time System Monitoring**:
  - CPU Information (Name, Physical Cores, Logical Cores, Temperature, Voltage)
  - GPU Information (Name, VRAM, Temperature, Fan Speed)
  - RAM (Total RAM, Used RAM, Free RAM)
  - Storage Information (Drive Name, Serial #, Size, Reads, Writes, Free Space)
  - Network (Interface, Bytes Received, Bytes Sent, Packets Received, Packets Sent)
  - Battery (Name, Remaining Capacity, Time Remaining, Charging, Voltage)
  - Settings (Change Theme, Change Chibi Appearance, Chibi Tracker)

- **Reactive Chibi Avatar**:
  - Dynamically reacts to the current state of the system.
  - Offers visual cues for system health and performance.
  - Multiple options for what the Chibi Avatar will react to
  - Multiple options for Chibi Avatar Appearance

- **Enhanced Integration**:
  - Works seamlessly with [OpenHardwareMonitor](https://openhardwaremonitor.org/downloads/) for improved data access and reliability.

---

## How It Works

The application leverages [OpenHardwareMonitor](https://openhardwaremonitor.org/downloads/) to access system-level information with higher permissions. This integration allows **Computer Health Chibi** to:
1. Collect accurate system data.
2. Display it in real-time using a fun and interactive chibi avatar.
3. Provide users with visual and textual feedback about their computer's health.

The application can run without OpenHardwareMonitor but various information may not be able to get detected. 
---


### Installation

1. Clone The master branch of this repository
   ```
   git clone https://github.com/MSiddiqui3/ComputerHealthChibi
   ```
3. Download and install [OpenHardwareMonitor](https://openhardwaremonitor.org/downloads/).
4. This step is not required but some users may find issues regarding VM options in their IDE specifially IntelliJ if this is the case the next steps are recommended
   Inside the SDK folder, locate the lib directory, which contains all the required .jar files.
   Open the project in IntelliJ IDEA.
   Click on the dropdown menu in the top-right corner (next to the "Run" button) and select Edit Configurations
   In the Run/Debug Configurations window, select your application's configuration (e.g., main).

   Locate the VM options field. If it’s not visible, click on the "Modify options" dropdown and enable Add VM options.

   Add the "lib" javaFX sdk file path to the VM options field:
   Click Apply and then OK to save the changes


   This is the correct format: --module-path "/opt/javafx-sdk/lib" --add-modules
   javafx.controls,javafx.fxml
5. Run the code

###As of 12/1/2024 a jar version is available in the github
The jar file should be able to get downloaded and run on its own. 

#Contributors
* **Jason Urquilla-Martinez** *
* **Augustine Ajua** *
* **Andrew Nosa** *
* **Mohammed Siddiqui** *
   
