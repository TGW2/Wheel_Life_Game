# 2026 年 7 月 11 日

## 项目方向调整

### Project Direction Adjustment

### 中文

项目主题正式调整为 **“大转盘人生”**。相比原有的校园冒险游戏设计，该方案的核心机制更加清晰，项目规模也更适合当前开发阶段。

游戏的主要机制如下：

- 玩家通过转动转盘随机触发人生事件。
- 每个事件会影响角色的部分属性。
- 当前主要属性包括：
  - 理智值 `San`
  - 心情值 `Mood`
  - 智慧值 `Wisdom`
- 玩家每转动两次转盘，有机会增长一岁。
- 不同年龄阶段拥有不同的事件池。
- 当角色的理智值或心情值下降至 `0` 时，游戏结束。

### English

The project theme was officially changed to **Life Wheel**. Compared with the original campus adventure concept, the new design provides a clearer core mechanic and a more manageable project scope.

The main game mechanics are:

- The player spins a wheel to trigger a random life event.
- Each event affects one or more player attributes.
- The current primary attributes are:
  - Sanity (`San`)
  - Mood
  - Wisdom
- The character may age by one year after every two wheel spins.
- Different age groups have different event pools.
- The game ends when either the player’s sanity or mood reaches `0`.

---

## 游戏事件系统设计

### Event System Design

事件根据玩家年龄划分为不同阶段。每个事件包含事件描述、可发生的年龄范围，以及对玩家属性产生的影响。

### 婴幼儿阶段：0–6 岁

#### Early Childhood: Ages 0–6

| 事件   | English Description | San 变化 | Mood 变化 |
| ------ | ------------------- | -------: | --------: |
| 吃饭   | Eating              |       +2 |        +4 |
| 睡觉   | Sleeping            |       +4 |        +4 |
| 哭泣   | Crying              |       -1 |        -5 |
| 被拥抱 | Being Hugged        |       +5 |       +10 |
| 大笑   | Laughing            |       +1 |        +1 |

### 小学阶段：7–12 岁

#### Elementary School: Ages 7–12

| 事件       | English Description  | San 变化 | Mood 变化 |
| ---------- | -------------------- | -------: | --------: |
| 上课       | Attending Class      |       -2 |        -2 |
| 和朋友玩   | Playing with Friends |       +4 |        +3 |
| 忘记做作业 | Forgetting Homework  |        0 |        -4 |
| 参加运动   | Playing Sports       |       +2 |        +4 |
| 结交新朋友 | Making a New Friend  |       +3 |        +2 |

### 青少年阶段：13–15 岁

#### Teenage Years: Ages 13–15

| 事件           | English Description             | San 变化 | Mood 变化 |
| -------------- | ------------------------------- | -------: | --------: |
| 违反校规       | Violating School Rules          |        0 |        -3 |
| 开始恋爱       | Entering a Relationship         |       +6 |        +9 |
| 考试失利       | Failing an Important Exam       |       -5 |        -7 |
| 对人生感到失望 | Becoming Disappointed with Life |     -100 |      -100 |

其中，事件 **“对人生感到失望”** 会使玩家的理智值和心情值直接下降至最低值，从而触发游戏结束条件。

The event **“Becoming Disappointed with Life”** reduces both sanity and mood significantly and therefore triggers the game-over condition.

---

# 2026 年 7 月 12 日

## UI 架构重构

### UI Architecture Refactoring

### 中文

为了降低当前阶段的实现复杂度，并将开发重点集中在核心逻辑上，项目移除了原有的 Java Swing 图形用户界面代码，并迁移至基于控制台的交互模式。

本次重构完成了以下内容：

- 移除所有 Java Swing 相关代码。
- 删除原有的图形界面组件。
- 将用户输入与游戏输出迁移至控制台。
- 实现完整的命令行交互流程。
- 保留模型层与用户界面层之间的职责划分。

### English

To reduce implementation complexity and focus development on the core game logic, the Java Swing graphical user interface was removed and replaced with a console-based interaction model.

The refactoring included:

- Removal of all Java Swing-related code.
- Removal of the previous graphical interface components.
- Migration of user input and game output to the console.
- Implementation of a complete command-line interaction flow.
- Preservation of the separation between the model layer and the user interface layer.

---

## 核心系统开发

### Core System Development

### 1. 玩家系统

#### Player System

创建并完成了 `Player` 类的基础实现。

`Player` 类负责保存玩家状态，并提供与玩家属性相关的行为。

#### 玩家属性

