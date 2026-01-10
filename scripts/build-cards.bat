@echo off
echo Building cards locally with Jib...
cd ..
cd cards
call mvn compile jib:dockerBuild
echo cards local build completed.
echo Pushing cards to registry...
call mvn compile jib:build
cd ..
echo cards push completed.
