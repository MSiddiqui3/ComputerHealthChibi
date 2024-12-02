# Computer Health Chibi

**Computer Health Chibi** is an interactive application designed to monitor various system information, such as CPU temperature, GPU temperature, storage usage, and more. It features a reactive **chibi avatar** that visually represents the current state of your computer systems, making monitoring both functional and engaging.

---

## Table of Contents

- [Features](#features)
- [How It Works](#how-it-works)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Contributors](#contributors)
- [License](#license)

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

## Getting Started

### Prerequisites

1. Download and install [OpenHardwareMonitor](https://openhardwaremonitor.org/downloads/).
2. Ensure Java is installed on your system. (Minimum Java version: 11)
