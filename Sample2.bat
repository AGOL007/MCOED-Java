cd E:\Selenium scripts\MCOEDTest
set ProjectPath=E:\Selenium scripts\MCOEDTest
echo %ProjectPath%
set classpath=%ProjectPath%\bin;%ProjectPath%\lib\*
echo %classpath%
java org.testng.TestNG %ProjectPath%\ testng.xml
pause