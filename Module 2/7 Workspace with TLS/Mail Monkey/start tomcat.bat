cd ./tomcat/bin/
java -Dsun.lang.ClassLoader.allowArraySyntax=true -Djavax.net.ssl.trustStore= -XX:MaxPermSize=256M -jar bootstrap.jar
