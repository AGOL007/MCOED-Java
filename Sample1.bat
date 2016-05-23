cd E:\Selenium scripts\MCOEDTest
set ProjectPath=E:\Selenium scripts\MCOEDTest
echo %ProjectPath%
set classpath=%ProjectPath%\bin;%ProjectPath%\lib\*
echo %classpath%
javac findParcel.BlockAndLotSearch.java
java findParcel.BlockAndLotSearch
pause