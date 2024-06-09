@echo off
cd /d %~dp0
for %%i in (*.txt *.csv) do (
    if /I not "%%i"=="authDataBase.csv" (
        type nul > "%%i"
    )
)
echo Content of text and csv files erased, except for authDataBase.csv.
echo Made by Francisco
pause

