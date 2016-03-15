[package value="pl.metastack.metatime.manual"]
# Introduction
MetaTime is a type-safe library for working with times and dates in Scala and Scala.js.

## Installation
Add the following dependencies to your build configuration:

```scala
libraryDependencies += "pl.metastack" %%  "metatime" % "%version%"  // Scala
libraryDependencies += "pl.metastack" %%% "metatime" % "%version%"  // Scala.js
```

## Key Features
## Type-safe DSL
  MetaTime is a type-safe DSL which offers to prevents a lot of type errors a user might do and gives users opportunity to focus
  on how best library can be used in their projects.

## Scheduler
  One of the most powerful feature offered by MetaTime is the scheduler, user may schedule different tasks / activities
  by defining the relative time in future from now or absolute Time/DateTime components.
  One simple example of scheduler usage looks like this:

  [scala type="section" value="scheduler" file="Examples"]
  Above example will schedule the task for 01 May 2016 midnight.

## Date / Time formatting
  MetaTime provides various ways to customize the format of the Time, Date and DateTime components.
  You may wish to use the default patterns or you can create your own, its as easy as describing component next to each other.

  For example below is usage of default format:
  [scala type="section" value="formatting" file="Examples"]

## Scala.js support
  Library offers support for Scala.js which enables users to use all these features in Scala.js also

## Features to be added in next release
* Duration
* Timezones

