# CustomStats

![Project Logo](https://avatars.githubusercontent.com/u/178014793?s=200&v=4) <!-- Add a logo if you have one -->

CustomStats is a powerful and flexible Minecraft plugin designed to track and manage player statistics efficiently. Whether you're running a small server or a large network, CustomStats allows you to store and retrieve player data seamlessly.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Commands](#commands)
- [Config Setup](#config-setup)

## Features

- Track player statistics such as deaths blocks mined and others.
- Store data in a MySQL database for easy retrieval and management.
- Command interface for players and server admins.
- Simple JSON output for player stats, making it easy to integrate with other plugins or systems.

## Installation

1. Download the latest release of CustomStatsPAPI from the [releases page](https://github.com/polarisdev-fr/CustomStatsVanillaPlay/releases).
2. Place the downloaded JAR file into the `plugins` folder of your Minecraft server.
3. Start or restart your server.
4. Configure the plugin settings in `plugins/CustomStats/config.yml`.

## Usage

After installation, you can begin using the plugin commands in your server's chat. The plugin automatically tracks player stats, so you don't need to do anything additional to start collecting data!

## Commands

| Command                | Description                                           |
|------------------------|-------------------------------------------------------|
| `/customstats`         | Displays your current player stats.                   |
| `/customstats <player>`      | Displays the stats for the specified player.          |
| `/resetstats <player>` | **[SOON]** Resets the stats for the specified player. |

## Config Setup

To connect CustomStats your MySQL database, modify the `config.yml` file in the `plugins/CustomStats` directory. Here’s a sample configuration:

```yaml
database:
  url: "jdbc:mysql://localhost:3306/yourdatabase"
  user: "yourusername"
  password: "yourpassword"
api:
  port: 8080
```