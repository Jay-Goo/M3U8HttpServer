# M3U8HttpServer
Android Local Http Server for M3U8

# Dependencies

```xml
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        compile 'com.github.Jay-Goo:M3U8HttpServer:v1.0.0'
	}
   
```

# Usage
- `createLocalHttpUrl(String filePath)` convert local file path to http server url

- `execute()` start server

- `finish()`  stop server

# Sample
  you can go to [M3U8Downloader](https://github.com/Jay-Goo/M3U8Downloader) to know how to use it in your project


