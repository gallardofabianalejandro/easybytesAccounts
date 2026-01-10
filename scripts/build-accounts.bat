@echo off
echo Building accounts locally with Jib...
cd ..
cd accounts
call mvn compile jib:dockerBuild
echo accounts local build completed.
echo Pushing accounts to registry...
call mvn compile jib:build
cd ..
echo accounts push completed.
