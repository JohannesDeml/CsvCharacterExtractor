# CSV Character Extractor

![Csv Character Extractor workflow and functionality](https://i.imgur.com/ionpTPp.gif)

## Functionality
Extract all unique characters of each column of a csv file, combine and manipulate results and store the results in text files for further usage.  
This tool was developed to create font assets for TextmeshPro in Unity. Creating textures with just the character you need is essential for languages like Chinese, Japanese or Korean.

### Input
* Input file can be defined in config.xml, default value is "in/example.csv"
* Languages are defined in columns, first column defines the language name (see [example.csv](in/example.csv))
* Column `ID` and `Description` will be ignored
* Newline character (\n\r) and all emojis will be ignored

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
* With the config you can set the in and out path as well as characters that should be always or never included. Take a look at the [example config](config.xml)
* Paths can be relative, e.g. `in/example.csv`
* Paths can be absolute, e.g. `C:/Users/UserName/Documents/LanguageCharacterFiles/`
* Use forward slashes only `/`
* Automatically add lower and upper case charaters to the unique characters file
* Create union files of multiple separate columns
## Example
[Example Spreadsheet](https://docs.google.com/spreadsheets/d/1WmGauAzcCyQu7OcOnFP2Ypx2x9xuJCclpf7p25cFpz0/edit#gid=1088591893)

## Roadmap
* Document code
* Add information on how to build the project

## Third Party Libraries
* https://github.com/uniVocity/univocity-parsers (Apache 2.0 License)
* https://github.com/vdurmont/emoji-java (MIT License)
