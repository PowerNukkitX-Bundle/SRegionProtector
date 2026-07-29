# SRegionProtector

SRegionProtector is a flexible region-protection plugin for
[PowerNukkitX](https://github.com/PowerNukkitX/PowerNukkitX). Players can claim
areas, manage members and owners, configure protection flags, and work with
regions through commands or a chest/form UI.

> [!NOTE]
> This repository is a port of the original Nukkit plugin to PowerNukkitX.
> Because much of the code originates from the Nukkit version, it is not
> intended as a reference project for learning the PowerNukkitX API.

## Features

- Cuboid region creation with a selection wand or position commands
- Region owners and members
- Configurable protection flags
- Chest and form-based user interfaces
- YAML, SQLite, MySQL, and PostgreSQL storage
- Optional region creation costs and region trading through LlamaEconomy
- Region priorities, borders, teleportation, and data migration
- English, Russian, and Korean translations
- Asynchronous command execution, loading, and automatic saves

## Requirements

- A current PowerNukkitX server
- Java 21
- [LlamaEconomy](https://powernukkitx.org/plugins/PowerNukkitX-Bundle.LlamaEconomy)
  (optional; required for economy features)

## Installation

1. Download the latest JAR from the
   [GitHub releases page](https://github.com/PowerNukkitX-Bundle/SRegionProtector/releases).
2. Copy the JAR into the server's `plugins` directory.
3. Start the server once to generate the configuration files.
4. Stop the server, adjust the files in `plugins/SRegionProtector/`, and start
   it again.

The default configuration uses YAML storage, so no database setup is required
for a basic installation.

## Quick start

All subcommands are available through `/region` or its alias `/rg`.

1. Run `/rg wand` to receive the selection wand.
2. Select two opposite corners of the area. Alternatively, use `/rg pos1` and
   `/rg pos2`.
3. Check the selection with `/rg size` or `/rg showborder`.
4. Create the region with `/rg create <name>`.
5. Open its interface with `/rg gui <name>` or manage it through commands.

Run `/rg help` in-game to see every command available to you. Commands can
also be registered individually unless `hide-commands` is enabled.

## Common commands

| Command | Description |
| --- | --- |
| `/rg wand` | Gives the region selection wand |
| `/rg pos1 [position]` | Sets the first selection point |
| `/rg pos2 [position]` | Sets the second selection point |
| `/rg create <region>` | Creates a region from the current selection |
| `/rg info [region]` | Shows information about a region |
| `/rg list <owner\|member\|creator>` | Lists matching regions |
| `/rg gui [region]` | Opens the configured region UI |
| `/rg flag <region> <flag> <allow\|deny>` | Changes a region flag |
| `/rg addmember <region> <player>` | Adds a member |
| `/rg removemember <region> <player>` | Removes a member |
| `/rg addowner <region> <player>` | Adds an owner |
| `/rg removeowner <region> <player>` | Removes an owner |
| `/rg teleport <region>` | Teleports to a region |
| `/rg select <region>` | Loads an existing region as the selection |
| `/rg remove <region>` | Deletes a region |

Permissions follow the pattern
`sregionprotector.command.<subcommand>`. For example, `/rg create` requires
`sregionprotector.command.create`. Most player commands are granted by
default; administrative commands and sensitive flags default to operators.
See [`plugin.yml`](src/main/resources/plugin.yml) for the complete permission
tree and defaults.

## Configuration

The plugin creates its files under `plugins/SRegionProtector/`.

| File or directory | Purpose |
| --- | --- |
| `config.yml` | General settings, storage provider, UI, performance, and limits |
| `region-settings.yml` | Default flag values and region-specific settings |
| `DB/` | SQLite, MySQL, and PostgreSQL connection settings |
| `Lang/` | Language files |
| `Regions/` and `Flags/` | Data used by the YAML provider |

Important options in `config.yml` include:

- `provider`: `yaml`, `sqlite`, `mysql`, or `postgresql`
- `language`: `default`, `eng`, `rus`, or `kor`
- `gui-type`: `chest` or `form`
- `active-flags`: enables individual flag handlers; disabled flags do not
  enforce protection
- `default-max-region-size` and `default-max-region-amount`: player limits
- `region-creation-price` and `price-per-block`: economy-based creation costs
- `priority-system`: enables priorities for overlapping regions

> [!IMPORTANT]
> Flags are selectively enabled in `active-flags`. If a flag appears to have
> no effect, check that section before reporting a problem.

### Database storage

Set `provider` in `config.yml`, then edit the corresponding file in `DB/`.

```yaml
# config.yml
provider: mysql
```

For MySQL and PostgreSQL, configure `address`, `port`, `database`, `username`,
and `password`. SQLite only requires its database file setting. Restart the
server after changing providers.

The administrative migration command can copy existing regions between
providers:

```text
/rg migrate <source-provider> <target-provider>
```

Back up the plugin directory and database before migrating production data.

## Building from source

The project uses Maven and targets Java 21.

```bash
git clone https://github.com/PowerNukkitX-Bundle/SRegionProtector.git
cd SRegionProtector
mvn clean package
```

The shaded plugin JAR is written to `target/SRegionProtector.jar`.
PowerNukkitX and LlamaEconomy are provided dependencies and are not bundled
into the artifact.

## Support and contributing

- Report reproducible bugs through
  [GitHub Issues](https://github.com/PowerNukkitX-Bundle/SRegionProtector/issues).
- Existing usage notes are available in the
  [project wiki](https://github.com/SergeyDertan/SRegionProtector/wiki).
- Pull requests are welcome. Please describe the motivation and test the
  resulting JAR on PowerNukkitX.

When reporting an issue, include the PowerNukkitX version, Java version,
SRegionProtector version, selected storage provider, relevant configuration,
and the complete error log.

## License

SRegionProtector is distributed under the
[GNU General Public License v3.0](LICENSE.MD).
