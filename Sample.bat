set projectLocation=E:\Selenium scripts\MCOEDTest
cd %projectLocation%
echo %projectLocation%

set classpath=%projectLocation%\bin;%projectLocation%\lib\*
echo %classpath%
java org.testng.TestNG %projectLocation%\ SmokeTest.xml
pause