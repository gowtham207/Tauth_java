@echo off

REM ==================================================
REM Run Gradle clean and bootRun commands on Windows
REM ==================================================

echo Cleaning project...
call .\gradlew clean

if %errorlevel% neq 0 (
    echo ❌ Gradle clean failed.
    exit /b %errorlevel%
)

echo Starting Spring Boot application...
call .\gradlew bootRun

if %errorlevel% neq 0 (
    echo ❌ Application failed to start.
    exit /b %errorlevel%
)

echo ✅ Application started successfully!
pause
