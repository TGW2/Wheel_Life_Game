# Life Wheel

### 2026.07.11

#### The project pivoted from a campus adventure concept to **Life Wheel**. The core loop became clear: spin the wheel → trigger an event → affect stats (Sanity / Mood / Wisdom) → grow older → game over when any stat hits zero.

Built the initial event pool across three life stages:

- **Early Childhood (0–6)**: Eating, Sleeping, Crying, Being Hugged, Laughing
- **Elementary (7–12)**: Attending Class, Playing with Friends, Forgetting Homework, Playing Sports, Making a New Friend
- **Teen (13–15)**: Violating School Rules, Entering a Relationship, Failing an Exam, Becoming Disappointed with Life

---

### 2026.07.12

#### Dropped Java Swing, moved to console. Core systems built:

- `Player` — stats, state updates, game-over checks
- `Event` — age range, stat changes, event codes
- `EventLibrary` — 15+ events, age-based filtering, wheel spin
- `GameApp` — full CLI interaction flow
- `Main` — entry point

---

### 2026.07.14

#### Event pool expanded significantly. Prerequisite system shipped.

**New Events**

- Early childhood +5: Hopscotch, A Childhood Friend, Bedwetting, Looking to Be an Adult, Lying to Parents
- High school +3: Join a Club, Skipping Class, Get a Part-Time Job
- Relationship chain +5: In a Lovership → Broke Up / Engaged → Get Married → Divorced (prerequisite-linked)
- Math chain +3: Into Math → Love in Math → Master in Math (prerequisite-linked)
- All-age universal +5: Eating, Sleeping, Crying, Hugging, Laughing

**New Systems**

- Prerequisite event system: `checkPrerequistieEligible`, `addGoneThroughEvents`, `checkGoneThroughEvents`, `prerequistieEventAlternation`
- Events can now be recorded, queried, and alter the future event pool

**Player Changes**

- Initial stats adjusted: Sanity → 30, Mood → 30
- `roundCheck` implemented — checks all player stats each turn, decides whether to continue or end the game

**Achievements**

- Added achievement status tracking
- First achievement: `mathMaster`
