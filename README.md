# email-parser

Email parser is a tool to extract email addresses from thunderbird folder file, that is the file including all the emails. On UNIX-systems you can find this folders in:
```
~/.thunderbird/<PROFILE_ID>/Mail/Local\ Folders
```
or

```
~/.thunderbird/<PROFILE_ID>/ImapMail
```

## Getting Started

git clone https://github.com/hermann77/email-parser.git

### Prerequisites

* JDK 8 (1.8) or newer 
* Gradle: you can use gradle wrapper (*gradlew*) 

### Installing

To build JAR file:

```
gradlew build
```


## Running the tests



### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Gradle](https://gradle.org/) 


## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Hermann Schwarz** - *Initial work*

See also the list of [contributors](https://github.com/hermann77/email-parser/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Inspiration: to resolve the problem if you get a lot of auto-response e-mails if your newsletter engine send e-mails to non existing e-mail addresses.