| 属性   | Java 字段含义 |     初始值 |
| ------ | ------------- | ---------: |
| 名字   | Name          | 由玩家输入 |
| 出生地 | Birthplace    | 由玩家输入 |
| 年龄   | Age           |          0 |
| 理智值 | Sanity / San  |         60 |
| 心情值 | Mood          |         50 |
| 智慧值 | Wisdom        |         60 |

#### 已实现功能

- 初始化玩家基本信息。
- 获取玩家当前属性。
- 修改玩家年龄。
- 根据事件结果更新玩家状态。
- 将属性值限制在有效范围内。
- 判断玩家是否满足游戏结束条件。

---

### 2. 事件系统

#### Event System

创建了 `Event` 类，用于表示游戏中的单个人生事件。

每个 `Event` 对象包含以下字段：

| 字段                 | 作用                   |
| -------------------- | ---------------------- |
| `eventDescription` | 事件描述               |
| `minAge`           | 事件允许发生的最小年龄 |
| `maxAge`           | 事件允许发生的最大年龄 |
| `sanChange`        | 事件对理智值的影响     |
| `moodChange`       | 事件对心情值的影响     |

#### 已实现方法

##### `canHappenAtAge(int age)`

判断当前事件是否可以在指定年龄发生。

当传入年龄位于事件的最小年龄与最大年龄之间时，该方法返回 `true`；否则返回 `false`。

##### Getter 方法

为事件的各项属性实现了对应的 Getter 方法，包括：

- 获取事件描述。
- 获取最小年龄。
- 获取最大年龄。
- 获取理智值变化量。
- 获取心情值变化量。

---

### 3. 事件库系统

#### Event Library System

创建了 `EventLibrary` 类，用于集中管理游戏中的全部事件。

事件库在初始化时创建并保存所有年龄阶段的事件。目前事件数量已超过 15 个。

##### `getEventsForAge(int age)`

该方法根据玩家年龄返回所有可以发生的事件。

基本逻辑如下：

1. 遍历事件库中的所有事件。
2. 对每个事件调用 `canHappenAtAge(age)`。
3. 将符合年龄条件的事件加入结果列表。
4. 返回筛选后的事件集合。

##### `spinWheelForAge(int age)`

该方法模拟转动人生转盘，并随机返回一个适用于当前年龄的事件。

基本逻辑如下：

1. 调用 `getEventsForAge(age)` 获取当前年龄对应的事件列表。
2. 从事件列表中随机选择一个事件。
3. 返回被选中的事件。
4. 如果当前年龄没有可用事件，则返回 `null`。

---

### 4. 玩家事件处理

#### Player Event Handling

在 `Player` 类中实现了：

```java
experienceEvent(Event event)
```

该方法用于将事件结果应用到玩家当前状态。

#### 处理流程

1. 获取事件的 `sanChange`。
2. 将该变化量应用到玩家的理智值。
3. 获取事件的 `moodChange`。
4. 将该变化量应用到玩家的心情值。
5. 对更新后的属性进行范围限制。

#### 属性范围限制

玩家属性目前采用以下有效范围：

```text
0 <= San <= 100
0 <= Mood <= 100
```

如果属性变化后超过 `100`，则将其设置为 `100`。

如果属性变化后低于 `0`，则将其设置为 `0`。

#### 游戏结束条件

当以下任一条件成立时，游戏结束：

```text
San == 0
Mood == 0
```

---

### 5. 控制台 UI 系统

#### Console User Interface System

创建了 `GameApp` 类，用于管理控制台中的所有用户交互和游戏流程。

#### 当前交互流程

1. 启动程序。
2. 获取玩家姓名。
3. 获取玩家出生地。
4. 创建玩家对象。
5. 显示玩家当前属性。
6. 获取当前年龄可发生的事件列表。
7. 随机触发一个事件。
8. 显示事件描述和属性变化。
9. 显示更新后的玩家状态。
10. 提供继续游戏或退出游戏的选项。
11. 重复执行游戏循环，直到玩家退出或触发游戏结束条件。

#### 菜单选项

当前控制台菜单主要包含：

- 进入下一阶段或下一年。
- 继续转动人生转盘。
- 退出游戏。

---

### 6. 程序入口

#### Application Entry Point

创建了 `Main` 类作为程序入口。

`Main` 类负责：

- 创建 `GameApp` 对象。
- 启动游戏。
- 调用主要的游戏运行方法。
- 将程序执行流程交由 `GameApp` 管理。

# 后续目标

1.完成项目收集系统

一个 “add multiple Xs to a Y”
一个 “list all Xs in my Y” ------？

拟定会在轮盘里出现衣服，帽子，球鞋等物品，这些会存储到主角的背包里面
