# CSV Character Extractor

![Csv Character Extractor workflow and functionality](https://i.imgur.com/Q2bLbVf.gif)

This is a small tool to find all unique characters of a csv. The collected information can be used in order to know which specific characters of a language need to be supported.
This tool was developed to create font assets for textmesh pro with just the characters you need. This is especially useful for languages like korean, japanese or chinese.

### Input
* Has to be placed in "in/table.csv"
* Column ID and Description will be ignored
* Newline (\n) and all emojis will be ignored

### Output
* Files are created in "out.ColumnName.txt
* One file per column, expecting to have one language per column

## Download
[latest release](https://github.com/JohannesDeml/CsvCharacterExtractor/releases)

## Example
[Example Spreadsheet](https://docs.google.com/spreadsheets/d/1WmGauAzcCyQu7OcOnFP2Ypx2x9xuJCclpf7p25cFpz0/edit#gid=1088591893)

## Roadmap
* Add gif showing the functionality
* Add a file to define all files that should be ignored (e.g. {,})
* Add a file to define common characters that should always be included in the list (e.g. 0,1,2,3,4,5,6,7,8,9)
* Document code
* Add information on how to build the project

## Used libraries
* https://github.com/uniVocity/univocity-parsers
