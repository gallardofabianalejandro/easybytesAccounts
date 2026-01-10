@echo off
echo Building all modules with Jib...
echo.
cd ..
echo Building accounts...
cd accounts
call mvn compile jib:dockerBuild
cd ..call mvn compile jib:build
cd ..
echo accounts build completed.
echo.

echo Building cards...
cd cards
call mvn compile jib:dockerBuild
call mvn compile jib:build
cd ..
echo cards build completed.
echo.

echo Building loans...
cd loans
call mvn compile jib:dockerBuild
call mvn compile jib:build
cd ..
echo loans build completed.
echo.

echo All builds completed successfully!
