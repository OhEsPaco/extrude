# Extrude

It also supports lathe!

### Prerequisites

```
Maven
Any java IDE
```
### Building

Open your favorite terminal application and execute:
```
mvn clean compile assembly:single
```
Now you can find the jar file in target folder.

### Usage

```
java -jar extrude-X.X.X.jar extrude <input> <output> <length>
java -jar extrude-X.X.X.jar lathe <input> <output> <steps> <angle> <axis>
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
