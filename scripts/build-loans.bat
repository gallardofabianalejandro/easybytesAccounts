@echo off
echo Building loans locally with Jib...
cd ..
cd loans
call mvn compile jib:dockerBuild
echo loans local build completed.
echo Pushing loans to registry...
call mvn compile jib:build
cd ..
echo loans push completed.
