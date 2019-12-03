# Oracle Version Numbers

## Release Number Format
```
12.1.0.1.0
 ┬ ┬ ┬ ┬ ┬
 │ │ │ │ └───── Platform-Specific Release Number
 │ │ │ └────────── Component-Specific Release Number
 │ │ └─────────────── Fusion Middleware Release Number
 │ └──────────────────── Database Maintenance Release Number
 └───────────────────────── Major Database Release Number
```

**Major Database Release Number**

The first numeral is the most general identifier. It represents a major new version of the software that contains significant new functionality.

**Database Maintenance Release Number**

The second numeral represents a maintenance release level. Some new features may also be included.

**Fusion Middleware Release Number**

The third numeral reflects the release level of Oracle Fusion Middleware.

**Component-Specific Release Number**

The fourth numeral identifies a release level specific to a component. Different components can have different numbers in this position depending upon, for example, component patch sets or interim releases.

**Platform-Specific Release Number**

The fifth numeral identifies a platform-specific release. Usually this is a patch set. When different platforms require the equivalent patch set, this numeral will be the same across the affected platforms.

## Checking Your Current Release Number
```
SELECT * FROM PRODUCT_COMPONENT_VERSION;
```

## References
- [Oracle Version Numbers](https://www.krenger.ch/blog/oracle-version-numbers/)
- [Identifying Your Oracle Database Software Release](https://docs.oracle.com/cd/E11882_01/server.112/e25494/dba.htm#ADMIN11032)