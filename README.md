## Installation
### Linux
[Download](https://github.com/urankajtazaj/remove-unused/releases/download/v1/cleanFiles.java)

or via Terminal

`wget 'https://github.com/urankajtazaj/remove-unused/releases/download/v1/cleanFiles.java'`

### Windows
[Download](https://github.com/urankajtazaj/remove-unused/releases/download/v1/cleanFiles.java)

## Usage
* Navigate to the directory where the `cleanFiles.java` file is currently (`cd dir/to/cleanFiles.java`)
* run `java cleanFiles [directory/to/clean] [not used since]`

### Example
`java cleanFiles c://user/JohnDoe/Downloads 30` - Deletes all files that have not been used for 30 days

`java cleanFiles` - Deletes all files in the home directory, that have not been used for 60 days

`java cleanFiles -h` - Shows how to use


**Java has to be installed**
