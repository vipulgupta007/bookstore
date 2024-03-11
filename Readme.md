# Useful docker commands

## 1. <code> docker ps </code>
<span> This command is used to get all the running containers   </span>

## 2. <code> docker ps -a </code>
<span> This command is used to get all the running containers along with ones that have been exited </span>

## 3. <code> docker inspect {containerId} </code>
<span> This command is used to inspect all the config of a container, which included network, environment variables etc </span>

## 4. <code> docker images </code>
<span> Provides all the available images cached locally </span>

## 5. <code> docker rm {imageId} </code>
<span> This command removes provided non referenced image </span>

## 6. <code> docker rm $(docker container ls --all | grep -w bookstore-app | awk '{print $1}') </code>
<span> This command removes all the exited containers where bookstore-app key is contained </span>

## 7. <code> docker rm $(docker ps -aq  -f "ancestor=alpine/git" ) </code>
<span> This is similar to above command to remove containers based on their image name. Here if you change key ancestor to name then it will remove on basis of container name </span>

## 8. <code> docker images --filter=reference='*bookstore*' </code>
<span> This command is used to get all images filtered on basis of REPOSITORY Name. If you want to filter on tag as well then put that in as colon separated. 
        e.g. docker images --filter=reference='*bookstore*:*latest*'
References <a href="https://docs.docker.com/engine/reference/commandline/images/#filtering"> Image Filtering </a>
</span>

## 9. <code> docker build . -t {tag-name} </code>
<span> 

This will create an image with provided tag name.
Period after build command specify dockerfile is at current working directory. 
-f option should be used followed by custom file name if file name is different than default name 'dockerfile'

</span>

## 10. <code> docker run {imageId} </code>
<span> This will run corresponding image as container 
In case we want to pass environment variables use -e parameter followed by {VARIABLE_NAME}={VARIABLE_VALUE}
To use custom network to run container with use --network={network_name} 
Use --name= {container_name} to provide custom name to container
</span>

## 11. <code> docker network inspect {network_name} </code>
<span> This command will show network config in json format </span>

## 12. <code> docker network ls </code>
<span> This command will show all available networks </span>

## 13. <code>  docker network create --driver=bridge --subnet=182.168.120.0/24 --gateway=182.168.120.1 my-bridge
 </code> 
<span> This command will let us create a custom network with above mentioned subnet and gateway with driver type as bridge</span>

## 14. <code> docker tag docker-demo:latest vipul753/dockerdemo:3.0.0 </code>
<span> This command will tag existing docker image as {repository-name}/{new-image-name}:{tag}</span>

## 15. <code> docker push {repository-name}/{new-image-name}:{tag} </code>
<span> This command is used to push above image to docker registry </span>

## 16. <code> docker login </code>
<span> To login to docker registry hub </span>

## 17. <code> docker logout </code>
<span> To logout from docker registry hub </span>
