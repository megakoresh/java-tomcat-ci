#point to the correct configuration and webapp
CATALINA_BASE=`pwd`/target/apache-tomcat-8.5.13
export CATALINA_BASE

#copy over the Heroku config files (we already did this with maven)
#cp ./server-heroku.xml ./target/apache-tomcat-8.5.13/conf/server.xml
#cp ./persistence-heroku.xml ./target/apache-tomcat-8.5.13/webapps/ROOT/WEB-INF/classes/META-INF/persistence.xml

#make tomcat scripts executable
chmod a+x ./target/apache-tomcat-*/bin/*.sh

#heroku randomly generates a port to which app must bind, we need to access it via this $PORT variable
#if you use persistence you will also require database connection url, which you must put into a environment var on heroku and reference here
JAVA_OPTS="$JAVA_OPTS -Dhttp.port=$PORT -Dhibernate.connection.url=$DATABASE_URL"
export JAVA_OPTS

#finally run tomcat. Our app is already in the directory
$CATALINA_BASE/bin/catalina.sh run