# Life Wheel

> Every year leaves a constellation.

Life Wheel is a compact narrative life simulator for desktop. A turn of the wheel advances one year and draws a memory from childhood, education, friendship, work, love, rest, failure, and discovery. Earlier memories unlock later possibilities, while Spirit, Joy, and Insight shape the life that emerges.

This repository now contains a complete playable vertical slice rather than the original CPSC 210 prototype.

## The game

- Play a full run from birth to age 90 in short, one-year turns.
- Follow six named life chapters and 30 authored, prerequisite-aware events.
- Balance Spirit, Joy, and Insight; reaching zero can end a journey early.
- Unlock persistent in-run achievements and one of several epilogues.
- Review a reverse-chronological life journal with every defining memory.
- Save and continue from the main menu. Saves live at `~/.lifewheel/journey.json`.
- Use the animated wheel or press Space for keyboard play.

## Run it

Java 11 or newer and Maven 3.8+ are required.

```bash
mvn clean package
java -jar target/project-g6h9c-1.0.0.jar
```

The packaged JAR is self-contained and includes the original character artwork created for this game.

## Test it

```bash
mvn test
```

The suite currently contains 43 model and persistence tests.

## Architecture

- `model.GameLife` owns the run lifecycle, chapters, scoring, achievements, and endings.
- `model.EventLibrary` owns event eligibility, prerequisite chains, and non-repeating draws.
- `model.Player` owns bounded attributes and their event-driven changes.
- `persistence` provides backwards-compatible JSON saves.
- `ui.GamePanel` provides the custom-rendered Swing presentation, main menu, wheel animation, HUD, journal, and epilogue.
- `src/main/resources/images/wanderer.png` is an original generated production asset bundled on the classpath.

## Release status

The project is a polished, complete desktop game vertical slice and builds as a distributable JAR. A commercial Steam release still requires platform packaging/signing, a Steamworks app ID and SDK integration, store capsule art/screenshots, localization, accessibility QA, audio/music licensing, controller validation, and platform-specific testing. Those are publishing operations rather than missing core gameplay.

## Credits

Design and original prototype: project-g6h9c contributors.
Production redesign, original event writing, UI implementation, and generated character direction: 2026.
