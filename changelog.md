Version 0.2.7
------------------------------------------------------
- Added options for:
	- The amount of each raider per raid level (currently only thru the config file)
	- The health threshold for hunger damage
	- Enabling/disabling zombie spawn reinforcements
	- Enabling/disabling zombies breaking doors
	- Enabling/disabling spider potion effects
	- Enabling/disabling extra lightning fire
	- Enabling/disabling idle wither skulls

------------------------------------------------------
Version 0.2.6
------------------------------------------------------
- Removed a lot of `@Overwite`s
- Added Quilt loader support

------------------------------------------------------
Version 0.2.5
------------------------------------------------------
- Changed the `Phantom` mixin from an `@Overwrite` to a `@ModifyVariable` fixing a crash with architectury

------------------------------------------------------
Version 0.2.4
------------------------------------------------------
- Turned the janky `@Inject`s that were basically `@Overwrite`s into actual `@Overwrite`s
- Added a zombie villager chance mixin
- Changed the config format from TOML to JSON
- Updated to 1.18.1
- Moved special thanks from the mod description to the readme

------------------------------------------------------
Version 0.2.3
------------------------------------------------------
- Updated to 1.18.

------------------------------------------------------
Version 0.2.2
------------------------------------------------------
- Now depends on FAPI
- Fixed translations not working
- Fixed crash when no other mod using TOML is present

------------------------------------------------------
Version 0.2.1
------------------------------------------------------
- Moved subcommands into entity & world
- Changed every message to be a TranslatableText allowing for translations
- Added issues link

------------------------------------------------------
Version 0.2.0
------------------------------------------------------
- Implemented per world config saving thanks to macbrayne
- Cleaned up code

------------------------------------------------------
Version 0.1.2
------------------------------------------------------
- Implemented option saving thru config

------------------------------------------------------
Version 0.1.1
------------------------------------------------------
- Added Changelog
- Added Mod Icon
- Added Anti-Forge image
- Tried and failed to optimize the `AbstractSkeletonEntity` mixin

------------------------------------------------------
Version 0.1.0
------------------------------------------------------
- Initial Release