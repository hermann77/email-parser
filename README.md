# email-parser

Email parser is a tool to extract email addresses from thunderbird folder file, that is the file including all the emails. On UNIX-systems you can find this folders in:
```
~/.thunderbird/<PROFILE_ID>/Mail/Local\ Folders
```
or

```
~/.thunderbird/<PROFILE_ID>/ImapMail
```
This tool deletes all the email addresses from your database that are read in thunderbird emails folder file (auto-response emails) and have status codes:
550 - mailbox unavailable
511 - user unknown
520 - user unknown
553 - no such user
554 - no account with this address
and
"Host or domain name not found" (no code)


## Getting Started

git clone https://github.com/hermann77/email-parser.git

### Prerequisites

* JDK 8 (1.8) or newer 
* Gradle: you can use gradle wrapper (*gradlew*) 

### Installing

To build JAR file:

```
gradlew fatJar
```

### Usage
```
Usage:

java -jar build/libs/email-parser-all-<VERSION>.jar
-a,--attribute attribute name where the email addresses are saved
-d,--dbname database name where the email addresses are saved
-i,--input input file path
-p,--port database port
-t,--table database table name where the email addresses are
saved

```

```
Example usage:

java -jar build/libs/email-parser-all-0.6.1.jar -i E-Mails_Export/ -d DATABASE_NAME -t TABLE_NAME -a COLUMN_NAME
```

`E-Mails_Export/` must include one or more subdirectories including mbox file:
```
E-Mails_Export/
├── 2018-01-01
│   └── mbox
├── 2018-01-02
│   └── mbox
├── 2018-01-03
│   └── mbox
```

#### DB PORT
standard on unix: 3306
on MacOS: 3307

#### DATABASE NAME, TABLE NAME, ATTRIBUTE NAME 
used to delete email addresses from DB: "DELETE FROM " + tableName + " WHERE " + attributeName + " IN (?)"
where ? is placeholder for read email addresses from thunderbird email autoresponses.

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


## Authors

* **Hermann Schwarz** - *Initial work*

See also the list of [contributors](https://github.com/hermann77/email-parser/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Inspiration: to resolve the problem if you get a lot of auto-response e-mails if your newsletter engine send e-mails to non existing e-mail addresses.

