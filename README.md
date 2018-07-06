# CSV Character Extractor

![Csv Character Extractor workflow and functionality](https://i.imgur.com/4xgNbWc.gif)

This is a small tool to find all unique characters of a csv. The collected information can be used in order to know which specific characters of a language need to be supported.
This tool was developed to create font assets for textmesh pro with just the characters you need. This is especially useful for languages like korean, japanese or chinese.

### Input
* Input file can be dfined in config.xml, default value is "in/example.csv"
* Languages are defined in columns, first column defines the language name (see [example.csv](in/example.csv))
* Column ID and Description will be ignored
* Newline (\n) and all emojis will be ignored

### Output
* Text files are created for each language and named "ColumnName.txt". Output path can be defined in config.xml
* One file per column, expecting to have one language per column

## Requirements
* [Java Runtime environment](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)

## Download
* [latest release](https://github.com/JohannesDeml/CsvCharacterExtractor/releases)

## Run
* Windows: Doubleclick Run.bat
* Windows, Mac, Linux: Run `java -jar CsvCharacterExtractor.jar` in the terminal
### Config usage
* Paths can be relative, e.g. `in/example.csv`
* Paths can be absolute, e.g. `C:/Users/UserName/Documents/LanguageCharacterFiles/`
* Use forward slashes only `/`
## Example
[Example Spreadsheet](https://docs.google.com/spreadsheets/d/1WmGauAzcCyQu7OcOnFP2Ypx2x9xuJCclpf7p25cFpz0/edit#gid=1088591893)

## Roadmap
* Add a file to define all files that should be ignored (e.g. {,})
* Add a file to define common characters that should always be included in the list (e.g. 0,1,2,3,4,5,6,7,8,9)
* Document code
* Add information on how to build the project

## Used libraries
* https://github.com/uniVocity/univocity-parsers
