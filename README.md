# Wiclipedia
Read Wikipedia, the wiki anyone can now read in the console.
<img width="1136" alt="Screenshot 2024-05-07 at 5 28 14 PM" src="https://github.com/pocketrice/Wiclipedia/assets/79682953/83aac47e-9d78-43e2-8b2c-aad7001ff985">


## Installation

### Linux / MacOS
1. Download the `wicli-1.0.0.jar` file (or latest release).
2. Move the `wicli-1.0.0.jar` file to `/usr/local/bin` directory:
```
sudo mv .../wicli-1.0.0.jar /usr/local/bin/
```

4. Create a shell script named `wicli` (no extension) at `/usr/local/bin/`:
```
cd /usr/local/bin/ && sudo touch wicli
```

5. Write script contents to file:
```
vim wicli (or preferred editor)
...
#!/bin/bash
java -jar /usr/local/bin/wicli-1.0.0.jar
```

4. Make the script executable:
```
sudo chmod +x /usr/local/bin/wicli
```

5. Go read Wikipedia!
<br><br>
### Windows
> Not tested yet ~ sorry!
1. Download the `wicli-1.0.0.jar` file (or latest release).
2. Move the `wicli-1.0.0.jar` file to `C:\Windows\System32` directory.
3. Create a batch script named `wicli.bat` with the following content:
    ```
    @echo off
    java -jar "C:\Windows\System32\wicli-1.0.0.jar" %*
    ```
4. Go read Wikipedia!
