mvn archetype:generate -DarchetypeGroupId=com.sematree.archetypes \
    -DarchetypeArtifactId=st-microservice-pattern \
    -DarchetypeVersion=0.0.1-SNAPSHOT \
    -DgroupId=com.intigna.services.canary \
    -DartifactId=canary-services \
    -Dversion=0.0.1-SNAPSHOT \
    -Dpackage=com.intigna.services.canary \
    -Ddomain=Canary  \
    -Dparent-org=intigna \
    -Dbeanfactoryname=canary-services \
    -Dservice-secret=ig-canary-services \
    -Drootpath=canary

