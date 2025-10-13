#!/bin/bash
# ==================================================
# Run Gradle clean and bootRun commands on Linux/macOS
# ==================================================

echo "🧹 Cleaning project..."
./gradlew clean
if [ $? -ne 0 ]; then
  echo "❌ Gradle clean failed."
  exit 1
fi

echo "🚀 Starting Spring Boot application..."
./gradlew bootRun
if [ $? -ne 0 ]; then
  echo "❌ Application failed to start."
  exit 1
fi

echo "✅ Application started successfully!"
