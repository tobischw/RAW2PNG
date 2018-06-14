# RAW2PNG
Convert 24-bit RGB RAW pictures to PNG.

Built for converting RAW training data to PNG for viewing and analysis. The training data is used for [FogLight locationing](https://github.com/oci-pronghorn/FogLight).

It's not very fast, but it gets the job done.

## Install
Clone the repository, cd into it, and run the following command:

```
mvn install
```

Next, copy RAW2PNG.jar out of the target folder to use it anywhere.

## Usage

```
java -jar RAW2PNG.jar -r FOLDER_WITH_PICTURES -o FOLDER_WITH_OUTPUT -w 1920 -h 1080 -x .raw
```
