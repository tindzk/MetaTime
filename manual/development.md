# Development
[![Build Status](https://travis-ci.org/MetaStack-pl/MetaTime.svg)](https://travis-ci.org/MetaStack-pl/MetaTime)

## Tests
The proper functioning of each operation is backed by [test cases](https://github.com/MetaStack-pl/MetaTime/tree/master/shared/src/test/scala/pl/metastack/metatime). These also provide complementary documentation.

## Manual
Run the following command to generate the manual:

```bash
$ sbt manual/runMain pl.metastack.metatime.manual.Manual
```

Deploy it with:

```bash
$ sbt manual/runMain pl.metastack.metatime.manual.Deploy
```
