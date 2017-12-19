#!/bin/bash
ssh space_invasion@$BACKEND_HOST -o StrictHostKeyChecking=no "echo $TRAVIS_TAG $BACKEND_HOST; pwd; ls -la; cd SpaceInvasionBackend && sudo git checkout $TRAVIS_TAG && sudo git pull origin $TRAVIS_TAG && sudo docker rm -f $(sudo docker ps -aqf name=postgres) $(sudo docker ps -aqf name=backend); sudo docker-compose up --build"
